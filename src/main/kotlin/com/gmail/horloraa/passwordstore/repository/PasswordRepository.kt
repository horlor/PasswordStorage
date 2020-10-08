package com.gmail.horloraa.passwordstore.repository

import com.gmail.horloraa.passwordstore.model.PasswordRecord
import com.gmail.horloraa.passwordstore.repository.data.*
import com.gmail.horloraa.passwordstore.repository.data.Crypto
import javafx.collections.ObservableList
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.exists
import org.jetbrains.exposed.sql.transactions.transaction
import tornadofx.asObservable
import tornadofx.invalidate

object PasswordRepository{
    val all : ObservableList<com.gmail.horloraa.passwordstore.model.PasswordRecord> by lazy{
        transaction{
            DbPasswordRecord.all().map{
                return@map it.toDomainModel()
            }.asObservable()
        }
    }

    fun login(password: String): Boolean{
        val data = transaction {
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

    fun add(record: PasswordRecord): PasswordRecord{
        val (password, passwordIv) = Crypto.encrypt(record.password)

        val added  = transaction {
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

    fun update(record: PasswordRecord){
        if(record.id == null)
            return
        val (password, passwordiv) = Crypto.encrypt(record.password)
        transaction {
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

    fun delete(record: PasswordRecord){
        if(record.id == null)
            return
        transaction {
            val dbrecord = DbPasswordRecord.get(record.id!!)
            dbrecord.delete();
        }
        all.remove(record)
    }

    fun checkTableExist(): Boolean{
        return transaction {
            PasswordRecords.exists()
        }
    }

    fun createTable(password: String) {
        transaction {
            SchemaUtils.create(PasswordRecords);
            SchemaUtils.create(LoginRecords);
            val (hashSalt, passwordHash, salt) = Crypto.calculateHash(password)
            DbLoginRecord.new{
                this.hashSalt = hashSalt
                this.hash = passwordHash
                this.passwordSalt = salt;
            }
            loginWithPassword(password,passwordHash)

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