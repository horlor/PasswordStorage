package com.gmail.horloraa.passwordstore.view

import com.gmail.horloraa.passwordstore.viewmodel.AddPasswordViewModel
import com.gmail.horloraa.passwordstore.viewmodel.PasswordViewModel
import tornadofx.*

class AddPasswordView() : View("Add new record") {
    private val viewModel =  AddPasswordViewModel()

    override val root = vbox {
        form {
            fieldset {
                field("Username") { textfield(viewModel.username) }
                field("Password") { textfield(viewModel.password) }
                field("Email") { textfield(viewModel.email) }
                field("Webpage") { textfield(viewModel.webPage) }
                field("Comment") { textfield(viewModel.comment) }
            }
        }
        borderpane{
            left {
                button("Add").action{
                    viewModel.add()
                    this@AddPasswordView.close()
                }
            }
            right{
                button("Cancel").action {
                    this@AddPasswordView.close()
                }
            }
        }

    }
}
