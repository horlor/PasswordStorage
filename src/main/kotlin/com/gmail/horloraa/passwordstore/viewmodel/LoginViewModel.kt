package com.gmail.horloraa.passwordstore.viewmodel

import com.gmail.horloraa.passwordstore.services.PasswordService
import javafx.beans.property.SimpleStringProperty
import javafx.stage.FileChooser
import tornadofx.*

class LoginViewModel : StorageViewModel() {
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

}

