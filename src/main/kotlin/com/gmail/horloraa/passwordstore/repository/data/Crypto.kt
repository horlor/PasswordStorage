package com.gmail.horloraa.passwordstore.repository.data

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
    private val random: SecureRandom = SecureRandom()

    fun calculateKey(password: String){
        val salt = byteArrayOf(8,6,1,5,8,1,5,9,84);
        val spec = PBEKeySpec(password.toCharArray(), salt, 100000, 256)
        val skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val keyBytes = skf.generateSecret(spec).encoded
        key = SecretKeySpec(keyBytes,"AES")
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