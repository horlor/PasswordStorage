package com.gmail.horloraa.passwordstore.view

import com.gmail.horloraa.passwordstore.extension.asyncAction
import com.gmail.horloraa.passwordstore.extension.changeWindowWithNewScope
import com.gmail.horloraa.passwordstore.viewmodel.RegisterViewModel
import javafx.scene.layout.Priority
import tornadofx.*

class RegisterView : View("Creating store") {
    val viewModel : RegisterViewModel by inject()
    override val root = vbox {
        text("There is no store set up on your device, please give the password you want to use for it." +
                "\nNote that if you forget the given password there is no way to retrieve your data.")
        form{
            fieldset {
                field("Password"){
                    passwordfield(viewModel.password)
                }
                field("Password again"){
                    passwordfield(viewModel.passwordAgain).validator {
                        if(viewModel.password != viewModel.passwordAgain)
                            error("The two passwords's must match")
                        else null
                    }
                }
            }
            button("Register"){
                disableProperty().bind(!viewModel.dirty)
                asyncAction{
                    viewModel.createDatabase()
                    this@RegisterView.replaceWith<MainView>()
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
