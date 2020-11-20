package com.gmail.horloraa.passwordstore.viewmodel

import com.gmail.horloraa.passwordstore.model.PasswordRecord
import com.gmail.horloraa.passwordstore.services.PasswordService

class AddPasswordViewModel : PasswordViewModel() {
    val passwordService = PasswordService
    init{
        item = PasswordRecord()
    }
    fun add(){
        commit()
        item?.let {
            passwordService.add(it)
        }
    }
}