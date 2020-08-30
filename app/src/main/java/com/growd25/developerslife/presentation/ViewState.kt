package com.growd25.developerslife.presentation

import com.growd25.developerslife.model.Post
import com.growd25.developerslife.model.PostsCategory

data class ViewState(
    val category: PostsCategory,
    val post: Post?,
    val dataState: DataState,
    val isPrevEnabled: Boolean
) {

    companion object {
        fun fromState(state: State): ViewState {
            val postState = state.getPostState(state.currentCategory)
            return ViewState(
                category = state.currentCategory,
                post = postState.posts.getOrNull(postState.index),
                dataState = postState.dataState,
                isPrevEnabled = postState.index > 0
            )
        }
    }
}