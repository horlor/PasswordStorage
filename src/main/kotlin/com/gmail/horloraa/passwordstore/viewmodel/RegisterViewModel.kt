package com.gmail.horloraa.passwordstore.viewmodel
import com.gmail.horloraa.passwordstore.model.RegisterModel
import com.gmail.horloraa.passwordstore.services.PasswordService
import tornadofx.*

class RegisterViewModel : ItemViewModel<RegisterModel>() {
    val passwordService= PasswordService
    val password = bind(RegisterModel::passwordProperty)
    val passwordAgain = bind(RegisterModel::passwordAgainProperty)

    fun createDatabase(){
        commit()
        passwordService.createTable(password.value)
    }

    fun openStorage() = openStorageFun()

    fun createStorage() = createStorageFun()

}

