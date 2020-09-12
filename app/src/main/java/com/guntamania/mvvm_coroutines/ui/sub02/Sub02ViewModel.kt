package com.guntamania.mvvm_coroutines.ui.sub02

import androidx.lifecycle.*
import com.guntamania.mvvm_coroutines.datasource.Repository
import com.guntamania.mvvm_coroutines.entities.Issue
import com.guntamania.mvvm_coroutines.ui.Contract

class Sub02ViewModel(
    private val repository: Repository
) : Contract.ViewModel, ViewModel() {

    override var onUpdate
        get() = callback
        set(value) {
            callback = value
        }

    private var callback: (() -> Unit)? = null

    override var dataList: LiveData<List<Issue>>
        get() = issues
        set(value) {
            issues = value
        }

    private var fresh = MutableLiveData<Int>()

    private var issues = Transformations.switchMap(fresh) { page ->
        liveData<List<Issue>> {
            runCatching {
                repository.getIssues1(page)
            }.onSuccess {
                emit(it)
                callback?.invoke()
            }
                .onFailure { }
        }
    }

    fun load(page: Int) {
        fresh.value = page
    }

    init {
        refresh()
    }

    override fun refresh() {
        // 汚いが、他のレポジトリでも（主にView層で）やっている..
        issues.observeForever { }
        load(1)
    }

    override fun onCleared() {
        super.onCleared()
        issues.removeObserver { }
    }
}