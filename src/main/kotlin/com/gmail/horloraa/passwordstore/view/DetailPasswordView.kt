package com.gmail.horloraa.passwordstore.view

import com.gmail.horloraa.passwordstore.viewmodel.MainViewModel
import com.gmail.horloraa.passwordstore.viewmodel.PasswordViewModel
import com.gmail.horloraa.passwordstore.viewmodel.SelectedPasswordViewModel
import tornadofx.*

class DetailPasswordView() : View("Detailview for password") {
    private val viewModel: SelectedPasswordViewModel by inject()
    override val root = vbox{
        prefWidth=400.0
        form {
            fieldset {
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
                    label(viewModel.password)
                }
                label("Comment")
                text(viewModel.comment)
            }
        }

    }
}
