package com.gmail.horloraa.passwordstore.view.controls

import javafx.beans.property.SimpleStringProperty
import javafx.event.EventHandler
import javafx.scene.image.Image
import javafx.scene.layout.Priority
import tornadofx.*
import java.io.FileInputStream

class PasswordFieldWithShow(private val passwordProperty: SimpleStringProperty) : Fragment("PasswordField") {
    private val passwordField = passwordfield(passwordProperty)
    private var hidden = false

    override val root = hbox{
        stackpane {
            textfield(passwordProperty) {  }
            this+=passwordField
            hgrow=Priority.ALWAYS
        }
        imageview(Image(FileInputStream("eye.png"))){
            onMouseClicked = EventHandler {
                hidden = if(hidden){
                    passwordField.show()
                    false
                } else{
                    passwordField.hide()
                    true
                }
            }
        }
    }

}
