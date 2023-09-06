package com.nguyen.test4.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.nguyen.test4.data.room.ShoppingItem
import com.nguyen.test4.databinding.ItemShoppingBinding
import javax.inject.Inject

class ItemsAdapter @Inject constructor(val glide: RequestManager) : RecyclerView.Adapter<ItemsAdapter.ViewHolder> () {
    private val callback = object : DiffUtil.ItemCallback<ShoppingItem>() {
        override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem) = oldItem == newItem

        override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem) = oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, callback)
    var items: List<ShoppingItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    private var onClickListener: ((String) -> Unit)? = null

    inner class ViewHolder(val binding: ItemShoppingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ShoppingItem) {
            binding.apply {
                glide.load(item.imageUrl).into(shopImage)
                setOnClickListener {
                    onClickListener?.let { click ->
                        click(item.imageUrl)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemShoppingBinding.inflate(inflater, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setOnClickListener(listener: (String) -> Unit) {
        onClickListener = listener
    }
}