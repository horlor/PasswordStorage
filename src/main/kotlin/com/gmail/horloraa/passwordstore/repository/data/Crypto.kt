package com.gmail.horloraa.passwordstore.repository.data

import com.sun.org.apache.xpath.internal.operations.Bool
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec

import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec


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