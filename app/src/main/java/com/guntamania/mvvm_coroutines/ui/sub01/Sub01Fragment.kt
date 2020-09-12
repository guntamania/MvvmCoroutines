package com.guntamania.mvvm_coroutines.ui.sub01

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.guntamania.mvvm_coroutines.R
import com.guntamania.mvvm_coroutines.databinding.Sub01FragmentBinding
import com.guntamania.mvvm_coroutines.libs.InfiniteScrollListener
import com.guntamania.mvvm_coroutines.libs.RecyclerViewAdaptor
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class Sub01Fragment : Fragment() {

    private val sub01ViewModel: Sub01ViewModel by viewModel()
    private lateinit var viewDataBinding: ViewDataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.sub01_fragment, container, false)
        viewDataBinding = Sub01FragmentBinding.bind(view).apply {
            viewmodel = sub01ViewModel
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner

        val adaptor = RecyclerViewAdaptor(
            sub01ViewModel,
            this.viewLifecycleOwner
        )
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adaptor
        val infiniteScrollListener = InfiniteScrollListener { sub01ViewModel.load(it) }
        recyclerView.addOnScrollListener(infiniteScrollListener)

        sub01ViewModel.onUpdate = {
            adaptor.notifyDataSetChanged()
            infiniteScrollListener.finishLoading()
        }

    }
}