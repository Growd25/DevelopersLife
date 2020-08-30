package com.growd25.developerslife.presentation

import com.growd25.developerslife.model.Post
import com.growd25.developerslife.model.PostsCategory

sealed class Message {

    object OnNextClicked : Message()
    object OnPrevClicked : Message()
    object OnRetryClicked : Message()
    data class OnPostLoadedError(val category: PostsCategory, val error: Throwable) : Message()
    data class OnCategoryChanged(val postsCategory: PostsCategory) : Message()
    data class OnPostLoaded(val category: PostsCategory, val posts: List<Post>) : Message()
}
