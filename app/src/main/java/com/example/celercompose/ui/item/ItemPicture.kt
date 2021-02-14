package com.example.celercompose.ui.item

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
internal fun ItemPicture(
    url: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        CoilImage(
            data = url,
            contentDescription = null,
            fadeIn = true,
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.medium)
        )
    }
}