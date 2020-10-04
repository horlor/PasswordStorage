package com.gmail.horloraa.passwordstore.viewmodel

import com.gmail.horloraa.passwordstore.model.PasswordRecord
import com.gmail.horloraa.passwordstore.repository.PasswordRepository

class AddPasswordViewModel : PasswordViewModel(PasswordRecord()) {
    fun add(){
        commit()
        item?.let {
            PasswordRepository.add(it)
        }
    }
}