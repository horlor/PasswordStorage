package com.gmail.horloraa.passwordstore.viewmodel

import com.gmail.horloraa.passwordstore.services.PasswordService
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class ChangePasswordViewModel : ViewModel(){
    val passwordProperty = SimpleStringProperty()
    var password: String by passwordProperty

    val passwordAgainProperty = SimpleStringProperty()
    var passwordAgain : String by passwordAgainProperty

    override fun onCommit() {
        super.onCommit()
        if(password == passwordAgain)
            PasswordService.changePassword(password)
    }
}