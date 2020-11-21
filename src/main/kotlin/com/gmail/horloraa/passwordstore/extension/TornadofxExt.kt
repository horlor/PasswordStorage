package com.gmail.horloraa.passwordstore.extension

import javafx.stage.StageStyle
import tornadofx.*

inline fun <reified T : View>View.changeWindowWithNewScope(){
    val scope = Scope()
    this.close();
    this.find(T::class.java, scope = scope).openWindow()
}

inline fun <reified T> View.openModalInNewScope() where T : UIComponent{
    val scope = Scope()
    this.find(T::class.java, scope = scope).openModal(stageStyle = StageStyle.UTILITY)
}