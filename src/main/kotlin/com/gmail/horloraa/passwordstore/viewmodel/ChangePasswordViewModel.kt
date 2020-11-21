package com.gmail.horloraa.passwordstore.viewmodel

import com.gmail.horloraa.passwordstore.model.RegisterModel
import com.gmail.horloraa.passwordstore.services.PasswordService
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class ChangePasswordViewModel : ItemViewModel<RegisterModel>(){
    var password = bind(RegisterModel::passwordProperty)
    var passwordAgain = bind(RegisterModel::passwordAgainProperty)

    override fun onCommit() {
        super.onCommit()
        if(!password.value.isNullOrEmpty())
            PasswordService.changePassword(password.value)
    }
}