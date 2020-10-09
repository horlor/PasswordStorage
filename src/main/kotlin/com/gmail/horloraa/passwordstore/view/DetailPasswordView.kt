package com.gmail.horloraa.passwordstore.view

import com.gmail.horloraa.passwordstore.Styles
import com.gmail.horloraa.passwordstore.view.controls.PasswordLabel
import com.gmail.horloraa.passwordstore.viewmodel.MainViewModel
import com.gmail.horloraa.passwordstore.viewmodel.PasswordViewModel
import com.gmail.horloraa.passwordstore.viewmodel.SelectedPasswordViewModel
import javafx.geometry.Orientation
import javafx.scene.text.FontWeight
import tornadofx.*

class DetailPasswordView() : View("Detailview for password") {
    private val viewModel: SelectedPasswordViewModel by inject()
    override val root = vbox{
        prefWidth=250.0
        form {
            fieldset() {
                field("Webpage") {
                    label(viewModel.webPage)
                }
                field("Username") {
                    label(viewModel.username)
                }
                field("Email") {
                    label(viewModel.email)
                }
                field("Password") {
                    this.add(PasswordLabel(viewModel.password))
                }
                field("Tag"){
                    label(viewModel.tag)
                }
            }
            fieldset(labelPosition = Orientation.VERTICAL) {
                field("Comment",Orientation.VERTICAL){
                    legend?.style{
                        fontWeight = FontWeight.BOLD
                    }
                    text(viewModel.comment){
                        wrappingWidth = 230.0
                    }
                }
            }

        }

    }
}

