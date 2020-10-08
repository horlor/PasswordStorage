package com.gmail.horloraa.passwordstore.view

import com.gmail.horloraa.passwordstore.viewmodel.AddPasswordViewModel
import com.gmail.horloraa.passwordstore.viewmodel.PasswordViewModel
import tornadofx.*

class AddPasswordView() : View("Add new record") {
    private val viewModel =  AddPasswordViewModel()

    override val root = vbox {
        form {
            fieldset {
                field("Webpage") { textfield(viewModel.webPage) }
                field("Username") { textfield(viewModel.username) }
                field("Email"){ textfield(viewModel.email) }
                field("Password") { textfield(viewModel.password) }
                field("Tag"){textfield(viewModel.tag) }
                field("Comment") { textarea(viewModel.comment) }
            }
        }
        hbox(10) {
            button("Add").action {
                viewModel.add()
                this@AddPasswordView.close()
            }
            button("Cancel").action {
                this@AddPasswordView.close()
            }
        }
    }

}

