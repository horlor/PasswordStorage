package com.gmail.horloraa.passwordstore.viewmodel

import com.gmail.horloraa.passwordstore.services.PasswordService
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class MainViewModel : ViewModel() {
    val passwordService = PasswordService
    val passwords   = SortedFilteredList( passwordService.getAll())

    val selected : SelectedPasswordViewModel by inject()

    val searchStringProperty = SimpleStringProperty()
    var searchString by searchStringProperty

    fun removeSelected(){
        selected.item?.let {
            passwordService.delete(it);
        }
    }

    fun openStorage(){
        //TODO
        val files = chooseFile(title="Open storage file",mode = FileChooserMode.Single,filters =  arrayOf())
        if(!files.isEmpty()){
            val file = files.first()
            println(file.absolutePath)
            passwordService.openRepository(file.absolutePath)
        }

    }

    fun createStorage(){
        val files = chooseFile(title="Open storage file",mode = FileChooserMode.Single,filters =  arrayOf())
        if(!files.isEmpty()){
            val file = files.first()
            passwordService.createRepository(file.absolutePath)
        }

    }


    init{
        searchString = ""
        passwords.filterWhen(searchStringProperty
        ) { query, item ->
            item.webPage.contains(searchString) || item.tag.contains(searchString)
        }
    }

}