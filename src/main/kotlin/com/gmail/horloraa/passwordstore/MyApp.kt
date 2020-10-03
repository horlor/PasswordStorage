package com.gmail.horloraa.passwordstore

import com.gmail.horloraa.passwordstore.repository.data.Crypto
import com.gmail.horloraa.passwordstore.repository.data.DbSetting
import com.gmail.horloraa.passwordstore.repository.data.DbPasswordRecord
import com.gmail.horloraa.passwordstore.repository.data.PasswordRecords
import com.gmail.horloraa.passwordstore.view.MainView
import javafx.application.Application
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import tornadofx.App

class MyApp : App(MainView::class, Styles::class)

fun main(args: Array<String>) {
    DbSetting.database;
    Crypto.calculateKey("login")
    Application.launch(MyApp::class.java, *args)
}
