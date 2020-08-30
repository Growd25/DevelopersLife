package com.growd25.developerslife.presentation

import com.growd25.developerslife.model.Post

data class PostCategoryState(
    val posts: List<Post> = emptyList(),
    val index: Int = -1,
    val pageNumber: Int = 0,
    val dataState: DataState = DataState.LOADING
) {

    enum class DataState {
        LOADING, LOADED, ERROR
    }
}
