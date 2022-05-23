package com.teamb.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.teamb.calculator.components.Calculator
import com.teamb.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {

    private val viewModel : CalculatorViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CalculatorTheme() {

                val state = viewModel.state
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Calculator(
                        state = state, modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        onAction = viewModel::onAction,
                        buttonSpacing = 12.dp
                    )
                }
            }
        }
    }


}
