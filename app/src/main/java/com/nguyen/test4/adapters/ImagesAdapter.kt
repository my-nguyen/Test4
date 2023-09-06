package com.nguyen.test4.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.nguyen.test4.databinding.ItemImageBinding
import javax.inject.Inject

class ImagesAdapter @Inject constructor(val glide: RequestManager) : RecyclerView.Adapter<ImagesAdapter.ViewHolder> () {
    private val callback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, callback)
    var images: List<String>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    private var onClickListener: ((String) -> Unit)? = null

    inner class ViewHolder(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(url: String) {
            binding.apply {
                glide.load(url).into(itemImage)
                setOnClickListener {
                    onClickListener?.let { click ->
                        click(url)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemImageBinding.inflate(inflater, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = images.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position])
    }

    fun setOnClickListener(listener: (String) -> Unit) {
        onClickListener = listener
    }
}