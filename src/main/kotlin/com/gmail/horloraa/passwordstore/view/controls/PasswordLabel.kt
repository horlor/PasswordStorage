package com.gmail.horloraa.passwordstore.view.controls


import javafx.beans.property.SimpleStringProperty
import javafx.event.EventHandler
import javafx.scene.Parent
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import javafx.scene.layout.Priority
import tornadofx.*
import tornadofx.FX.Companion.icon
import java.io.FileInputStream

class PasswordLabel(private val passwordProperty: SimpleStringProperty) : Fragment(){

    val textShownProperty = SimpleStringProperty()
    var textShown by textShownProperty
     init{
         textShown = ""
         passwordProperty.onChange { str ->
             if(str != null && str != "")
                 textShown = "⦁⦁⦁⦁⦁⦁⦁⦁"
         }
     }

    override val root = hbox {
        label(textShownProperty) {
            contextmenu {
                item("Copy to clipboard").action {
                    val clipboard = Clipboard.getSystemClipboard()
                    clipboard.putString(passwordProperty.value)
                }
            }
        }
        hbox{
            hgrow = Priority.ALWAYS
        }
        imageview(Image(FileInputStream("eye.png"))){
            onMousePressed = EventHandler {
                textShown = passwordProperty.value
            }
            onMouseReleased = EventHandler {
                if(passwordProperty.value != "" &&passwordProperty.value != null)
                    textShown = "⦁⦁⦁⦁⦁⦁⦁⦁"
            }

        }
    }
}