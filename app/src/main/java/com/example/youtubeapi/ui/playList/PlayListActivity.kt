package com.example.youtubeapi.ui.playList

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtubeapi.base.BaseActivity
import com.example.youtubeapi.databinding.ActivityPlayListMainBinding
import com.example.youtubeapi.model.Item
import com.example.youtubeapi.ui.detail.DetailPlayListActivity


class PlayListActivity() : BaseActivity<PlayListViewModel, ActivityPlayListMainBinding>() {
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
            if (binding.rvPlayLists.equals(isConnected)) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }


    private fun setPlaylist() {
        viewModel.getPlayLists().observe(this) {
            binding.rvPlayLists.layoutManager = LinearLayoutManager(this)
            binding.rvPlayLists.adapter = adapter
            adapter.setPlayList(it.items)
        }
    }

    private fun onItemClick(list:Item){
        Intent(this,DetailPlayListActivity::class.java).apply {
            putExtra(ID,list.id)
            startActivity(this)
        }
    }

    companion object{
        const val ID = "id"
    }
}


