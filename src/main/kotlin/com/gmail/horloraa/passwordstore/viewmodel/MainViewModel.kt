package com.gmail.horloraa.passwordstore.viewmodel

import com.gmail.horloraa.passwordstore.services.PasswordService
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class MainViewModel : StorageViewModel() {
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

    init{
        searchString = ""
        passwords.filterWhen(searchStringProperty
        ) { query, item ->
            item.webPage.contains(searchString) || item.tag.contains(searchString)
        }
    }

}