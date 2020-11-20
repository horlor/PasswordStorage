package com.gmail.horloraa.passwordstore.viewmodel

import com.gmail.horloraa.passwordstore.services.PasswordService
import javafx.beans.property.SimpleStringProperty
import javafx.stage.FileChooser
import tornadofx.*

class LoginViewModel : ViewModel() {
    val passwordService = PasswordService
    val passwordProperty = SimpleStringProperty()
    var password by passwordProperty

    init{
        password = ""
    }

    fun onLogin(): Boolean{
        return passwordService.login(password)
    }

    fun fileExists() = passwordService.checkTableExist()

    fun openStorage(){
        val files = chooseFile(title="Open storage file",mode = FileChooserMode.Single,filters =  arrayOf())
        val file = files.first()
        passwordService.openRepository(file.absolutePath)
    }

    fun createStorage(){
        val files = chooseFile(title="Open storage file",mode = FileChooserMode.Single,filters =  arrayOf())
        val file = files.first()
        passwordService.createRepository(file.absolutePath)
    }
}

