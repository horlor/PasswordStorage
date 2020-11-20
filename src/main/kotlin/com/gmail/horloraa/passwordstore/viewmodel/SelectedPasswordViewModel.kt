package com.gmail.horloraa.passwordstore.viewmodel

import com.gmail.horloraa.passwordstore.services.PasswordService

class SelectedPasswordViewModel : PasswordViewModel(){
    val passwordService = PasswordService
    fun save(){
        commit();
        item?.let{
            passwordService.update(item)
        }
    }
}