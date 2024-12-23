package com.cumaliguzel.apps.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ClothesViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClothesViewModel::class.java)) {
            return ClothesViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
