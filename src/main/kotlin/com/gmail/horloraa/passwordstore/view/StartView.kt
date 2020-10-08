package com.gmail.horloraa.passwordstore.view

import com.gmail.horloraa.passwordstore.viewmodel.StartViewModel
import javafx.stage.Modality
import tornadofx.*

class StartView : View("My View") {
    val viewModel: StartViewModel by inject()
    override val root = vbox {
        label("Password Storage")
        passwordfield(viewModel.passwordProperty)
        button("Login").action{
            viewModel.onLogin()
            this@StartView.close();
            find<MainView>().openWindow()

        }
        prefWidth = 800.0
        prefHeight = 500.0
    }

    override fun onDock() {
        super.onDock()
        if(!viewModel.fileExists)
            find<CreateView>().openWindow()
    }
}
