package com.gmail.horloraa.passwordstore.viewmodel

import com.gmail.horloraa.passwordstore.model.PasswordRecord
import com.gmail.horloraa.passwordstore.services.PasswordService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.launch

class AddPasswordViewModel : PasswordViewModel() {
    val passwordService = PasswordService
    init{
        item = PasswordRecord()
    }
    fun add(){
        commit()
        item?.let {
            GlobalScope.launch{
                passwordService.add(it)
            }
        }
    }
}