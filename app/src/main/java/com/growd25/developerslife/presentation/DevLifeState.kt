package com.growd25.developerslife.presentation

import com.growd25.developerslife.model.PostsCategory

data class DevLifeState(
    val category: PostsCategory,
    val postState: PostState,
    val isPrevButtonEnabled:Boolean,
)
