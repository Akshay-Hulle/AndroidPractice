package com.supremology.travelguide2.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supremology.travelguide2.domain.model.Place
import com.supremology.travelguide2.domain.usecase.SearchDestinationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchDestinationUseCase
) : ViewModel() {

    private val _suggestions = MutableLiveData<List<Place>>()
    val suggestions: LiveData<List<Place>> = _suggestions

    fun search(query: String) {
        viewModelScope.launch {
            val result = searchUseCase(query)
            _suggestions.value = result
        }
    }
}
