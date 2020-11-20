package com.gmail.horloraa.passwordstore

import com.gmail.horloraa.passwordstore.view.LoginView
import javafx.application.Application
import tornadofx.App
import java.lang.Exception

class MyApp : App(LoginView::class, Styles::class){

}

fun main(args: Array<String>) {
    try{
        Application.launch(MyApp::class.java, *args)
    }
    catch (e: Exception){
        tornadofx.error("Unexpected error", "An unexpected exception was thrown\n${e.message}").show()
    }

}
