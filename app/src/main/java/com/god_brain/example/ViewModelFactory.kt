package com.god_brain.example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.god_brain.example.data.repository.MainRepository
import com.god_brain.example.ui.view_01_store_list.StoreListViewModel

class ViewModelFactory(
    private val repository: MainRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(StoreListViewModel::class.java)) {
            return StoreListViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}