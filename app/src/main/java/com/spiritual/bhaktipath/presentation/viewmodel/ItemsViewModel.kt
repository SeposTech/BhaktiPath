package com.spiritual.bhaktipath.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spiritual.bhaktipath.domain.usecase.ItemsUseCase
import com.spiritual.bhaktipath.utils.ItemsData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ItemsViewModel @Inject constructor(private val itemsUseCase: ItemsUseCase) : ViewModel() {

    sealed interface UiState {
        object Loading : UiState
        data class Success(val items: List<ItemsData>) : UiState
        data class Error(val message: String) : UiState
    }


    private val _uiState = mutableStateOf<UiState>(UiState.Loading)
    val uiState: androidx.compose.runtime.State<UiState> = _uiState

    init {
        loadItems()
    }

    private fun loadItems() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val items = itemsUseCase.invoke()
                _uiState.value = UiState.Success(items)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}