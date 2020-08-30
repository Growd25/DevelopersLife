package com.growd25.developerslife.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.growd25.developerslife.model.Post
import com.growd25.developerslife.repository.DevLifeRepository
import kotlinx.coroutines.*

class DevLifeViewModel(
    private val repository: DevLifeRepository
) : ViewModel() {

    private val _postStateLiveData = MutableLiveData<PostState>()
    val postStateLiveData: LiveData<PostState> = _postStateLiveData

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private val posts = mutableListOf<Post>()
    var index = 0

    init {
        coroutineScope.launch { loadRandomPost() }
    }

    fun onNextClicked() {
        coroutineScope.launch {
            if (posts.lastIndex == index) {
                loadRandomPost()
            } else {
                _postStateLiveData.value = PostState(
                    post = posts[++index],
                    dataState = DataState.LOADED,
                    isPrevEnabled = isPrevEnabled
                )
            }
        }
    }

    fun onPrevClicked() {
        if (!isPrevEnabled) return
        _postStateLiveData.value = PostState(
            post = posts[--index],
            dataState = DataState.LOADED,
            isPrevEnabled = isPrevEnabled
        )
    }

    fun onRetryClicked() {
        coroutineScope.launch { loadRandomPost() }
    }

    private suspend fun loadRandomPost() {
        if (_postStateLiveData.value?.dataState == DataState.LOADING) return
        _postStateLiveData.value = PostState(
            post = null,
            dataState = DataState.LOADING,
            isPrevEnabled = isPrevEnabled
        )
        _postStateLiveData.value = try {
            val post = withContext(Dispatchers.IO) { repository.getRandomPost() }
            posts.add(post)
            index = posts.lastIndex
            PostState(
                post = post,
                dataState = DataState.LOADED,
                isPrevEnabled = isPrevEnabled
            )
        } catch (th: Throwable) {
            Log.e("DevLifeViewModel", "load random post error", th)
            PostState(post = null, dataState = DataState.ERROR, isPrevEnabled)
        }
    }

    private val isPrevEnabled
        get() = index > 0

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}

data class PostState(val post: Post?, val dataState: DataState, val isPrevEnabled: Boolean)
enum class DataState { LOADING, LOADED, ERROR }