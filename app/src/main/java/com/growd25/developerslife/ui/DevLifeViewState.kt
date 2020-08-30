package com.growd25.developerslife.ui

import com.growd25.developerslife.model.Post
import com.growd25.developerslife.model.PostsCategory

data class DevLifeViewState(
    val category: PostsCategory,
    val isProgressVisible:Boolean,
    val isErrorVisible:Boolean,
    val post:Post?,
    val isPrevButtonEnabled:Boolean,
    val isPostVisible:Boolean
)
