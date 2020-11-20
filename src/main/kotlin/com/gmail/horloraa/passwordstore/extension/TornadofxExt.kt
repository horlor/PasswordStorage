package com.gmail.horloraa.passwordstore.extension

import tornadofx.*

inline fun <reified T : View>View.changeWindowWithNewScope(){
    val scope = Scope()
    this.close();
    this.find(T::class.java, scope = scope).openWindow()
}