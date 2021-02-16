package org.salient.artplayer.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.OnLifecycleEvent
import org.salient.artplayer.MediaPlayerManager
import org.salient.artplayer.audio.DefaultAudioManager
import org.salient.artplayer.audio.IAudioManager
import org.salient.artplayer.bean.VideoSize
import org.salient.artplayer.conduction.PlayerState
import org.salient.artplayer.conduction.ScaleType
import org.salient.artplayer.conduction.WindowType
import org.salient.artplayer.extend.Utils
import org.salient.artplayer.player.IMediaPlayer

/**
 * description: 视频播放视容器
 *
 * @author Maiwenchang
 * email: cv.stronger@gmail.com
 * date: 2020-05-04 10:06 AM.
 */
open class VideoView : FrameLayout, IVideoView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val TAG = javaClass.simpleName
    private var textureView: ResizeTextureView? = null
    private var surfaceTexture: SurfaceTexture? = null
    private var surface: Surface? = null

    final override val cover: ImageView by lazy { ImageView(context).apply { visibility = View.GONE } }

    final override var mediaPlayer: IMediaPlayer<*>? = null
        set(value) {
            removeMediaPlayerObserver(field)
            field = value
            registerMediaPlayerObserver(field)
        }

    override var audioManager: IAudioManager = DefaultAudioManager(context, this.mediaPlayer)

    /**
     * 是否正在播放
     */
    override val isPlaying: Boolean
        get() = mediaPlayer?.isPlaying == true

    /**
     * 当前位置
     */
    override val currentPosition: Long
        get() = mediaPlayer?.currentPosition ?: 0

    /**
     * 视频时长
     */
    override val duration: Long
        get() = when (playerState) {
            PlayerState.PREPARED, PlayerState.STARTED, PlayerState.PAUSED, PlayerState.STOPPED, PlayerState.COMPLETED -> {
                mediaPlayer?.duration ?: 0
            }
            else -> 0
        }

    /**
     * 视频高度
     */
    override val videoHeight: Int
        get() = mediaPlayer?.videoHeight ?: 0

    /**
     * 视频宽度
     */
    override val videoWidth: Int
        get() = mediaPlayer?.videoWidth ?: 0

    /**
     * 播放器状态
     */
    override val playerState: PlayerState
        get() = mediaPlayer?.playerStateLD?.value ?: PlayerState.IDLE

    init {
        tag = WindowType.NORMAL
        this.setBackgroundColor(Color.BLACK)
        //添加播放器视图容器
        textureView = ResizeTextureView(context)
        textureView?.surfaceTextureListener = this
        val layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER)
        this.addView(textureView, layoutParams)
        //添加封面遮罩容器
        this.addView(this.cover, layoutParams)
        //注册生命周期监听
        registerLifecycleCallback()
        //注册播放器状态监听
        registerMediaPlayerObserver(this.mediaPlayer)
    }

    fun setScreenScale(scaleType:ScaleType){
        textureView?.setScreenScale(scaleType)
    }
    /**
     * 准备
     */
    override fun prepare() {
        Log.d(TAG, "prepare() called")
        attach()
        when (playerState) {
            PlayerState.INITIALIZED, PlayerState.STOPPED -> mediaPlayer?.prepareAsync()
        }
    }

    /**
     * 开始播放
     */
    override fun start() {
        Log.d(TAG, "start() called")
        when (playerState) {
            PlayerState.PREPARED, PlayerState.STARTED, PlayerState.PAUSED, PlayerState.COMPLETED -> mediaPlayer?.start()
        }
    }

    /**
     * 重播
     */
    override fun replay() {
        Log.d(TAG, "replay() called")
        when (playerState) {
            PlayerState.PREPARED, PlayerState.STARTED, PlayerState.PAUSED, PlayerState.COMPLETED -> {
                mediaPlayer?.seekTo(0)
                mediaPlayer?.start()
            }
        }

    }

    /**
     * 暂停
     */
    override fun pause() {
        Log.d(TAG, "pause() called")
        when (playerState) {
            PlayerState.STARTED, PlayerState.PAUSED, PlayerState.COMPLETED -> mediaPlayer?.pause()
        }
    }

    /**
     * 停止
     */
    override fun stop() {
        Log.d(TAG, "stop() called")
        when (playerState) {
            PlayerState.PREPARED, PlayerState.STARTED, PlayerState.PAUSED, PlayerState.STOPPED, PlayerState.COMPLETED -> {
                mediaPlayer?.stop()
            }
        }
    }

    /**
     * 释放
     */
    override fun release() {
        Log.d(TAG, "release() called")
        mediaPlayer?.release()
        surfaceTexture = null
    }

    /**
     * 重置
     */
    override fun reset() {
        Log.d(TAG, "reset() called")
        mediaPlayer?.reset()
    }

    /**
     * 跳转
     */
    override fun seekTo(time: Long) {
        Log.d(TAG, "seekTo() called with: time = $time")
        when (playerState) {
            PlayerState.PREPARED, PlayerState.STARTED, PlayerState.PAUSED, PlayerState.COMPLETED -> mediaPlayer?.seekTo(time)
        }
    }

    /**
     * 设置音量
     *
     */
    override fun setVolume(volume: Int) {
        Log.d(TAG, "setVolume() called with: volume = $volume")
        audioManager.setVolume(volume)
    }

    override fun attach() {
        Log.d(TAG, "attach() called")
        surfaceTexture?.let {
            surface?.release()
            surface = Surface(it)
            mediaPlayer?.setSurface(surface)
        }
    }

    override fun getBitmap(): Bitmap? {
        return textureView?.bitmap
    }

    override fun onSurfaceTextureAvailable(surfaceTexture: SurfaceTexture, width: Int, height: Int) {
        Log.d(TAG, "onSurfaceTextureAvailable() called with: surfaceTexture = $surfaceTexture, width = $width, height = $height")
        if (this.surfaceTexture == null) {
            this.surfaceTexture = surfaceTexture
            attach()
        } else if (textureView?.surfaceTexture != surfaceTexture) {
            textureView?.setSurfaceTexture(surfaceTexture)
        }
    }

    override fun onSurfaceTextureSizeChanged(surfaceTexture: SurfaceTexture, width: Int, height: Int) {
        Log.d(TAG, "onSurfaceTextureSizeChanged() called with: surfaceTexture = $surfaceTexture, width = $width, height = $height")
        if (textureView?.surfaceTexture != surfaceTexture) {
            textureView?.setSurfaceTexture(surfaceTexture)
        }
    }

    override fun onSurfaceTextureDestroyed(surfaceTexture: SurfaceTexture): Boolean {
        Log.d(TAG, "onSurfaceTextureDestroyed() called with: surfaceTexture = $surfaceTexture")
        this.surfaceTexture = null
        return true
    }

    override fun onSurfaceTextureUpdated(surfaceTexture: SurfaceTexture) {
        if (textureView?.surfaceTexture != surfaceTexture) {
            textureView?.setSurfaceTexture(surfaceTexture)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onActivityPause() {
        pause()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onActivityDestroy() {
        stop()
        release()
        Utils.scanForActivity(context)?.let {
            MediaPlayerManager.dismissFullscreen(it)
            MediaPlayerManager.dismissTinyWindow(it)
        }
    }

    private fun registerLifecycleCallback() {
        val activity = Utils.scanForActivity(context) ?: return
        if (activity is FragmentActivity) {
            activity.lifecycle.addObserver(this)
        }
    }

    /**
     * 注册播放器内核的监听
     */
    private fun registerMediaPlayerObserver(mediaPlayer: IMediaPlayer<*>?) {
        val activity = Utils.scanForActivity(context)
        val lifecycleOwner = if (activity is LifecycleOwner) activity else return
        mediaPlayer?.videoSizeLD?.observe(lifecycleOwner, videoSizeObserver)
        mediaPlayer?.playerStateLD?.observe(lifecycleOwner, playerStateObserver)
    }

    /**
     * 移除播放器内核监听
     */
    private fun removeMediaPlayerObserver(mediaPlayer: IMediaPlayer<*>?) {
        mediaPlayer?.videoSizeLD?.removeObserver(videoSizeObserver)
        mediaPlayer?.playerStateLD?.removeObserver(playerStateObserver)
        audioManager.abandonAudioFocus()
    }

    /**
     * 视频尺寸监听
     */
    private val videoSizeObserver = Observer<VideoSize> {
        Log.d(TAG, "VideoSize: width = ${it.width}, height = ${it.height}")
        textureView?.setVideoSize(it.width, it.height)
    }

    /**
     * 视频播放器状态监听
     */
    private val playerStateObserver = Observer<PlayerState> {
        Log.d(TAG, "PlayerState: ${it.javaClass.canonicalName}")
        when (it) {
            PlayerState.PREPARED -> {
                if (mediaPlayer?.playWhenReady == true) {
                    mediaPlayer?.start()
                }
            }
            PlayerState.STARTED -> {
                audioManager.requestAudioFocus()
                //移除封面
                postDelayed({
                    cover.visibility = View.GONE
                }, 50)
            }
            PlayerState.STOPPED, PlayerState.END -> {
                audioManager.abandonAudioFocus()
            }
            PlayerState.ERROR -> {
                audioManager.abandonAudioFocus()
            }
        }
    }

}