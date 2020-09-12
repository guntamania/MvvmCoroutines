package com.guntamania.mvvm_coroutines.libs

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InfiniteScrollListener(
    private val onScrolled: (page: Int) -> Unit
) : RecyclerView.OnScrollListener() {

    private var previousTotal = 0
    private var loading = true
    private var currentPage = 1

    fun finishLoading() {
        loading = false
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy > 0) {
            val visibleItemCount = recyclerView.childCount
            val totalItemCount = recyclerView.layoutManager?.itemCount ?: 0
            val firstVisibleItem =
                (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false
                    previousTotal = totalItemCount
                }
            }
            if (
                !loading &&
                (totalItemCount - visibleItemCount) <= (firstVisibleItem + 2 /*最後の行から２つ上*/)
            ) {
                currentPage++
                // End has been reached
                onScrolled.invoke(currentPage) //呼び出し元で実装
                loading = true
            }
        }

    }
}
