package com.guntamania.mvvm_coroutines.libs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.guntamania.mvvm_coroutines.R
import com.guntamania.mvvm_coroutines.databinding.IssueItemBinding
import com.guntamania.mvvm_coroutines.ui.Contract

class RecyclerViewAdaptor(
    private val viewModel: Contract.ViewModel,
    private val parentLifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = DataBindingUtil.inflate<IssueItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.issue_item,
            parent,
            false
        )
        return MainViewHolder(binding)
    }

    override fun getItemCount(): Int = viewModel.dataList.value?.size ?: 0

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.binding.viewmodel = viewModel
        holder.binding.position = position
        holder.binding.lifecycleOwner = parentLifecycleOwner
    }
}

class MainViewHolder(
    val binding: IssueItemBinding
) : RecyclerView.ViewHolder(binding.root) {

}