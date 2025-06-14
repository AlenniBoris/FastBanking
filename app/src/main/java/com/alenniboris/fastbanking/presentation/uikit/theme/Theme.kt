package com.alenniboris.fastbanking.presentation.uikit.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alenniboris.fastbanking.presentation.uikit.utils.ThemeMode
import com.alenniboris.fastbanking.presentation.uikit.utils.baseCurrencyMode
import com.alenniboris.fastbanking.presentation.uikit.utils.currentLanguageMode
import com.alenniboris.fastbanking.presentation.uikit.utils.currentThemeMode
import com.alenniboris.fastbanking.presentation.uikit.utils.getLastBaseCurrencyAndApply
import com.alenniboris.fastbanking.presentation.uikit.utils.getLastLanguageAndApply
import com.alenniboris.fastbanking.presentation.uikit.utils.getLastThemeAndApply

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xff404040),
    background = Color(0xff404040),
    onBackground = Color(0xffffffff),
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xffdbdbdb),
    background = Color(0xffdbdbdb),
    onBackground = Color(0xff000000)
)

@Composable
fun FastBankingTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

    val context = LocalContext.current
    val themeModeInit = remember { context.getLastThemeAndApply(isSystemDarkMode = darkTheme) }
    val langModeInit = remember { context.getLastLanguageAndApply() }
    val baseCurrencyInit = remember { context.getLastBaseCurrencyAndApply() }

    val colorScheme by remember(
        key1 = currentThemeMode.collectAsStateWithLifecycle().value.isThemeDark,
        key2 = currentLanguageMode.collectAsStateWithLifecycle().value,
        key3 = baseCurrencyMode.collectAsStateWithLifecycle().value
    ) {
        mutableStateOf(LightColorScheme.copy())
    }

    val view = LocalView.current
    if (!view.isInEditMode) {

        val themeMode = currentThemeMode.collectAsStateWithLifecycle()

        SideEffect {
            val window = (view.context as Activity).window

            val appBarColor = Color.Transparent

            window.statusBarColor = appColor.toArgb()
            window.navigationBarColor = bottomBarColor.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                themeMode.value is ThemeMode.Light
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
                themeMode.value is ThemeMode.Dark
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}