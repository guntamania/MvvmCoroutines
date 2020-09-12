package com.guntamania.mvvm_coroutines.ui.sub01

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guntamania.mvvm_coroutines.datasource.Repository
import com.guntamania.mvvm_coroutines.entities.Issue
import com.guntamania.mvvm_coroutines.ui.Contract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Sub01ViewModel(
    private val repository: Repository
) : Contract.ViewModel, ViewModel() {

    override var onUpdate
        get() = callback
        set(value) {
            callback = value
        }

    override var dataList: LiveData<List<Issue>>
        get() = issues
        set(_) = Unit

    private var issues = MutableLiveData<List<Issue>>()

    private var callback: (() -> Unit)? = null

    init {
        refresh()
    }

    override fun refresh() = load(1)

    fun load(page: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            val value = repository.getIssues(viewModelScope, page).await()
            issues.postValue(value)
        }.invokeOnCompletion {
            callback?.invoke()
        }
    }
}