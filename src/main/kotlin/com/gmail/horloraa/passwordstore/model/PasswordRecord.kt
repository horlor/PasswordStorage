package com.gmail.horloraa.passwordstore.model

import javafx.beans.property.SimpleStringProperty
import tornadofx.*;

class PasswordRecord{

    var id : Int? = null

    val usernameProperty = SimpleStringProperty()
    var username by usernameProperty

    val passwordProperty = SimpleStringProperty()
    var password by passwordProperty

    val webPageProperty = SimpleStringProperty()
    var webPage by webPageProperty

    val emailProperty = SimpleStringProperty()
    var email by emailProperty

    val commentProperty = SimpleStringProperty()
    var comment by commentProperty

    val tagProperty = SimpleStringProperty()
    var tag by tagProperty

    init{
        username =""
        password= ""
        webPage = ""
        email = ""
        comment = ""
        tag = ""
    }
}