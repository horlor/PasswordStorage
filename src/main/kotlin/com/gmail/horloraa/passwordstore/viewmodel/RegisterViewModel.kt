package com.gmail.horloraa.passwordstore.viewmodel
import com.gmail.horloraa.passwordstore.repository.PasswordRepository
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class RegisterViewModel : ItemViewModel<RegisterModel>() {
    val password = bind(RegisterModel::passwordProperty)
    val passwordAgain = bind(RegisterModel::passwordAgainProperty)

    fun createDatabase(){
        commit()
        PasswordRepository.createTable(password.value)
    }

}

class RegisterModel{
    val passwordProperty = SimpleStringProperty()
    var password : String by passwordProperty

    val passwordAgainProperty = SimpleStringProperty()
    var passwordAgain : String by passwordProperty
}