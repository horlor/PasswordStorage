package com.gmail.horloraa.passwordstore.model

import javafx.beans.property.SimpleStringProperty
import tornadofx.*;
import javax.jws.WebParam

class PasswordRecord{

    var id : Int? = null

    val usernameProperty = SimpleStringProperty()
    var username by usernameProperty

    val passwordProperty = SimpleStringProperty()
    var password by passwordProperty

    val webPageProperty = SimpleStringProperty()
    var webPage by webPageProperty

    val emailProperty = SimpleStringProperty()
    var email by usernameProperty

    val commentProperty = SimpleStringProperty()
    var comment by commentProperty

    init{
        username =""
        password= ""
        webPage = ""
        email = ""
        comment = ""
    }
}