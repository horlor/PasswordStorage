package com.gmail.horloraa.passwordstore.repository.data

import org.jetbrains.exposed.sql.Database
import java.io.File

object DbSetting{
    val database by lazy{
        val userhome = System.getProperty("user.home");
        val path = "$userhome\\.password_store"
        val dir = File(path)
        if(!dir.exists()) {
            dir.mkdir();
        }
        Database.connect("jdbc:sqlite:$path\\passwords.db", "org.sqlite.JDBC")
    }
}

