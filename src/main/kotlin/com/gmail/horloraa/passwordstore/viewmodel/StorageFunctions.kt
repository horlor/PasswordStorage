package com.gmail.horloraa.passwordstore.viewmodel

import com.gmail.horloraa.passwordstore.services.PasswordService
import javafx.stage.FileChooser
import tornadofx.FileChooserMode
import tornadofx.ItemViewModel
import tornadofx.ViewModel
import tornadofx.chooseFile

fun openStorageFun(): Boolean{
    val files = chooseFile(
            title = "Open storage file",
            mode = FileChooserMode.Single,
            filters = arrayOf(FileChooser.ExtensionFilter("Database files","*.db"))
    )
    if(!files.isEmpty()){
        val file = files.first()
        PasswordService.openRepository(file.absolutePath)
        return true
    }
    return false
}

fun createStorageFun(): Boolean{
    val files = chooseFile(
            title = "Create storage file",
            mode = FileChooserMode.Save,
            filters = arrayOf(FileChooser.ExtensionFilter("Database files","*.db"))
    )
    if(!files.isEmpty()){
        val file = files.first()
        PasswordService.createRepository(file.absolutePath)
        return true;
    }
    return false
}

open class StorageViewModel : ViewModel(){
    fun createStorage() = createStorageFun()
    fun openStorage() = openStorageFun()
}

