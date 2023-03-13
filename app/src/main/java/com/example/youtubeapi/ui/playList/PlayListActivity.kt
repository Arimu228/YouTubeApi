package com.example.youtubeapi.ui.playList

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
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
        fun isOnline(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                val capabilities =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                    } else {
                        TODO("VERSION.SDK_INT < M")
                    }
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                        return true
                    }
                }
            }
            return false
        }

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
            putExtra("id",list.id)
            startActivity(this)
        }
    }

    companion object{
        const val ID = "id"
    }
}


