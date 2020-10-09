package com.gmail.horloraa.passwordstore.view

import com.gmail.horloraa.passwordstore.viewmodel.RegisterViewModel
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
                        if(viewModel.password.value != viewModel.passwordAgain.value)
                            error("The two passwords's must match")
                        else null
                    }
                }
            }
            button("Register"){
                disableProperty().bind(!viewModel.dirty)
                action{
                viewModel.createDatabase()
                this@RegisterView.close()
                this@RegisterView.find<MainView>().openWindow()
                }
            }
        }
    }
}
