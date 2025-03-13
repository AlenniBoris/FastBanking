package com.alenniboris.fastbanking.presentation.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alenniboris.fastbanking.domain.usecase.logic.ISignOutUseCase
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import org.koin.compose.koinInject

@RootNavGraph
@Destination
@Composable
fun MainScreen() {
    val signOut = koinInject<ISignOutUseCase>()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Main screen")
        Button(
            onClick = { signOut.invoke() }
        ) {
            Text("Sign out")
        }
    }
}