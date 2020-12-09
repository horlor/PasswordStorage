package com.gmail.horloraa.passwordstore.extension

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.stage.StageStyle
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.javafx.JavaFx
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

fun Button.asyncAction(op: suspend()->Unit){
    val eventActor = GlobalScope.actor<ActionEvent>(Dispatchers.Main, capacity = Channel.CONFLATED) {
        for (event in channel) op() // pass event to action
    }
    onAction = EventHandler {
        eventActor.offer(it);
    }
}
