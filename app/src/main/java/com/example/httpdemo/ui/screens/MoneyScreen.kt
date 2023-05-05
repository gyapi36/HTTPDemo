package com.example.httpdemo.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.httpdemo.data.MoneyResult

@Composable
fun MoneyScreen(
    moneyViewModel: MoneyViewModel = viewModel()
) {
    Column {
        Button(onClick = {
            moneyViewModel.getRates("969c37b5a73f8cb2d12c081dcbdc35e6")
        }) {
            Text(text = "Get exchange rates")
        }

        when (moneyViewModel.moneyUiState) {
            is MoneyUiState.Init -> {}
            is MoneyUiState.Loading -> CircularProgressIndicator()
            is MoneyUiState.Error -> Text(text = "Error in network")
            is MoneyUiState.Success -> ResultView(
                moneyResult = (moneyViewModel.moneyUiState as MoneyUiState.Success).moneyResult
            )
        }
    }
}

@Composable
fun ResultView(moneyResult: MoneyResult) {
    Column() {
        Text(text = "Base is EUR data: ${moneyResult.date}")
        Text(text = "USD: ${moneyResult.rates?.uSD}")
        Text(text = "HUF: ${moneyResult.rates?.hUF}")
        Text(text = "GBP: ${moneyResult.rates?.gBP}")
    }
}