package com.growd25.developerslife.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.growd25.developerslife.model.PostsCategory
import com.growd25.developerslife.repository.DevlLifeRepository
import com.growd25.developerslife.ui.DevLifeViewState
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

class DevLifeViewModel(
    private val repository: DevlLifeRepository
) : ViewModel() {

    private val _viewState = MutableLiveData<DevLifeViewState>()
    val viewState: LiveData<DevLifeViewState> = _viewState
    private var state: DevLifeState = DevLifeState()
    private val loadDataCommandSubject: Subject<LoadDataCommand> = PublishSubject.create()
    private var loadDataDisposable: Disposable? = null

    init {
        Log.i("den","createViewModel")
        loadDataDisposable = loadDataCommandSubject
            .flatMap { cmd ->
                repository.getPosts(cmd.category, cmd.pageNumber)
                    .map<Message> { posts -> Message.OnPostLoaded(cmd.category, posts) }
                    .onErrorReturn { e -> Message.OnPostLoadedError(cmd.category, e) }
                    .toObservable()
            }
            .subscribe { msg ->
                acceptMessage(msg)
            }
        loadDataCommandSubject.onNext(
            LoadDataCommand(
                state.currentCategory,
                state.getCurrentCategoryState().pageNumber
            )
        )
    }

    fun acceptMessage(msg: Message) {
        val result = reduce(state, msg)
        _viewState.value = stateToViewState(result.state)
        result.loadDataCommand?.let { loadDataCommandSubject.onNext(it) }
        state = result.state
    }

    private fun reduce(devLifeState: DevLifeState, msg: Message): ReduceResult =
        when (msg) {
            is Message.OnNextClicked -> {
                val currentCategoryState = devLifeState.getCurrentCategoryState()
                if (currentCategoryState.index < currentCategoryState.posts.size - 1 && currentCategoryState.dataState == PostCategoryState.DataState.LOADED) {
                    val newCategoryState =
                        currentCategoryState.copy(index = currentCategoryState.index + 1)
                    val newState = devLifeState.copyCurrentCategoryState(newCategoryState)
                    ReduceResult(newState)
                } else {
                    val newCategoryState =
                        currentCategoryState.copy(dataState = PostCategoryState.DataState.LOADING)
                    val newState = devLifeState.copyCurrentCategoryState(newCategoryState)

                    ReduceResult(
                        newState,
                        LoadDataCommand(devLifeState.currentCategory, newCategoryState.pageNumber)
                    )
                }
            }
            is Message.OnPrevClicked -> {
                val currentCategoryState = devLifeState.getCurrentCategoryState()
                val newCategoryState =
                    if (currentCategoryState.index != 0 && currentCategoryState.dataState == PostCategoryState.DataState.LOADED) {
                        currentCategoryState.copy(index = currentCategoryState.index - 1)
                    } else {
                        currentCategoryState
                    }

                val newState = devLifeState.copyCurrentCategoryState(newCategoryState)
                ReduceResult(newState)
            }
            is Message.OnCategoryChanged -> {
                val categoryState = devLifeState.getCategoryState(msg.postsCategory)
               val loadDataCommand = if (categoryState.posts.isEmpty()) {
                    LoadDataCommand(msg.postsCategory, categoryState.pageNumber)
                } else {
                    null
                }
                ReduceResult(devLifeState.copy(currentCategory = msg.postsCategory),loadDataCommand)
            }
            is Message.OnPostLoaded -> {
                val categoryState = devLifeState.getCategoryState(msg.category)

                val newCategoryState = categoryState.copy(
                    posts = categoryState.posts + msg.posts,
                    index = categoryState.index + 1,
                    pageNumber = categoryState.pageNumber + 1,
                    dataState = PostCategoryState.DataState.LOADED
                )

                ReduceResult(devLifeState.copyCategoryState(msg.category, newCategoryState))

            }
            is Message.OnPostLoadedError -> {
                Log.e("DevLifeViewModel", "OnPostLoadedError category ${msg.category}", msg.error)
                val categoryState = devLifeState.getCategoryState(msg.category)
                val newCategoryState =
                    categoryState.copy(dataState = PostCategoryState.DataState.ERROR)
                val newState = devLifeState.copyCategoryState(msg.category, newCategoryState)
                ReduceResult(newState)

            }
            is Message.OnRetryClicked -> {
                val categoryState = devLifeState.getCurrentCategoryState()
                val newCategoryState =
                    categoryState.copy(dataState = PostCategoryState.DataState.LOADING)
                val newState = devLifeState.copyCurrentCategoryState(newCategoryState)
                ReduceResult(
                    newState,
                    LoadDataCommand(devLifeState.currentCategory, newCategoryState.pageNumber)
                )
            }
        }

    private fun stateToViewState(state: DevLifeState): DevLifeViewState {
        val categoryState = state.getCurrentCategoryState()
        return DevLifeViewState(
            category = state.currentCategory,
            isProgressVisible = categoryState.dataState == PostCategoryState.DataState.LOADING,
            isErrorVisible = categoryState.dataState == PostCategoryState.DataState.ERROR,
            post = categoryState.posts.getOrNull(categoryState.index),
            isPrevButtonEnabled = categoryState.index != 0,
            isPostVisible = categoryState.dataState == PostCategoryState.DataState.LOADED
        )
    }

    override fun onCleared() {
        super.onCleared()
        loadDataDisposable?.dispose()
    }

    private class ReduceResult(
        val state: DevLifeState,
        val loadDataCommand: LoadDataCommand? = null
    )

    private class LoadDataCommand(val category: PostsCategory, val pageNumber: Int)
}
