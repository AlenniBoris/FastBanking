package com.alenniboris.fastbanking.presentation.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alenniboris.fastbanking.domain.usecase.logic.ILoginUserIntoBankingUseCase
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@RootNavGraph(start = true)
@Destination
@Composable
fun LoginScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val login = koinInject<ILoginUserIntoBankingUseCase>()
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        Text("Login")
        Button(
            onClick = {
                scope.launch {
                    login.invoke(
                        login = "a@a-1",
                        password = "111111"
                    )
                }
            }
        ) {
            Text("Login")
        }
    }
}