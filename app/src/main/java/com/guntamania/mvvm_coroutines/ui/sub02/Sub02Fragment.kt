package com.guntamania.mvvm_coroutines.ui.sub02

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.guntamania.mvvm_coroutines.R
import com.guntamania.mvvm_coroutines.databinding.Sub02FragmentBinding
import com.guntamania.mvvm_coroutines.libs.InfiniteScrollListener
import com.guntamania.mvvm_coroutines.libs.RecyclerViewAdaptor
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class Sub02Fragment : Fragment() {

    private val viewModel: Sub02ViewModel by viewModel()
    private lateinit var viewDataBinding: ViewDataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.sub02_fragment, container, false)
        viewDataBinding = Sub02FragmentBinding.bind(view).apply {
            viewmodel = viewModel
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner

        val adaptor = RecyclerViewAdaptor(
            viewModel,
            this.viewLifecycleOwner
        )
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adaptor
        val infiniteScrollListener = InfiniteScrollListener {
            viewModel.load(it)
        }
        recyclerView.addOnScrollListener(infiniteScrollListener)

        viewModel.onUpdate = {
            infiniteScrollListener.finishLoading()
            adaptor.notifyDataSetChanged()
        }

    }

}