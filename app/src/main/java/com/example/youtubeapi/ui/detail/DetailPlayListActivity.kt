package com.example.youtubeapi.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.youtubeapi.core.network.result.Status.*
import com.example.youtubeapi.core.ui.BaseActivity
import com.example.youtubeapi.data.remote.model.Item

import com.example.youtubeapi.databinding.ActivityDetailPlayListBinding
import com.example.youtubeapi.ui.player.VideoPlayerActivity
import com.example.youtubeapi.ui.utils.ConnectionLiveData

class DetailPlayListActivity : BaseActivity<DetailViewModel, ActivityDetailPlayListBinding>() {
    private var adapter: DetailAdapter? = null
    private lateinit var cld: ConnectionLiveData
    private var playlistItemData = listOf<Item>()
    private var videosId = arrayListOf<String>()
    private val id: String?
        get() = intent.getStringExtra(ID)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = DetailAdapter(this::onNextButton)
        checkNetwork()
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityDetailPlayListBinding {
        return ActivityDetailPlayListBinding.inflate(inflater)
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        setItemList()

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

    override fun observe() {
        super.observe()
        setVideoId()
    }

    private fun setItemList() {
        id?.let { id ->
            viewModel.getItemList(id).observe(this) {
                binding.rvItems.adapter = adapter
                it.data?.let { it1 -> adapter?.setItemsList(it1.items) }
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
        viewModel.loading.observe(this) {
            binding.progressCircular.isVisible = it
        }

        id?.let { id ->
            viewModel.getItemList(id).observe(this) {
                when (it.status) {
                    SUCCESS -> {
                        viewModel.loading.postValue(false)
                        playlistItemData = it.data!!.items
                        getVideoId()
                        adapter?.setItemsList(playlistItemData)
                    }
                    ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                    LOADING -> viewModel.loading.postValue(true)


                }
            }
        }
    }

    override fun initListener() {
        binding.toolbar.tvBack.setOnClickListener {
            finish()
        }
    }

    private fun onNextButton(item: Item) {
        Intent(this, VideoPlayerActivity::class.java).apply {
            putExtra(VIDEOS_KEY, item.id)
            putExtra(VIDEOS_KEY, item.snippet.title)
            putExtra(VIDEOS_KEY, item.snippet.description)
            startActivity(this)
        }
    }


    companion object {
        const val ID = "id"
        const val VIDEOS_KEY = "videos.key"
    }

}




