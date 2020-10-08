package com.gmail.horloraa.passwordstore

import com.gmail.horloraa.passwordstore.repository.data.DbSetting
import com.gmail.horloraa.passwordstore.view.StartView
import javafx.application.Application
import tornadofx.App

class MyApp : App(StartView::class, Styles::class)

fun main(args: Array<String>) {
    DbSetting.database;
    Application.launch(MyApp::class.java, *args)
}
