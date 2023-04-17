package com.example.youtubeapi.ui.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.youtubeapi.core.network.result.Status
import com.example.youtubeapi.core.ui.BaseActivity
import com.example.youtubeapi.data.remote.model.Item
import com.example.youtubeapi.databinding.ActivityVideoPlayerBinding
import com.example.youtubeapi.ui.detail.DetailPlayListActivity
import com.example.youtubeapi.ui.utils.ConnectionLiveData

class VideoPlayerActivity : BaseActivity<VideoViewModel,ActivityVideoPlayerBinding>() {
    private lateinit var cld: ConnectionLiveData
    private var playlistItemData = listOf<Item>()
    private var videosId = arrayListOf<String>()
    private val id: String?
        get() = intent.getStringExtra(DetailPlayListActivity.ID)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkNetwork()
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

    private fun setVideoId() {
        viewModel.loading.observe(this) {
            binding.progressCircular.isVisible = it
        }

        id?.let { id ->
            viewModel.getItemVideo(id).observe(this) {
                when (it.status) {
                    Status.SUCCESS -> {
                        viewModel.loading.postValue(false)
                        playlistItemData = it.data!!.items
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



    override fun inflateViewBinding(inflater: LayoutInflater): ActivityVideoPlayerBinding {
        return ActivityVideoPlayerBinding.inflate(inflater)
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[VideoViewModel::class.java]
        setVideoId()
    }
}