package com.gmail.horloraa.passwordstore.viewmodel

import com.gmail.horloraa.passwordstore.repository.PasswordRepository

class SelectedPasswordViewModel : PasswordViewModel(){
    fun save(){
        commit();
        item?.let{
            PasswordRepository.update(item)
        }
    }
}