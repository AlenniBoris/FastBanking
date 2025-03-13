package com.alenniboris.fastbanking.presentation.screens.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alenniboris.fastbanking.presentation.screens.NavGraphs
import com.alenniboris.fastbanking.presentation.screens.destinations.LoginScreenDestination
import com.alenniboris.fastbanking.presentation.screens.destinations.MainScreenDestination
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.rememberNavHostEngine
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FastBankingTheme {
                FastBankingUi()
            }
        }
    }
}

@Composable
fun FastBankingUi() {
    val viewModel = koinViewModel<MainActivityViewModel>()
    val isUserAuthenticated by viewModel.isUserAuthenticatedFlow.collectAsStateWithLifecycle()

    val navHostEngine = rememberNavHostEngine(
        navHostContentAlignment = Alignment.TopCenter,
        rootDefaultAnimations = RootNavGraphDefaultAnimations(
            enterTransition = { fadeIn(animationSpec = tween(600)) },
            exitTransition = { fadeOut(animationSpec = tween(600)) }
        )
    )

    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            DestinationsNavHost(
                navGraph = NavGraphs.root,
                startRoute = if (isUserAuthenticated) MainScreenDestination else LoginScreenDestination,
                engine = navHostEngine
            )
        }
    }
}