package com.gmail.horloraa.passwordstore.viewmodel

import com.gmail.horloraa.passwordstore.repository.PasswordRepository
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class StartViewModel : ViewModel() {
    val passwordProperty = SimpleStringProperty()
    var password by passwordProperty

    init{
        password = ""
    }

    fun onLogin(){
        PasswordRepository.loginWithPassword(password);
    }

    val fileExists = PasswordRepository.checkTableExist()
}

