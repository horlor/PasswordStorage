package com.gmail.horloraa.passwordstore.viewmodel

import com.gmail.horloraa.passwordstore.model.PasswordRecord
import com.gmail.horloraa.passwordstore.repository.PasswordRepository
import javafx.beans.property.SimpleStringProperty
import javafx.collections.transformation.FilteredList
import tornadofx.*

class MainViewModel : ViewModel() {
    val passwords   = SortedFilteredList( PasswordRepository.all)

    val selected : SelectedPasswordViewModel by inject()

    val searchStringProperty = SimpleStringProperty()
    var searchString by searchStringProperty

    fun removeSelected(){
        selected.item?.let {
            PasswordRepository.delete(it)
        }
    }


    init{
        searchString = ""
        passwords.filterWhen(searchStringProperty,
                { query, item ->
                    item.webPage.contains(searchString) || item.tag.contains(searchString)
                })
    }
}