package com.supremology.fakestore2.ui

import androidx.lifecycle.*
import com.supremology.fakestore2.data.model.Product
import com.supremology.fakestore2.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val repository = ProductRepository()

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            try {
                _products.value = repository.getProducts()
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Failed to load products: ${e.message}"
            }
        }
    }
}
