package com.alenniboris.fastbanking.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.UserGender
import com.alenniboris.fastbanking.domain.usecase.logic.ILoginUserIntoBankingUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.IRegisterUserIntoBankingUseCase
import com.alenniboris.fastbanking.presentation.mappers.toUiMessageString
import com.alenniboris.fastbanking.presentation.ui.theme.FastBankingTheme
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FastBankingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    val loginUseCase = koinInject<ILoginUserIntoBankingUseCase>()
    val registerUseCase = koinInject<IRegisterUserIntoBankingUseCase>()

    LaunchedEffect(Unit) {

        val res = registerUseCase.invoke("a@a-1", "222", "222")

        when (res) {
            is CustomResultModelDomain.Success -> {
                Log.e("!!!", "Successfully registered for a")
            }

            is CustomResultModelDomain.Error -> {
                Log.e("!!!", res.exception.toUiMessageString())
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FastBankingTheme {
        Greeting("Android")
    }
}