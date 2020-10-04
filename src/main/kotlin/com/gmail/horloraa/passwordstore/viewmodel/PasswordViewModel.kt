package com.gmail.horloraa.passwordstore.viewmodel

import com.gmail.horloraa.passwordstore.model.PasswordRecord
import tornadofx.ItemViewModel

open class PasswordViewModel(record: PasswordRecord) : ItemViewModel<PasswordRecord>(record){
    val username = bind(PasswordRecord::usernameProperty)
    val password = bind(PasswordRecord::passwordProperty)
    val email = bind(PasswordRecord::emailProperty)
    val comment = bind(PasswordRecord::commentProperty)
    val webPage = bind(PasswordRecord::webPageProperty)
}