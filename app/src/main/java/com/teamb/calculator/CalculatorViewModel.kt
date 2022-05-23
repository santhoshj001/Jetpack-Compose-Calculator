package com.teamb.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamb.calculator.action.CalculatorAction
import com.teamb.calculator.action.CalculatorOperation
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CalculatorViewModel : ViewModel() {

    var state by mutableStateOf(CalculatorState())
        private set

    fun onAction(action: CalculatorAction) {
        when (action) {
            CalculatorAction.Calculate -> performCalculation()
            CalculatorAction.Clear -> performClear()
            CalculatorAction.Decimal -> onDecimal()
            CalculatorAction.Delete -> performDelete()
            is CalculatorAction.Number -> onNumber(action.number)
            is CalculatorAction.Operation -> onOperation(action.operation)
        }
    }

    private fun onOperation(operation: CalculatorOperation) {
        if (state.operation == null && state.number1.isNotBlank()) {
            state = state.copy(operation = operation)
        }
    }

    private fun onNumber(number: Int) {
        state = if (state.operation == null) {
            state.copy(
                number1 = state.number1.plus(number)
            )
        } else {
            state.copy(
                number2 = state.number2.plus(number)
            )
        }

    }

    private fun performDelete() {
        if (state.operation == null && state.number1.isNotBlank()) {
            state = state.copy(number1 = state.number1.dropLast(1))
            return
        } else if (state.number2.isNotBlank()) {
            state = state.copy(number2 = state.number2.dropLast(1))
            return
        } else {
            state = state.copy(operation = null)
        }
    }

    private fun onDecimal() {
        if (state.operation == null && state.number1.isNotBlank() && !state.number1.contains(".")) {
            state = state.copy(number1 = state.number1.plus("."))
            return
        }

        if (state.number2.isNotBlank() && !state.number2.contains(".")) {
            state = state.copy(number2 = state.number2.plus("."))
            return
        }
    }

    private fun performClear() {
        state = CalculatorState()
    }

    private fun performCalculation() {
        var num1 = state.number1.toDoubleOrNull()
        val num2 = state.number2.toDoubleOrNull()
        if (num1 != null && num2 != null && state.operation != null) {
            when (state.operation) {
                CalculatorOperation.Add -> num1 += num2
                CalculatorOperation.Divide -> num1 /= num2
                CalculatorOperation.Multiply -> num1 *= num2
                CalculatorOperation.Subtract -> num1 -= num2
                null -> num1 = 0.0
            }
        }
        state = CalculatorState(number1 = num1.toString().take(10))
    }
}