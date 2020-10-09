package com.gmail.horloraa.passwordstore.viewmodel

import com.gmail.horloraa.passwordstore.repository.PasswordRepository
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class LoginViewModel : ViewModel() {
    val passwordProperty = SimpleStringProperty()
    var password by passwordProperty

    init{
        password = ""
    }

    fun onLogin(): Boolean{
        return PasswordRepository.login(password);
    }

    val fileExists = PasswordRepository.checkTableExist()
}

