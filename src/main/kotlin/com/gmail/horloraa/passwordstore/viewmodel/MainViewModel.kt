package com.gmail.horloraa.passwordstore.viewmodel

import com.gmail.horloraa.passwordstore.services.PasswordService
import javafx.beans.property.SimpleStringProperty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.launch
import tornadofx.*

class MainViewModel : StorageViewModel() {
    val passwordService = PasswordService
    val passwords   = SortedFilteredList( passwordService.getAll())

    val selected : SelectedPasswordViewModel by inject()

    val searchStringProperty = SimpleStringProperty()
    var searchString by searchStringProperty

    val placeholderProperty = SimpleStringProperty()
    var placeholder by placeholderProperty

    fun removeSelected(){
        selected.item?.let {
            GlobalScope.launch {
                passwordService.delete(it);
            }
        }
    }

    init{
        placeholder="Loading..."
        GlobalScope.launch{
            passwordService.loadItems()
            placeholder = "No content"
        }
        searchString = ""
        passwords.filterWhen(searchStringProperty
        ) { query, item ->
            item.webPage.contains(searchString) || item.tag.contains(searchString)
        }
    }

}