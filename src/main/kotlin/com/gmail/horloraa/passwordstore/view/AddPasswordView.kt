package com.gmail.horloraa.passwordstore.view

import com.gmail.horloraa.passwordstore.extension.asyncAction
import com.gmail.horloraa.passwordstore.view.controls.PasswordFieldWithShow
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
                field("Password") { this+=PasswordFieldWithShow(viewModel.password) }
                field("Tag"){textfield(viewModel.tag) }
                field("Comment") { textarea(viewModel.comment) }
            }
        }
        hbox(10) {
            button("Add") {
                disableProperty().bind(!viewModel.dirty)
            }.action {
                viewModel.add()
                this@AddPasswordView.close()
            }
            button("Cancel").action {
                this@AddPasswordView.close()
            }
        }
    }

}

