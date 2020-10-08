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
            if(viewModel.onLogin()){
                this@StartView.close();
                find<MainView>().openWindow()
            }
            else{
                error("The password is wrong","The password you given is not matching the stored. Please try again")
            }
        }
        prefWidth = 800.0
        prefHeight = 500.0
    }

    override fun onDock() {
        super.onDock()
        if(!viewModel.fileExists){
            this.close()
            find<CreateView>().openWindow()
        }
    }
}
