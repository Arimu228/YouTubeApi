package com.example.youtubeapi.ui.playList

import androidx.lifecycle.LiveData
import com.example.youtubeapi.App.Companion.repository
import com.example.youtubeapi.core.network.result.Resource
import com.example.youtubeapi.data.remote.model.PlayList
import com.example.youtubeapi.core.ui.BaseViewModel

class PlayListViewModel : BaseViewModel() {
    fun getPlayLists(): LiveData<Resource<PlayList>> {
        return repository.getPlayLists()
    }
}