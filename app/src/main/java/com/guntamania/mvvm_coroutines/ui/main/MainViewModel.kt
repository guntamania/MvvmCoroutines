package com.guntamania.mvvm_coroutines.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guntamania.mvvm_coroutines.datasource.Repository
import com.guntamania.mvvm_coroutines.entities.Issue
import com.guntamania.mvvm_coroutines.ui.Contract
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: Repository
) : Contract.ViewModel, ViewModel() {

    private val exceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            Log.e("viewmodel", "例外キャッチ $throwable") // ログ出力のみ
        }

    private var callback: (() -> Unit)? = null

    override var onUpdate
        get() = callback
        set(value) {
            callback = value
        }
    override var dataList: LiveData<List<Issue>>
        get() = issues
        set(_) = Unit

    private var issues = MutableLiveData<List<Issue>>()

    override fun refresh() {
        load(1)
    }

    fun load(page: Int) {
        viewModelScope.launch(exceptionHandler) {
            repository.getIssues2(page).collect {
                issues.postValue(it)
            }
        }.invokeOnCompletion {
            callback?.invoke()
        }
    }

    init {
        refresh()
    }
}
