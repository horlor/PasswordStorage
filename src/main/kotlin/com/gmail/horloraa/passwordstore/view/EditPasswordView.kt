package com.gmail.horloraa.passwordstore.view

import com.gmail.horloraa.passwordstore.viewmodel.PasswordViewModel
import com.gmail.horloraa.passwordstore.viewmodel.SelectedPasswordViewModel
import javafx.scene.Parent
import tornadofx.*
import tornadofx.Form

class EditPasswordView() : View("My View") {
    private val viewModel : SelectedPasswordViewModel by inject()
    override val root = vbox{
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
        hbox(10){
            button("Save"){
                disableProperty().bind(!viewModel.dirty)
            }.action{
                viewModel.save()
                this@EditPasswordView.close()
            }
            button("Cancel").action {
                viewModel.rollback()
                this@EditPasswordView.close()
            }
        }
    }

    override fun onUndock() {
        super.onUndock()
        viewModel.rollback()
    }
}
