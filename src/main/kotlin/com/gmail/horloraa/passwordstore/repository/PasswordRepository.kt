package com.gmail.horloraa.passwordstore.repository

import com.gmail.horloraa.passwordstore.model.PasswordRecord
import com.gmail.horloraa.passwordstore.repository.data.*
import javafx.collections.ObservableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.exists
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync
import org.jetbrains.exposed.sql.transactions.transaction
import tornadofx.asObservable
import tornadofx.invalidate
import java.io.File
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

private val defaultPath by lazy{
    val userhome = System.getProperty("user.home");
    val path = "$userhome\\.password_store"
    val dir = File(path)
    if(!dir.exists()) {
        dir.mkdir();
    }
    return@lazy "$path\\passwords.db"
}

class PasswordRepository(private val path: String){
    internal object Crypto{
        private lateinit var key: SecretKey

        private val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        private val keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        private val random: SecureRandom = SecureRandom()

        fun calculateKey(password: String, salt: ByteArray){
            val spec = PBEKeySpec(password.toCharArray(), salt, 100000, 256)
            val keyBytes = keyFactory.generateSecret(spec).encoded
            key = SecretKeySpec(keyBytes,"AES")
        }

        fun calculateHash(password: String): Triple<ByteArray, ByteArray, ByteArray>{
            val salt = ByteArray(32,{0})
            random.nextBytes(salt)
            val keyspec = PBEKeySpec(password.toCharArray(),salt,100000,256)
            val keyBytes = keyFactory.generateSecret(keyspec).encoded
            val passwordSalt = ByteArray(32,{0})
            random.nextBytes(passwordSalt)
            return Triple(salt, keyBytes, passwordSalt)
        }

        fun checkHash(password: String, hash: ByteArray, salt: ByteArray): Boolean{
            val keyspec = PBEKeySpec(password.toCharArray(),salt,100000,256)
            val keyBytes = keyFactory.generateSecret(keyspec).encoded
            return hash.contentEquals(keyBytes)
        }

        fun encrypt(plaintext: String): Pair<ByteArray,ByteArray>{
            val bytes =  plaintext.toByteArray(Charsets.UTF_8)
            val iv = ByteArray(16,{0})
            random.nextBytes(iv)
            cipher.init(Cipher.ENCRYPT_MODE,key, IvParameterSpec(iv))
            val ciphertext = cipher.doFinal(bytes)

            return Pair(ciphertext, iv);
        }

        fun decrypt(ciphertext: ByteArray, iv: ByteArray = ByteArray(16,{0})): String{
            cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(iv))
            val plaintext = cipher.doFinal(ciphertext)
            return plaintext.toString(Charsets.UTF_8)
        }
    }

    private val database by lazy{
        Database.connect("jdbc:sqlite:$path", "org.sqlite.JDBC")
    }

    constructor() : this(defaultPath)

    val all = mutableListOf<PasswordRecord>().asObservable()

    suspend fun loadRecords(){
        val records =
            newSuspendedTransaction (Dispatchers.IO,db=database) {
                DbPasswordRecord.all().map {
                    it.toDomainModel()
                }
            }
        records.forEach {
            all.add(it)
        }
    }

    suspend fun login(password: String): Boolean{
        val data = newSuspendedTransaction(Dispatchers.IO, database) {
             DbLoginRecord.all().elementAt(0)
        }
        val res = Crypto.checkHash(password,data.hash,data.hashSalt)
        if(res){
            loginWithPassword(password,data.passwordSalt)
        }
        return res
    }

    private fun loginWithPassword(password: String, salt: ByteArray){
        Crypto.calculateKey(password, salt);
    }

    suspend fun add(record: PasswordRecord): PasswordRecord{
        val (password, passwordIv) =
                withContext(Dispatchers.Main){
                    Crypto.encrypt(record.password)
                }

        val added  = newSuspendedTransaction(Dispatchers.IO, database) {
            val ret = DbPasswordRecord.new{
                username = record.username
                email = record.email
                webPage = record.webPage
                comment = record.comment
                tag = record.tag
                this.password = password
                this.passwordIv = passwordIv
            }
            ret.toDomainModel()
        }
        all.add(added);
        return added;
    }

    suspend fun update(record: PasswordRecord){
        if(record.id == null)
            return
        val (password, passwordiv) = Crypto.encrypt(record.password)
        newSuspendedTransaction(Dispatchers.IO, database) {
            val dbrecord = DbPasswordRecord.get(record.id!!)
            dbrecord.comment = record.comment
            dbrecord.email = record.email
            dbrecord.username = record.username
            dbrecord.webPage = record.webPage
            dbrecord.password = password
            dbrecord.passwordIv = passwordiv
            dbrecord.tag = record.tag
        }
        all.invalidate()
    }

    suspend fun delete(record: PasswordRecord){
        if(record.id == null)
            return
        newSuspendedTransaction(Dispatchers.IO,database) {
            val dbrecord = DbPasswordRecord.get(record.id!!)
            dbrecord.delete();
        }
        all.remove(record)
    }

    fun checkTableExist(): Boolean{
        return transaction(database) {
            PasswordRecords.exists()
        }
    }

    suspend fun createTable(password: String) {
        newSuspendedTransaction(Dispatchers.IO,database) {
            SchemaUtils.create(PasswordRecords);
            SchemaUtils.create(LoginRecords);
            val (hashSalt, passwordHash, salt) = Crypto.calculateHash(password)
            DbLoginRecord.new{
                this.hashSalt = hashSalt
                this.hash = passwordHash
                this.passwordSalt = salt;
            }
            loginWithPassword(password,salt)

        }
    }

    suspend fun changePassword(passwordstr: String){
        val (hashSalt, passwordHash, salt) = Crypto.calculateHash(passwordstr)
        newSuspendedTransaction(Dispatchers.IO,database) {
            val data = transaction(database) {
                DbLoginRecord.all().elementAt(0)
            }
            data.hashSalt = hashSalt
            data.hash = passwordHash
            data.passwordSalt = salt

            loginWithPassword(passwordstr, salt)

            DbPasswordRecord.all().forEachIndexed { idx, record ->
                val (password, iv) = Crypto.encrypt(all[idx].password)

                record.password = password
                record.passwordIv = iv
            }
        }
    }

    fun DbPasswordRecord.toDomainModel(): PasswordRecord{
        return PasswordRecord().apply {
            id = this@toDomainModel.id.value
            username = this@toDomainModel.username
            email = this@toDomainModel.email
            webPage = this@toDomainModel.webPage
            comment = this@toDomainModel.comment
            password = Crypto.decrypt(this@toDomainModel.password,this@toDomainModel.passwordIv)
            tag = this@toDomainModel.tag
        }
    }
}

