package com.gmail.horloraa.passwordstore.viewmodel

import com.gmail.horloraa.passwordstore.services.PasswordService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SelectedPasswordViewModel : PasswordViewModel(){
    private val passwordService = PasswordService
    fun save(){
        commit();
        item?.let{
            GlobalScope.launch {
                passwordService.update(item)
            }
        }
    }
}