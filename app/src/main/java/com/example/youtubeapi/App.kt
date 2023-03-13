package com.example.youtubeapi

import android.app.Application
import com.example.youtubeapi.repository.Repository

class App:Application(){
    val repository: Repository by lazy{
        Repository()
    }

}