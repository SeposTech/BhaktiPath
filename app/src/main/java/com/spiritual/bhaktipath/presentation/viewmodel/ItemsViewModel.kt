package com.spiritual.bhaktipath.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spiritual.bhaktipath.domain.usecase.ItemsUseCase
import com.spiritual.bhaktipath.utils.ItemsData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ItemsViewModel @Inject constructor(private val itemsUseCase: ItemsUseCase) : ViewModel() {

    sealed interface UiState {
        object Loading : UiState
        data class Success(val items: List<ItemsData>) : UiState
        data class Error(val message: String) : UiState
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState


    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab


    fun setSelectedTab(tabIndex: Int) {
        _selectedTab.value = tabIndex
    }

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