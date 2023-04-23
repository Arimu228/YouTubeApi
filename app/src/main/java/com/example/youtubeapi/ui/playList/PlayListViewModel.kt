package com.example.youtubeapi.ui.playList

import androidx.lifecycle.LiveData
import com.example.youtubeapi.core.network.result.Resource
import com.example.youtubeapi.data.remote.model.PlayList
import com.example.youtubeapi.core.ui.BaseViewModel
import com.example.youtubeapi.repository.Repository

class PlayListViewModel(private val repository: Repository) : BaseViewModel() {
    fun getPlayLists(): LiveData<Resource<PlayList>> {
        return repository.getPlayLists()
    }
}