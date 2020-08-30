package com.growd25.developerslife.presentation

import com.growd25.developerslife.model.Post
import com.growd25.developerslife.model.PostsCategory

data class State(
    var currentCategory: PostsCategory = PostsCategory.LATEST,
    val latestState: PostState = PostState(),
    val hotState: PostState = PostState(),
    val topState: PostState = PostState()
) {

    fun getPostState(category: PostsCategory) = when (category) {
        PostsCategory.LATEST -> latestState
        PostsCategory.HOT -> hotState
        PostsCategory.TOP -> topState
    }

    data class PostState(
        val posts: MutableList<Post> = mutableListOf(),
        var index: Int = 0,
        var page: Int = 0,
        var dataState: DataState = DataState.LOADED
    )
}