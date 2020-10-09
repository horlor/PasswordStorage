package com.gmail.horloraa.passwordstore

import com.gmail.horloraa.passwordstore.repository.data.DbSetting
import com.gmail.horloraa.passwordstore.view.StartView
import javafx.application.Application
import javafx.scene.image.Image
import javafx.stage.Stage
import tornadofx.App
import java.io.File
import java.io.FileInputStream

class MyApp : App(StartView::class, Styles::class){

}

fun main(args: Array<String>) {
    DbSetting.database;
    Application.launch(MyApp::class.java, *args)
}
