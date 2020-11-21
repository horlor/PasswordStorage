package com.gmail.horloraa.passwordstore.view

import com.gmail.horloraa.passwordstore.extension.changeWindowWithNewScope
import com.gmail.horloraa.passwordstore.viewmodel.LoginViewModel
import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.layout.Priority
import tornadofx.*
import javax.swing.text.html.ImageView

class LoginView : View("Password storage") {
    val viewModel: LoginViewModel by inject()
    override val root =
        vbox {
            style(append = false){
                padding = box(10.px)
                fontSize = 14.px
            }

            label("Please give the password to access the store.")
            hbox{
                style{
                    padding = box(10.px)
                }
                passwordfield(viewModel.passwordProperty){
                    hgrow = Priority.ALWAYS
                }
            }
            hbox {
                style {
                    padding = box(10.px)
                    alignment = Pos.CENTER
                }
                button("Login") {
                    action {
                        if (viewModel.onLogin()) {
                            this@LoginView.replaceWith<MainView>();
                        } else {
                            error("The password is wrong", "The password you given is not matching the stored. Please try again")
                        }
                    }
                }

            }
            hbox {
                label("Open storage"){
                    onLeftClick {
                        if(viewModel.openStorage())
                            this@LoginView.changeWindowWithNewScope<LoginView>()
                    }
                }
                vbox{
                    hgrow = Priority.ALWAYS
                }
                label("Create storage"){
                    onLeftClick {
                        if(viewModel.createStorage())
                            this@LoginView.changeWindowWithNewScope<RegisterView>()
                    }
                }
            }
    }

    override fun onDock() {
        super.onDock()
        if(!viewModel.fileExists()){
            this.replaceWith<RegisterView>()
        }
        currentWindow?.sizeToScene()
    }
}
