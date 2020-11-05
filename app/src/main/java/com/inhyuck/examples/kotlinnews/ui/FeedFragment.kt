package com.inhyuck.examples.kotlinnews.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.inhyuck.examples.kotlinnews.R
import com.inhyuck.examples.kotlinnews.databinding.FragmentFeedBinding
import com.inhyuck.examples.kotlinnews.db.AppDB

class FeedFragment : Fragment(){

    private lateinit var binding: FragmentFeedBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentFeedBinding>(
            inflater,
            R.layout.fragment_feed,
            container,
            false
        )

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        binding = dataBinding
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner

        val feedID = arguments?.getString("feed_id")
        feedID?.let {
            binding.feed = AppDB.getInstance(requireContext()).feedDao().lookupFeed(it)
        }

        super.onViewCreated(view, savedInstanceState)
    }

}