package com.halilibo.composevideoplayer

import androidx.compose.material.Icon
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun ShadowedIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    iconSize: Dp = 48.dp

) {
    Box(modifier = modifier) {
        Icon(
            imageVector = icon,
            contentDescription = "",
            tint = Color.Black.copy(alpha = 0.3f),
            modifier = Modifier
                .offset(2.dp, 2.dp)
                .then(modifier)
        )
    }
}