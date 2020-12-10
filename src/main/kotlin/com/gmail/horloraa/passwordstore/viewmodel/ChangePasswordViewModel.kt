package com.gmail.horloraa.passwordstore.viewmodel

import com.gmail.horloraa.passwordstore.model.RegisterModel
import com.gmail.horloraa.passwordstore.services.PasswordService
import javafx.beans.property.SimpleBooleanProperty
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tornadofx.*

class ChangePasswordViewModel : ItemViewModel<RegisterModel>(){
    var password = bind(RegisterModel::passwordProperty)
    var passwordAgain = bind(RegisterModel::passwordAgainProperty)

    val isBusyProperty = SimpleBooleanProperty()
    var isBusy by isBusyProperty

    suspend fun onChange() {
        commit()
        if(!password.value.isNullOrEmpty()){
            isBusy = true
            PasswordService.changePassword(password.value)
            isBusy = false
        }
    }
}