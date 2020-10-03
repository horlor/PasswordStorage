package com.gmail.horloraa.passwordstore.view

import com.gmail.horloraa.passwordstore.model.PasswordRecord
import com.gmail.horloraa.passwordstore.viewmodel.MainViewModel
import javafx.beans.property.SimpleIntegerProperty
import tornadofx.*

class MainView : View("Password store") {

    //private val controller: MainController by inject()
    private val viewModel: MainViewModel by inject()

    private val model = object : ViewModel() {
        val counter = SimpleIntegerProperty()
    }



    override val root = borderpane {
        center = tableview(viewModel.passwords) {
            readonlyColumn("Webpage", PasswordRecord::webPage)
            readonlyColumn("Username", PasswordRecord::username)
            readonlyColumn("Password", PasswordRecord::password)
            readonlyColumn("Email", PasswordRecord::email)
            readonlyColumn("Comment", PasswordRecord::comment)
        }
        right {
            vbox{
                form {
                    fieldset {
                        field("Username") { textfield(viewModel.new.username) }
                        field("Password") { textfield(viewModel.new.password) }
                        field("Email") { textfield(viewModel.new.email) }
                        field("Webpage") { textfield(viewModel.new.webPage) }
                        field("Comment") { textfield(viewModel.new.comment) }
                    }
                }
                button("Add").action {
                    viewModel.add()
                }
            }
        }
    }
}