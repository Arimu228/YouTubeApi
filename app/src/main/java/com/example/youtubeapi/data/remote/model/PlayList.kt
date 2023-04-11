package com.example.youtubeapi.data.remote.model
data class Playlist(
    val items: List<Item>,
)
data class PlayList(
    val etag: String,
    val items: List<Item>,
    val kind: String,
    val nextPageToken: String,
    val pageInfo: PageInfo
)
data class ItemList(
    val kind:String,
    val items:List<Item>
)
data class Thumbnails(
    val default: Default,
    val high: High,
    val maxres: Maxres,
    val medium: Medium,
    val standard: Standard
)

data class Standard(
    val height: Int,
    val url: String,
    val width: Int
)

data class Snippet(
    val channelId: String,
    val channelTitle: String,
    val description: String,
    val localized: Localized,
    val publishedAt: String,
    val thumbnails: Thumbnails,
    val title: String
)

data class PageInfo(
    val resultsPerPage: Int,
    val totalResults: Int
)

data class Medium(
    val height: Int,
    val url: String,
    val width: Int
)

data class Maxres(
    val height: Int,
    val url: String,
    val width: Int
)

data class Localized(
    val description: String,
    val title: String
)

data class Item(
    val contentDetails: ContentDetails,
    val etag: String,
    val id: String,
    val kind: String,
    val snippet: Snippet
)

data class High(
    val height: Int,
    val url: String,
    val width: Int
)

data class Default(
    val height: Int,
    val url: String,
    val width: Int
)
data class ContentDetails(
    val itemCount: Int,
    val videoId : String
)

