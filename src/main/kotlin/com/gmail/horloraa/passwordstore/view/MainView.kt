package com.gmail.horloraa.passwordstore.view

import com.gmail.horloraa.passwordstore.extension.changeWindowWithNewScope
import com.gmail.horloraa.passwordstore.model.PasswordRecord
import com.gmail.horloraa.passwordstore.viewmodel.MainViewModel
import javafx.beans.property.SimpleIntegerProperty
import javafx.scene.control.ContextMenu
import javafx.scene.image.Image
import javafx.scene.layout.Priority
import javafx.stage.FileChooser
import tornadofx.*
import javax.xml.soap.Detail

class MainView : View("Password store") {

    private val viewModel =  MainViewModel()

    override val root = borderpane {
        top{
            menubar{
                menu("File"){
                    item("Open storage"){
                        action{
                            viewModel.openStorage()
                            this@MainView.changeWindowWithNewScope<LoginView>()
                        }
                    }
                    item("Create storage"){
                        action{
                            viewModel.createStorage()
                            this@MainView.changeWindowWithNewScope<RegisterView>()
                        }
                    }
                    item("Exit"){
                        action{
                            this@MainView.close()
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
                    this@MainView.find<AddPasswordView> {
                        this.openModal()
                    }
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

                contextmenu{
                    item("Remove").action {
                        viewModel.removeSelected()
                    }
                    item("Edit").action {
                        this@MainView.find<EditPasswordView> {
                            this.openModal()
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