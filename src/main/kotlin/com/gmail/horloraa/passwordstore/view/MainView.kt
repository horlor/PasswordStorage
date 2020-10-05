package com.gmail.horloraa.passwordstore.view

import com.gmail.horloraa.passwordstore.model.PasswordRecord
import com.gmail.horloraa.passwordstore.viewmodel.MainViewModel
import javafx.beans.property.SimpleIntegerProperty
import javafx.scene.control.ContextMenu
import javafx.scene.image.Image
import javafx.scene.layout.Priority
import tornadofx.*
import javax.xml.soap.Detail

class MainView : View("Password store") {

    private val viewModel: MainViewModel by inject()

    override val root = borderpane {
        center =vbox{
            hbox {
                button("Add").action {
                    this@MainView.find<AddPasswordView> {
                        this.openModal()
                    }
                }
                textfield(viewModel.searchStringProperty) {
                    hgrow = Priority.ALWAYS
                }
            }
            tableview(viewModel.passwords) {
                viewModel.passwords.bindTo(this)
                smartResize()
                readonlyColumn("Email", PasswordRecord::email)
                readonlyColumn("Webpage", PasswordRecord::webPage).remainingWidth()
                readonlyColumn("Username", PasswordRecord::username)
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

}