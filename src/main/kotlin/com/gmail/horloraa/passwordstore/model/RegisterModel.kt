package com.gmail.horloraa.passwordstore.model

import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class RegisterModel{
    val passwordProperty = SimpleStringProperty()
    var password : String by passwordProperty

    val passwordAgainProperty = SimpleStringProperty()
    var passwordAgain : String by passwordProperty
}