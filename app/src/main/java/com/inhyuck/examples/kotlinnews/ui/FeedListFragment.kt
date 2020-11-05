package com.inhyuck.examples.kotlinnews.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.inhyuck.examples.kotlinnews.R
import com.inhyuck.examples.kotlinnews.databinding.FragmentFeedlistBinding
import com.inhyuck.examples.kotlinnews.db.AppDB
import com.inhyuck.examples.kotlinnews.viewmodel.FeedListVMFactory
import com.inhyuck.examples.kotlinnews.viewmodel.FeedListViewModel

class FeedListFragment: Fragment() {

    private val feedListViewModel: FeedListViewModel by viewModels { FeedListVMFactory.setDB(AppDB.getInstance(requireContext()))}

    private lateinit var binding: FragmentFeedlistBinding

    private lateinit var adapter: FeedAdapter

    private fun initFeedList(viewModel: FeedListViewModel){
        viewModel.feedList.observe(viewLifecycleOwner, Observer { listResource ->
            if (listResource != null){
                adapter.submitList(listResource)
            }else{
                adapter.submitList(emptyList())
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentFeedlistBinding>(
            inflater,
            R.layout.fragment_feedlist,
            container,
            false
        )
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)

        binding = dataBinding
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.feedlist = feedListViewModel.feedList

        val adapter= FeedAdapter{
            val bundle = bundleOf(
                "feed_id" to it
            )
            findNavController().navigate(
                R.id.feedDetail,
                bundle
            )
        }

        this.adapter = adapter
        binding.recyclerview.adapter = adapter
        postponeEnterTransition()

        binding.recyclerview.doOnPreDraw {
            startPostponedEnterTransition()
        }

        feedListViewModel.foundError.observe(viewLifecycleOwner, Observer { msg ->
            msg?.let { it ->
                Snackbar.make(view, it, Snackbar.LENGTH_LONG).show()
                feedListViewModel.foundError.value = null
            }
        })

        //TODO: Check internet connectivity
        initFeedList(feedListViewModel)

        super.onViewCreated(view, savedInstanceState)
    }
}