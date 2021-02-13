package com.example.celercompose.ui.item

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.unit.dp
import com.halilibo.composevideoplayer.MediaPlaybackControls
import com.halilibo.composevideoplayer.VideoPlayerSource

@Composable
fun ItemVideo(
    videoUrl: String,
    modifier: Modifier = Modifier
) {
    var mediaPlaybackControls by remember { mutableStateOf(MediaPlaybackControls()) }
    var videoSize by savedInstanceState { 0 to 0 }
    val (videoWidth, videoHeight) = videoSize
    mediaPlaybackControls = com.halilibo.composevideoplayer.VideoPlayer(
        source = VideoPlayerSource.Network(url = videoUrl),
        backgroundColor = Color.Transparent,
        controlsEnabled = false,
        modifier = Modifier.onGloballyPositioned {
            videoSize = it.size.width to it.size.height
        }
    )
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        item {
            with(DensityAmbient.current) {
                Box(
                    modifier = Modifier.preferredSize(
                        videoWidth.toDp(),
                        videoHeight.toDp()
                    )
                )
            }
        }
    }
}
