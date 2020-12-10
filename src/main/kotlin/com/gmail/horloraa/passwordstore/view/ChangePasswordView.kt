package com.gmail.horloraa.passwordstore.view

import com.gmail.horloraa.passwordstore.extension.asyncAction
import com.gmail.horloraa.passwordstore.viewmodel.ChangePasswordViewModel
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.control.ProgressIndicator
import javafx.scene.layout.Priority
import tornadofx.*

class ChangePasswordView : View("Change your password"){
    val viewModel: ChangePasswordViewModel by inject()
    override val root: Parent = vbox {
        style{
            padding = box(10.px)
        }
        form(){
            fieldset {
                field("New Password"){
                    passwordfield(viewModel.password).required()
                }
                field("New password again"){
                    passwordfield (viewModel.passwordAgain).apply{
                        validator {
                            if (viewModel.password.value != viewModel.passwordAgain.value)
                                error("The two passwords's must match")
                            else null
                        }
                        required()
                    }

                }
            }
        }
        hbox{
            hboxConstraints {
                alignment = Pos.CENTER
            }
            hiddenWhen(!viewModel.isBusyProperty)
            managedWhen(viewModel.isBusyProperty)
            progressindicator{
                this.progress = ProgressIndicator.INDETERMINATE_PROGRESS
            }
        }
        hbox{
            hiddenWhen(viewModel.isBusyProperty)
            managedWhen(!viewModel.isBusyProperty)
            button("Change"){
                enableWhen(viewModel.valid)
                asyncAction{
                    viewModel.onChange()
                    this@ChangePasswordView.close()
                }

            }
            vbox {
                hgrow = Priority.ALWAYS
            }
            button("Cancel"){
                action{
                    this@ChangePasswordView.close()
                }
            }
        }
    }
}