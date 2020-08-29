package com.growd25.developerslife.presentation

import com.growd25.developerslife.model.Post

sealed class PostState {

    object Loading : PostState()
    data class Loaded(val post: Post) : PostState()
    object Error : PostState()
}
