package com.alenniboris.fastbanking.presentation.uikit.views

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.R
import com.alenniboris.fastbanking.presentation.uikit.theme.appColor
import com.alenniboris.fastbanking.presentation.uikit.theme.appTopBarElementsColor

@Composable
fun AppProgressBar(
    modifier: Modifier = Modifier,
    iconTint: Color = appTopBarElementsColor
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = stringResource(R.string.progress_bar_animation_description)
    )


    Box(
        modifier = modifier
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer(rotationZ = rotationAngle),
            painter = painterResource(R.drawable.office_type_icon),
            contentDescription = stringResource(R.string.progress_bar_description),
            tint = iconTint
        )
    }
}

@Composable
@Preview
private fun AppProgressBarPreview() {
    Box(
        modifier = Modifier.fillMaxSize().background(appColor)
    ){
        AppProgressBar(
            modifier = Modifier.fillMaxSize()
        )
    }
}