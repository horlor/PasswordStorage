package com.gmail.horloraa.passwordstore.view

import com.gmail.horloraa.passwordstore.model.PasswordRecord
import com.gmail.horloraa.passwordstore.viewmodel.MainViewModel
import javafx.beans.property.SimpleIntegerProperty
import javafx.scene.control.ContextMenu
import javafx.scene.layout.Priority
import tornadofx.*

class MainView : View("Password store") {

    private val viewModel: MainViewModel by inject()

    override val root = borderpane {
        bottom{
            label(viewModel.searchStringProperty)
        }
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
                readonlyColumn("Webpage", PasswordRecord::webPage)
                readonlyColumn("Username", PasswordRecord::username)
                readonlyColumn("Email", PasswordRecord::email)

                viewModel.selected.rebindOnChange(this) { record ->
                    item = record
                }

                contextmenu{
                    item("Remove").action {
                        viewModel.removeSelected()
                    }
                    item("Edit").action {

                    }
                }
            }
        }
        right=gridpane{
            prefWidth = 200.0
            row{
                label("Webpage"){
                    gridpaneConstraints{
                        columnIndex = 0
                    }
                }
                label(viewModel.selected.webPage){
                    gridpaneConstraints{
                        columnIndex = 1
                    }
                }
            }
            row{
                label("Username"){
                    gridpaneConstraints{
                        columnIndex = 0
                    }
                }
                label(viewModel.selected.username){
                    gridpaneConstraints{
                        columnIndex = 1
                    }
                }
            }
            row{
                label("Password"){
                    gridpaneConstraints{
                        columnIndex = 0
                    }
                }
                label(viewModel.selected.password){
                    gridpaneConstraints{
                        columnIndex = 1
                    }
                }
            }
            row{
                label("Email"){
                    gridpaneConstraints{
                        columnIndex = 0
                    }
                }
                label(viewModel.selected.email){
                    gridpaneConstraints{
                        columnIndex = 1
                    }
                }
            }
            row("comment")
            row{
                text(viewModel.selected.comment)
            }

        }


    }
}