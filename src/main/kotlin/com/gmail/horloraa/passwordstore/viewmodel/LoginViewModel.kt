package com.gmail.horloraa.passwordstore.viewmodel

import com.gmail.horloraa.passwordstore.services.PasswordService
import javafx.beans.property.SimpleStringProperty
import javafx.stage.FileChooser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tornadofx.*

class LoginViewModel : StorageViewModel() {
    val passwordService = PasswordService
    val passwordProperty = SimpleStringProperty()
    var password by passwordProperty

    init{
        password = ""
    }

    suspend fun onLogin(): Boolean{
        return passwordService.login(password)
    }

    fun fileExists() = passwordService.checkTableExist()

}

