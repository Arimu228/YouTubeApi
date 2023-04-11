package com.example.youtubeapi.ui.playList

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtubeapi.core.network.result.Status
import com.example.youtubeapi.core.ui.BaseActivity
import com.example.youtubeapi.data.locol.AppPrefs.AppPrefs
import com.example.youtubeapi.databinding.ActivityPlayListMainBinding
import com.example.youtubeapi.data.remote.model.Item
import com.example.youtubeapi.ui.detail.DetailPlayListActivity
import com.example.youtubeapi.ui.utils.ConnectionLiveData

class PlayListActivity : BaseActivity<PlayListViewModel, ActivityPlayListMainBinding>() {
    private lateinit var cld: ConnectionLiveData
    private lateinit var adapter: PLayListsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = PLayListsAdapter(this::onItemClick)
        checkNetwork()
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityPlayListMainBinding {
        return ActivityPlayListMainBinding.inflate(inflater)
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel = ViewModelProvider(this)[PlayListViewModel::class.java]

        setPlaylist()
    }

    private fun checkNetwork() {

        cld = ConnectionLiveData(application)
        cld.observe(this) { isConnected ->
            if (isConnected) {
                binding.containerPlayList.visibility = View.GONE
            } else {
                binding.containerPlayList.visibility = View.VISIBLE

            }
        }
    }

    private fun setPlaylist() {
        viewModel.loading.observe(this) {
            binding.progressCircular.isVisible = it

        }
        viewModel.getPlayLists().observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.rvDetailPlayLists.layoutManager = LinearLayoutManager(this)
                    binding.rvDetailPlayLists.adapter = adapter
                    viewModel.loading.postValue(false)
                    it.data?.let { it1 -> adapter.setPlayList(it1.items) }
                }
                Status.ERROR -> {
                    binding.progressCircular.isVisible = false
                    viewModel.loading.postValue(true)

                }
                Status.LOADING -> {
                    binding.progressCircular.isVisible = true
                    viewModel.loading.postValue(true)

                }
            }

        }
    }

    private fun onItemClick(list: Item) {
        Intent(this, DetailPlayListActivity::class.java).apply {
            putExtra(ID, list.id)
            startActivity(this)
        }
    }

    companion object {
        const val ID = "id"
    }
}


