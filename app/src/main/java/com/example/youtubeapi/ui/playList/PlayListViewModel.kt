package com.example.youtubeapi.ui.playList

import androidx.lifecycle.LiveData
import com.bumptech.glide.load.engine.Resource
import com.example.youtubeapi.App
import com.example.youtubeapi.model.PlayList
import com.example.youtubeapi.base.BaseViewModel
import com.example.youtubeapi.remote.ApiServise
import com.example.youtubeapi.remote.RetroFitClient

class PlayListViewModel : BaseViewModel() {
    fun getPlayLists(): LiveData<PlayList> {
        return App().repository.getPlayLists()
    }
}