package com.example.celercompose.ui.item

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import org.salient.artplayer.conduction.ScaleType
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
                setScreenScale(ScaleType.SCALE_CENTER_CROP)
            }
        })
}