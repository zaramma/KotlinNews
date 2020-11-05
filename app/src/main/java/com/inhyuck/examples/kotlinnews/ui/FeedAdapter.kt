package com.inhyuck.examples.kotlinnews.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.inhyuck.examples.kotlinnews.ui.base.DataBoundListAdapter
import com.inhyuck.examples.kotlinnews.R
import com.inhyuck.examples.kotlinnews.databinding.ItemFeedlistBinding
import com.inhyuck.examples.kotlinnews.db.entity.Feed

class FeedAdapter(
    private val callback: ((String) -> Unit)?
) : DataBoundListAdapter<Feed, ItemFeedlistBinding>(
    diffCallback = object :DiffUtil.ItemCallback<Feed>() {
        override fun areItemsTheSame(oldItem: Feed, newItem: Feed): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Feed, newItem: Feed): Boolean {
            return oldItem.id == newItem.id
        }
    }
) {
    override fun createBinding(parent: ViewGroup): ItemFeedlistBinding {
        val binding = DataBindingUtil.inflate<ItemFeedlistBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_feedlist,
            parent,
            false
        )
        binding.root.setOnClickListener{
            binding.feed?.id?.let {
                callback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: ItemFeedlistBinding, item: Feed) {
        binding.feed = item
    }
}