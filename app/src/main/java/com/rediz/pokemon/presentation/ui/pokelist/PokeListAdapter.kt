package com.rediz.pokemon.presentation.ui.pokelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rediz.pokemon.common.glide
import com.rediz.pokemon.databinding.ItemPokeBinding
import com.rediz.pokemon.domain.model.PokeDetail

class PokeListAdapter : PagingDataAdapter<PokeDetail, PokeListAdapter.VH>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemPokeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item)
    }

    inner class VH(
        private val binding: ItemPokeBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PokeDetail) {
            binding.thumb.glide(item.imageUrl)
            binding.index.text = String.format("NO. %03d", item.index)
            binding.name.text = item.name
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<PokeDetail>() {
            override fun areItemsTheSame(oldItem: PokeDetail, newItem: PokeDetail): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: PokeDetail, newItem: PokeDetail): Boolean =
                oldItem.index == newItem.index
        }
    }
}