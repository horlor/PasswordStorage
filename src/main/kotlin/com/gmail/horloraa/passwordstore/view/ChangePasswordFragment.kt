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
                    passwordfield(viewModel.passwordProperty)
                }
                field("New password again"){
                    passwordfield (viewModel.passwordAgainProperty)
                }
            }
        }
        hbox{
            button("Change"){
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