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

    suspend fun login(password: String): Boolean{
        return repository.login(password);
    }

    //This only gives a reference you must load the data in order to get the Items.
    fun getAll() = repository.all

    suspend fun loadItems() = repository.loadRecords()

    suspend fun add(record: PasswordRecord): PasswordRecord = repository.add(record)

    suspend fun update(record: PasswordRecord) = repository.update(record)

    suspend fun delete(record:PasswordRecord) = repository.delete(record)

    fun checkTableExist() = repository.checkTableExist()

    suspend fun createTable(password: String) = repository.createTable(password)

    suspend fun changePassword(password: String) = repository.changePassword(password)
}