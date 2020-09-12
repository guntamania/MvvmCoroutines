package com.guntamania.mvvm_coroutines.ui

import androidx.lifecycle.LiveData
import com.guntamania.mvvm_coroutines.entities.Issue

interface Contract {
    interface ViewModel {
        var onUpdate: (() -> Unit)?
        var dataList: LiveData<List<Issue>>

        fun refresh()
    }
}