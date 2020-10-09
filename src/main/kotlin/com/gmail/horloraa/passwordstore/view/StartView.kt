package com.gmail.horloraa.passwordstore.view

import com.gmail.horloraa.passwordstore.viewmodel.StartViewModel
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.stage.Modality
import tornadofx.*
import java.io.FileInputStream

class StartView : View("Password storage") {
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
        prefWidth = 400.0
        prefHeight = 300.0
    }

    override fun onDock() {
        super.onDock()
        if(!viewModel.fileExists){
            this.close()
            find<CreateView>().openWindow()
        }
    }

    override fun onBeforeShow() {
        super.onBeforeShow()
    }
}
