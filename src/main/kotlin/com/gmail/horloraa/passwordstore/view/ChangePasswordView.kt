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
                    passwordfield(viewModel.password)
                }
                field("New password again"){
                    passwordfield (viewModel.passwordAgain)
                            .validator {
                                if (viewModel.password.value != viewModel.passwordAgain.value)
                                    error("The two passwords's must match")
                                else null
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
                disableProperty().bind(!viewModel.dirty)
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