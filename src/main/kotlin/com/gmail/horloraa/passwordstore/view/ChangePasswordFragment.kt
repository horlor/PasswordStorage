package com.gmail.horloraa.passwordstore.view

import com.gmail.horloraa.passwordstore.viewmodel.ChangePasswordViewModel
import javafx.scene.Parent
import javafx.scene.layout.Priority
import tornadofx.*

class ChangePasswordFragment : Fragment("Change your password"){
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
                                if (viewModel.password != viewModel.passwordAgain)
                                    error("The two passwords's must match")
                                else null
                            }
                }
            }
        }
        hbox{
            button("Change"){
                disableProperty().bind(!viewModel.dirty)
                action{
                    viewModel.commit()
                    this@ChangePasswordFragment.close()
                }

            }
            vbox {
                hgrow = Priority.ALWAYS
            }
            button("Cancel"){
                action{
                    this@ChangePasswordFragment.close()
                }
            }
        }
    }
}