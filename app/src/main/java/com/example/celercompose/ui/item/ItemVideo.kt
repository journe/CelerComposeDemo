package com.example.celercompose.ui.item

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import org.salient.artplayer.player.SystemMediaPlayer
import org.salient.artplayer.ui.VideoView

@Composable
fun ItemVideo(
    videoUrl: String,
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier,
        viewBlock = {
            VideoView(it).apply {
                release()
                mediaPlayer = SystemMediaPlayer().apply {
                    setDataSource(it, Uri.parse(videoUrl))
                    setLooping(true)
                }
                prepare()
            }
        })
//    Box(
//        modifier = modifier
//    ) {
//
//    }
}