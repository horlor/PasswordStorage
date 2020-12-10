package com.gmail.horloraa.passwordstore.viewmodel

import com.gmail.horloraa.passwordstore.services.PasswordService
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.stage.FileChooser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tornadofx.*

class LoginViewModel : StorageViewModel() {
    val passwordService = PasswordService
    val passwordProperty = SimpleStringProperty()
    var password by passwordProperty

    val isBusyProperty = SimpleBooleanProperty();
    var isBusy by isBusyProperty

    init{
        password = ""
        isBusy = false;
    }

    suspend fun onLogin(): Boolean{
        isBusy = true
        val ret = passwordService.login(password)
        isBusy = false
        return ret
    }

    fun fileExists() = passwordService.checkTableExist()

}

