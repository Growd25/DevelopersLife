package com.growd25.developerslife.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.growd25.developerslife.model.PostsCategory
import com.growd25.developerslife.repository.DevlLifeRepository

class DevLifeViewModel(
    private val repository: DevlLifeRepository
) : ViewModel() {

    private val _state = MutableLiveData<DevLifeState>()
    val state: LiveData<DevLifeState> = _state

    init {
        _state.value = DevLifeState(
            category = PostsCategory.LATEST,
            postState = PostState.Loading,
            isPrevButtonEnabled = false
        )
    }
}
