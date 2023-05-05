package com.example.httpdemo.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.httpdemo.data.MoneyResult
import com.example.httpdemo.network.MoneyApi
import kotlinx.coroutines.launch

sealed interface MoneyUiState {
    object Init : MoneyUiState
    object Loading : MoneyUiState
    data class Success(val moneyResult: MoneyResult) : MoneyUiState
    object Error : MoneyUiState
}

class MoneyViewModel : ViewModel() {

    var moneyUiState: MoneyUiState by mutableStateOf(MoneyUiState.Init)

    fun getRates(accessKey: String) {
        moneyUiState = MoneyUiState.Loading
        viewModelScope.launch {
            moneyUiState = try {
                val result = MoneyApi.retrofitService.getRates(accessKey)
                MoneyUiState.Success(result)
            } catch (e: Exception) {
                e.printStackTrace()
                MoneyUiState.Error
            }
        }
    }
}