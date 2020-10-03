package com.gmail.horloraa.passwordstore.repository.data

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.VarCharColumnType

object DbSetting{
    val database by lazy{
        Database.connect("jdbc:sqlite:C:\\Users\\Horvath Lorant\\Documents\\passwords.db", "org.sqlite.JDBC")
    }
}

class EncryptedVarChar: VarCharColumnType(){
    override fun valueToDB(value: Any?): Any? {
        return super.valueToDB(value)
    }
}