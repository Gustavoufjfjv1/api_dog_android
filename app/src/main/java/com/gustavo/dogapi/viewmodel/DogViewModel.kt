package com.gustavo.dogapi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gustavo.dogapi.model.DogDetailResponse
import com.gustavo.dogapi.network.RetrofitClient
import kotlinx.coroutines.launch

sealed interface DogUiState {
    object Loading : DogUiState
    data class Success(val dog: DogDetailResponse) : DogUiState
    object NotFound : DogUiState
}

class DogViewModel : ViewModel() {
    var uiState: DogUiState by mutableStateOf(DogUiState.Loading)
        private set

    init {
        buscar("hound")
    }

    fun buscar(termo: String) {
        val cleanTerm = termo.trim().lowercase()
        if (cleanTerm.isBlank()) return

        viewModelScope.launch {
            uiState = DogUiState.Loading
            uiState = try {
                val dog = RetrofitClient.apiService.getDog(cleanTerm)
                dog.breedName = cleanTerm
                DogUiState.Success(dog)
            } catch (e: Exception) {
                DogUiState.NotFound
            }
        }
    }
}
