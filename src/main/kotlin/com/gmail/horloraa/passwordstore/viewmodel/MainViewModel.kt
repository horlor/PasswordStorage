package com.gmail.horloraa.passwordstore.viewmodel

import com.gmail.horloraa.passwordstore.model.PasswordRecord
import com.gmail.horloraa.passwordstore.repository.PasswordRepository
import tornadofx.ViewModel

class MainViewModel : ViewModel() {
    val passwords  = PasswordRepository.all

    val new = PasswordViewModel(PasswordRecord().apply {
    })

    fun add(){
        new.commit()
        val record = new.item
        passwords.add(record);
        PasswordRepository.add(record)
    }
}