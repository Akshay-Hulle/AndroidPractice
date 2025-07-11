package com.example.composeproject

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    var name = mutableStateOf("")
    var count = mutableStateOf(0)

    // State list to hold added names
    var nameList = mutableStateListOf<String>()

    fun onNameChange(newName: String) {
        name.value = newName
    }

    fun increaseCounter() {
        count.value++
    }

    fun addNameToList() {
        if (name.value.isNotBlank()) {
            nameList.add(name.value.trim())
            name.value = "" // Clear the input
        }
    }

}