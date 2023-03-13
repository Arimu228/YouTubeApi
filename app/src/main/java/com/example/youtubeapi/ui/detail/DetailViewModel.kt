package com.example.youtubeapi.ui.detail

import androidx.lifecycle.LiveData
import com.example.youtubeapi.App
import com.example.youtubeapi.base.BaseViewModel
import com.example.youtubeapi.model.ItemList
import com.example.youtubeapi.model.PlayList

class DetailViewModel:BaseViewModel() {

        fun getItemList(id: String): LiveData<ItemList> {
            return App().repository.getItemList(id = id)
        }

}