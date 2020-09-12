package com.guntamania.mvvm_coroutines.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.guntamania.mvvm_coroutines.R
import com.guntamania.mvvm_coroutines.databinding.MainFragmentBinding
import com.guntamania.mvvm_coroutines.libs.InfiniteScrollListener
import com.guntamania.mvvm_coroutines.libs.RecyclerViewAdaptor
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var viewDataBinding: ViewDataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        viewDataBinding = MainFragmentBinding.bind(view).apply {
            viewmodel = mainViewModel
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner

        val adaptor = RecyclerViewAdaptor(
            mainViewModel,
            this.viewLifecycleOwner
        )
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adaptor
        val infiniteScrollListener = InfiniteScrollListener { mainViewModel.load(it) }
        recyclerView.addOnScrollListener(infiniteScrollListener)

        mainViewModel.onUpdate = {
            adaptor.notifyDataSetChanged()
            infiniteScrollListener.finishLoading()
        }
    }

}