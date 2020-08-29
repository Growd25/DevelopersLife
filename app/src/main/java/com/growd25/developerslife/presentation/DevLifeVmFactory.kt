package com.growd25.developerslife.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.growd25.developerslife.repository.DevlLifeRepository
import javax.inject.Inject

class DevLifeVmFactory @Inject constructor  (private val repository: DevlLifeRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DevLifeViewModel::class.java)) {
            return DevLifeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}