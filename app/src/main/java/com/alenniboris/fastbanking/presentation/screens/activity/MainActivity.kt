package com.alenniboris.fastbanking.presentation.screens.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alenniboris.fastbanking.presentation.screens.NavGraphs
import com.alenniboris.fastbanking.presentation.screens.destinations.LoginScreenDestination
import com.alenniboris.fastbanking.presentation.screens.destinations.MainScreenDestination
import com.alenniboris.fastbanking.presentation.uikit.theme.BottomBarHeight
import com.alenniboris.fastbanking.presentation.uikit.theme.FastBankingTheme
import com.alenniboris.fastbanking.presentation.uikit.theme.enterTextFieldColor
import com.alenniboris.fastbanking.presentation.uikit.values.AuthorizedActions
import com.alenniboris.fastbanking.presentation.uikit.values.LoginScreenRoute
import com.alenniboris.fastbanking.presentation.uikit.values.NotAuthorizedActions
import com.alenniboris.fastbanking.presentation.uikit.values.RoutesWithoutBottomBar
import com.alenniboris.fastbanking.presentation.uikit.values.toBottomBarModel
import com.alenniboris.fastbanking.presentation.uikit.views.AppBottomBar
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.navigation.navigate
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

    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route ?: ""

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (currentRoute !in RoutesWithoutBottomBar) {
                AppBottomBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(enterTextFieldColor)
                        .height(BottomBarHeight),
                    currentRoute = currentRoute,
                    items = if (isUserAuthenticated) {
                        AuthorizedActions.entries.toList().map { action ->
                            action.toBottomBarModel(
                                onClick = {
                                    when (action) {
                                        AuthorizedActions.Main -> {
                                            Log.e("!!!", "Main")
                                        }

                                        AuthorizedActions.History -> {
                                            Log.e("!!!", "History")
                                        }

                                        AuthorizedActions.Payment -> {
                                            Log.e("!!!", "Payment")
                                        }

                                        AuthorizedActions.Additions -> {
                                            Log.e("!!!", "Additions")
                                        }
                                    }
                                }
                            )
                        }
                    } else {
                        NotAuthorizedActions.entries.toList().map { action ->
                            action.toBottomBarModel(
                                onClick = {
                                    when (action) {
                                        NotAuthorizedActions.Entrance -> {
                                            if (currentRoute != LoginScreenRoute) {
                                                navController.navigate(LoginScreenDestination)
                                            }
                                        }

                                        NotAuthorizedActions.Atms -> {
                                            Log.e("!!!", "Atms")
                                        }

                                        NotAuthorizedActions.Exchange -> {
                                            Log.e("!!!", "Exchange")
                                        }

                                        NotAuthorizedActions.Help -> {
                                            Log.e("!!!", "Help")
                                        }

                                        NotAuthorizedActions.Additions -> {
                                            Log.e("!!!", "Additions")
                                        }
                                    }
                                }
                            )
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            DestinationsNavHost(
                navGraph = NavGraphs.root,
                startRoute = if (isUserAuthenticated) MainScreenDestination else LoginScreenDestination,
                engine = navHostEngine,
                navController = navController
            )
        }
    }
}