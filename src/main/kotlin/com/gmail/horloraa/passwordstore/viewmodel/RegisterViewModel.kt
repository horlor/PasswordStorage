package com.gmail.horloraa.passwordstore.viewmodel
import com.gmail.horloraa.passwordstore.model.RegisterModel
import com.gmail.horloraa.passwordstore.services.PasswordService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import tornadofx.*

class RegisterViewModel : ItemViewModel<RegisterModel>() {
    val passwordService= PasswordService
    val password = bind(RegisterModel::passwordProperty)
    val passwordAgain = bind(RegisterModel::passwordAgainProperty)

    suspend fun createDatabase(){
        commit()
        withContext(Dispatchers.IO) {
            passwordService.createTable(password.value)
        }

    }

    fun openStorage() = openStorageFun()

    fun createStorage() = createStorageFun()

}

