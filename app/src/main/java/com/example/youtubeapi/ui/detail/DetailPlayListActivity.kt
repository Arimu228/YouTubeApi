package com.example.youtubeapi.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.youtubeapi.core.network.result.Status.*
import com.example.youtubeapi.core.ui.BaseActivity
import com.example.youtubeapi.data.remote.model.Item
import com.example.youtubeapi.data.remote.model.PlayListInfo
import com.example.youtubeapi.databinding.ActivityDetailPlayListBinding
import com.example.youtubeapi.ui.player.VideoPlayerActivity

class DetailPlayListActivity : BaseActivity<DetailViewModel, ActivityDetailPlayListBinding>() {
    private lateinit var adapter: DetailAdapter
    private val playlistInfo by lazy { intent.getSerializableExtra(ID) as PlayListInfo }
    private var playlistItemData = listOf<Item>()
    private var videosId = arrayListOf<String>()
    private val id: String?
        get() = intent.getStringExtra(ID)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = DetailAdapter(this::onNextButton)
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityDetailPlayListBinding {
        return ActivityDetailPlayListBinding.inflate(inflater)
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        setItemList()

    }

    override fun observe() {
        super.observe()
        getVideoId()
    }

    private fun setItemList() {
        id?.let { id ->
            viewModel.getItemList(id).observe(this) {
                binding.rvPlaylist.adapter = adapter
                it.data?.let { it1 -> adapter.setItemsList(it1.items) }
            }
        }
    }


    private fun getVideoId() {
        viewModel.getVideosId(playlistItemData)
        viewModel.liveVideosId.observe(this) {
            videosId.addAll(it)
        }
    }

    private fun setVideoId() {
        viewModel.getItemList(playlistInfo.id).observe(this) {
            when (it.status) {
                SUCCESS -> {
                    playlistItemData = it.data!!.items
                    getVideoId()
                    adapter.setItemsList(playlistItemData)
                }
                ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                LOADING -> TODO()

            }
        }

    }


    override fun initListener() {
        binding.toolbar.tvBack.setOnClickListener {
            finish()
        }
    }

    private fun onNextButton(videoId: Int) {
        Intent(this, VideoPlayerActivity::class.java).apply {
            putExtra(VIDEOS_KEY, videoId)
            putExtra(VIDEOS_KEY, videosId)
            startActivity(this)
        }
    }

    companion object {
        const val ID = "id"
        const val VIDEOS_KEY = "videos.key"
    }


}




