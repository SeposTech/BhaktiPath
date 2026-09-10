package com.spiritual.bhaktipath.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spiritual.bhaktipath.domain.usecase.DetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val detailUseCase: DetailUseCase,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {

    sealed interface UiState {
        object Loading : UiState
        data class Success(val detail: String?) : UiState
        data class Error(val message: String) : UiState
    }


    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    val itemId = savedStateHandle.get<Int>("itemId") ?: 0

    init {
        loadDetail(itemId)
    }


    fun loadDetail(itemId: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val detail = detailUseCase.invoke(itemId)
                _uiState.value = UiState.Success(detail?.content)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }


}