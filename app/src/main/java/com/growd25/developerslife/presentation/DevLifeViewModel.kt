package com.growd25.developerslife.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.growd25.developerslife.model.Post
import com.growd25.developerslife.model.PostsCategory
import com.growd25.developerslife.repository.DevLifeRepository
import kotlinx.coroutines.*

class DevLifeViewModel(
    private val repository: DevLifeRepository
) : ViewModel() {

    private val _viewStateLiveData = MutableLiveData<ViewState>()
    val viewStateLiveData: LiveData<ViewState> = _viewStateLiveData
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val state = State()

    init {
        coroutineScope.launch { loadPosts(PostsCategory.LATEST, nextPage = 0) }
    }

    fun onNextClicked() {
        val postState = state.getPostState(state.currentCategory)
        postState.index++
        _viewStateLiveData.value = ViewState.fromState(state)
        if (postState.index == postState.posts.size) {
            coroutineScope.launch {
                loadPosts(state.currentCategory, nextPage = postState.page + 1)
            }
        }
    }

    fun onPrevClicked() {
        val postState = state.getPostState(state.currentCategory)
        if (postState.index == 0) return
        postState.index--
        _viewStateLiveData.value = ViewState.fromState(state)
    }

    fun onRetryClicked() {
        coroutineScope.launch {
            loadPosts(
                state.currentCategory,
                nextPage = state.getPostState(state.currentCategory).page + 1
            )
        }
    }

    fun onCategoryChanged(category: PostsCategory) {
        state.currentCategory = category
        val postState = state.getPostState(category)
        _viewStateLiveData.value = ViewState.fromState(state)
        if (postState.index == postState.posts.size) {
            coroutineScope.launch { loadPosts(category, nextPage = postState.page + 1) }
        }
    }

    private suspend fun loadPosts(category: PostsCategory, nextPage: Int) {
        if (state.getPostState(category).dataState != DataState.LOADING) {
            try {
                state.getPostState(category).dataState = DataState.LOADING
                _viewStateLiveData.value = ViewState.fromState(state)
                val posts: List<Post> = withContext(Dispatchers.IO) {
                    repository.getPosts(category, nextPage)
                }
                val postState = state.getPostState(category)
                postState.dataState = DataState.LOADED
                postState.page = nextPage
                postState.posts.addAll(posts)
                _viewStateLiveData.value = ViewState.fromState(state)

            } catch (th: Throwable) {
                Log.e("DevLifeViewModel", "load random post error", th)
                state.getPostState(category).dataState = DataState.ERROR
                _viewStateLiveData.value = ViewState.fromState(state)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}
