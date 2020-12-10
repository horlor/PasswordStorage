package com.gmail.horloraa.passwordstore.view

import com.gmail.horloraa.passwordstore.extension.asyncAction
import com.gmail.horloraa.passwordstore.extension.changeWindowWithNewScope
import com.gmail.horloraa.passwordstore.viewmodel.RegisterViewModel
import javafx.beans.binding.Bindings
import javafx.beans.binding.BooleanBinding
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import tornadofx.*

class RegisterView : View("Creating store") {
    val viewModel : RegisterViewModel by inject()
    override val root = vbox {
        style {
            padding = box(8.px)
        }
        text("There is no store set up on your device, please give the password you want to use for it." +
                "\nNote that if you forget the given password there is no way to retrieve your data.")
        form{
            fieldset {
                field("Password"){
                    passwordfield(viewModel.password).required()
                }
                field("Password again"){
                    passwordfield(viewModel.passwordAgain).apply{
                        validator {
                            if(viewModel.password.value != viewModel.passwordAgain.value)
                                error("The two passwords's must match")
                            else null
                        }
                        required()
                    }

                }
            }
            vbox{
                vboxConstraints {
                    alignment = Pos.CENTER
                }
                button("Register"){
                    enableWhen(viewModel.valid)
                    asyncAction{
                        viewModel.createDatabase()
                        this@RegisterView.replaceWith<MainView>()
                    }
                }
            }
        }
        hbox {
            label("Open storage"){
                onLeftClick {
                    if(viewModel.openStorage())
                    this@RegisterView.changeWindowWithNewScope<LoginView>()
                }
            }
            vbox{
                hgrow = Priority.ALWAYS
            }
            label("Create storage"){
                onLeftClick {
                    if(viewModel.createStorage())
                    this@RegisterView.changeWindowWithNewScope<RegisterView>()
                }
            }
        }
    }

    override fun onDock() {
        super.onDock()
        currentWindow?.sizeToScene()
    }
}
