package com.gmail.horloraa.passwordstore.services

import com.gmail.horloraa.passwordstore.model.PasswordRecord
import com.gmail.horloraa.passwordstore.repository.PasswordRepository
import tornadofx.*
import java.io.File

object PasswordService {
    private var repository: PasswordRepository = PasswordRepository();


    fun openRepository(path: String){
        repository = PasswordRepository(path)
    }

    fun createRepository(path: String){
        val file = File(path)
        if(file.exists()){
            file.delete()
        }
        openRepository(path);
    }

    fun login(password: String): Boolean{
        return repository.login(password);
    }

    fun getAll() = repository.all

    fun add(record: PasswordRecord): PasswordRecord = repository.add(record)

    fun update(record: PasswordRecord) = repository.update(record)

    fun delete(record:PasswordRecord) = repository.delete(record)

    fun checkTableExist() = repository.checkTableExist()

    fun createTable(password: String) = repository.createTable(password)

    fun changePassword(password: String) = repository.changePassword(password)
}