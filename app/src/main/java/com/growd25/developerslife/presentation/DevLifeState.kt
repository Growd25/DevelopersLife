package com.growd25.developerslife.presentation

import com.growd25.developerslife.model.PostsCategory

data class DevLifeState(
    val currentCategory: PostsCategory = PostsCategory.LATEST,
    val latestCategoryState: PostCategoryState = PostCategoryState(),
    val hotCategoryState: PostCategoryState = PostCategoryState(),
    val topCategoryState: PostCategoryState = PostCategoryState()
) {
    fun getCurrentCategoryState() =
        getCategoryState(currentCategory)

    fun copyCategoryState(category: PostsCategory, categoryState: PostCategoryState) =
        when (category) {
            PostsCategory.LATEST -> copy(latestCategoryState = categoryState)
            PostsCategory.TOP -> copy(topCategoryState = categoryState)
            PostsCategory.HOT -> copy(hotCategoryState = categoryState)
        }

    fun copyCurrentCategoryState(categoryState: PostCategoryState) =
        copyCategoryState(currentCategory, categoryState)

    fun getCategoryState(category: PostsCategory) =
        when (category) {
            PostsCategory.LATEST -> latestCategoryState
            PostsCategory.TOP -> topCategoryState
            PostsCategory.HOT -> hotCategoryState
        }
}
