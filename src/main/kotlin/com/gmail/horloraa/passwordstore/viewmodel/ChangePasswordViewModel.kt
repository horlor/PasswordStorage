package com.gmail.horloraa.passwordstore.viewmodel

import com.gmail.horloraa.passwordstore.model.RegisterModel
import com.gmail.horloraa.passwordstore.services.PasswordService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import tornadofx.*

class ChangePasswordViewModel : ItemViewModel<RegisterModel>(){
    var password = bind(RegisterModel::passwordProperty)
    var passwordAgain = bind(RegisterModel::passwordAgainProperty)

    override fun onCommit() {
        super.onCommit()
        if(!password.value.isNullOrEmpty())
            GlobalScope.launch {
                PasswordService.changePassword(password.value)
            }
    }
}