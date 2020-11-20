package com.gmail.horloraa.passwordstore.viewmodel
import com.gmail.horloraa.passwordstore.services.PasswordService
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class RegisterViewModel : ItemViewModel<RegisterModel>() {
    val passwordService= PasswordService
    val password = bind(RegisterModel::passwordProperty)
    val passwordAgain = bind(RegisterModel::passwordAgainProperty)

    fun createDatabase(){
        commit()
        passwordService.createTable(password.value)
    }

    fun openStorage(){
        val files = chooseFile(title="Open storage file",mode = FileChooserMode.Single,filters =  arrayOf())
        val file = files.first()
        passwordService.openRepository(file.absolutePath)
    }

    fun createStorage(){
        val files = chooseFile(title="Open storage file",mode = FileChooserMode.Single,filters =  arrayOf())
        val file = files.first()
        passwordService.createRepository(file.absolutePath)
    }

}

class RegisterModel{
    val passwordProperty = SimpleStringProperty()
    var password : String by passwordProperty

    val passwordAgainProperty = SimpleStringProperty()
    var passwordAgain : String by passwordProperty
}