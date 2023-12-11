package com.example.jumiachallange.ui.theme.screens.results

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jumiachallange.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class ResultsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {


    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private val _searchedProducts = MutableStateFlow<PagingData<Result>>(PagingData.empty())
    val searchedProducts = _searchedProducts

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun searchProducts(query: String) {

        viewModelScope.launch {
            repository.searchProducts(repository = repository, query = query).cachedIn(viewModelScope).collect {
                _searchedProducts.value = it
            }
        }
    }

}