package com.example.youtubeapi.ui.video

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.example.youtubeapi.R
import com.google.android.exoplayer2.util.Util
import com.example.youtubeapi.core.network.result.Status
import com.example.youtubeapi.core.ui.BaseActivity
import com.example.youtubeapi.data.remote.model.Item
import com.example.youtubeapi.databinding.ActivityVideoPlayerBinding
import com.example.youtubeapi.ui.detail.DetailPlayListActivity
import com.example.youtubeapi.ui.utils.ConnectionLiveData
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MergingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import org.koin.androidx.viewmodel.ext.android.viewModel

class VideoPlayerActivity : BaseActivity<VideoViewModel, ActivityVideoPlayerBinding>() {
    private lateinit var cld: ConnectionLiveData
    private var playlistItemData = listOf<Item>()
    private var player: ExoPlayer? = null
    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L
    override val viewModel: VideoViewModel by viewModel()
    private var videosId = arrayListOf<String>()
    private val id: String?
        get() = intent.getStringExtra(DetailPlayListActivity.DETAIL_ID)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkNetwork()
        setVideoId()
    }


    private fun checkNetwork() {

        cld = ConnectionLiveData(application)
        cld.observe(this) { isConnected ->
            if (isConnected) {
                binding.checkInternet.root.visibility = View.GONE
                binding.coordinatorLayout.visibility = View.VISIBLE
            } else {
                binding.checkInternet.root.visibility = View.VISIBLE
                binding.coordinatorLayout.visibility = View.GONE

            }
        }
    }

    private fun getItemVideoId() {
        viewModel.getItemVideos(playlistItemData)
        viewModel.liveVideosId.observe(this) {
            videosId.addAll(it)
        }
    }

    @SuppressLint("StaticFieldLeak")
    private fun initializePlayer() {
        player = ExoPlayer.Builder(this@VideoPlayerActivity)
            .build()
            .also { exoPlayer ->
                binding.exoPlayer.player = exoPlayer
            }
//        val youtubeLink = "http://youtube.com/watch?v=${intent.getStringExtra("id")}"
        val youtubeLink = "https://www.youtube.com/watch?v=4TaUIBXoAgU"

        object : YouTubeExtractor(this) {
            override fun onExtractionComplete(
                ytFiles: SparseArray<YtFile>?,
                videoMeta: VideoMeta?
            ) {
                if (ytFiles != null) {

                    val videoTag = 133
                    val audioTag = 140

                    val videoUri = ytFiles.get(videoTag).url
                    val audioUri = ytFiles.get(audioTag).url

                    val audioSource =
                        ProgressiveMediaSource.Factory(DefaultHttpDataSource.Factory())
                            .createMediaSource(MediaItem.fromUri(audioUri))

                    val videoSource =
                        ProgressiveMediaSource.Factory(DefaultHttpDataSource.Factory())
                            .createMediaSource(MediaItem.fromUri(videoUri))

                    player?.prepare()
                    player?.setMediaSource(MergingMediaSource(true, videoSource, audioSource), true)
                    player?.playWhenReady = playWhenReady
                    player?.seekTo(currentItem, playbackPosition)
                }
            }

        }.extract(youtubeLink)
    }

    private fun releasePlayer() {
        player?.let { exoPlayer ->
            playbackPosition = exoPlayer.currentPosition
            currentItem = exoPlayer.currentMediaItemIndex
            playWhenReady = exoPlayer.playWhenReady
            exoPlayer.release()
        }
        player = null
    }

    private fun enterFullScreen() {
        // Скрыть системные панели
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.exoPlayer).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        // Установить режим полноэкранного воспроизведения
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    private fun exitFullScreen() {
        // Отобразить системные панели
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(window, binding.exoPlayer).let { controller ->
            controller.show(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
        }

        // Установить обычный режим ориентации экрана
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.exoPlayer).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

    }

    private fun onFullScreenButtonClick() {

        if (isFullScreen()) {
            exitFullScreen()
        } else {
            enterFullScreen()
        }
    }


    private fun isFullScreen(): Boolean {
        val currentOrientation = requestedOrientation
        return currentOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    public override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initializePlayer()
        }
    }

    public override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    public override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUi()
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer()
        }
    }

    private fun setVideoId() {
        viewModel.loading.observe(this) {
            binding.progressCircular.isVisible = it
        }

        id?.let { id ->
            viewModel.loading.observe(this) {
                binding.progressCircular.isVisible = it
            }
            viewModel.getItemVideo(id).observe(this) {
                when (it.status) {
                    Status.SUCCESS -> {
                        viewModel.loading.postValue(false)
                        setData(it.data!!.items[0])
                        playlistItemData = it.data.items
                        getItemVideoId()

                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> viewModel.loading.postValue(true)


                }
            }
        }
    }

    private fun setData(data: Item) {
        with(binding) {
            tvName.text = data.snippet.title
            tvInfoDetail.text = data.snippet.description

        }

    }

    override fun initListener() {
        binding.toolbarBack.tvBack.setOnClickListener {
            finish()
        }
    }


    override fun inflateViewBinding(inflater: LayoutInflater): ActivityVideoPlayerBinding {
        return ActivityVideoPlayerBinding.inflate(inflater)
    }


}

