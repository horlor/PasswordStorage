package com.gmail.horloraa.passwordstore.view

import com.gmail.horloraa.passwordstore.extension.changeWindowWithNewScope
import com.gmail.horloraa.passwordstore.extension.openModalInNewScope
import com.gmail.horloraa.passwordstore.model.PasswordRecord
import com.gmail.horloraa.passwordstore.viewmodel.MainViewModel
import javafx.scene.layout.Priority
import tornadofx.*

class MainView : View("Password store") {

    private val viewModel =  MainViewModel()

    override val root = borderpane {
        top{
            menubar{
                menu("File"){
                    item("Open storage"){
                        action{
                            if (viewModel.openStorage())
                                this@MainView.changeWindowWithNewScope<LoginView>()
                        }
                    }
                    item("Create storage"){
                        action{
                            if(viewModel.createStorage())
                                this@MainView.changeWindowWithNewScope<RegisterView>()
                        }
                    }
                    item("Exit"){
                        action{
                            this@MainView.close()
                        }
                    }
                }
                menu("Storage"){
                    item("Change password"){
                        action {
                            this@MainView.openModalInNewScope<ChangePasswordView>()
                        }
                    }
                }

            }
        }
        center =vbox{
            style{
                padding = box(5.px)
            }
            hbox {
                button("Add").action {
                    this@MainView.openModalInNewScope<AddPasswordView>()
                }
                textfield(viewModel.searchStringProperty) {
                    hgrow = Priority.ALWAYS
                    this.promptText = "Search..."
                }
            }
            tableview(viewModel.passwords) {
                viewModel.passwords.bindTo(this)
                smartResize()
                readonlyColumn("Email", PasswordRecord::email)
                readonlyColumn("Webpage", PasswordRecord::webPage).remainingWidth()
                readonlyColumn("Username", PasswordRecord::username)
                readonlyColumn("Tag", PasswordRecord::tag)
                prefWidth = 400.0
                viewModel.selected.rebindOnChange(this) { record ->
                    item = record
                }
                placeholder = label(viewModel.placeholderProperty)
                contextmenu{
                    item("Remove").action {
                        viewModel.removeSelected()
                    }
                    item("Edit").action {
                        this@MainView.find<EditPasswordView> {
                            this.openModal();
                        }
                    }
                }
            }
        }
        right<DetailPasswordView>()
    }

    override fun onDock() {
        super.onDock()
        currentWindow?.sizeToScene()

    }

}