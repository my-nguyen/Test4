package com.nguyen.test4.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.nguyen.test4.R
import com.nguyen.test4.adapters.ItemsAdapter
import com.nguyen.test4.databinding.FragmentShoppingBinding
import javax.inject.Inject

class ShoppingFragment @Inject constructor(val itemsAdapter: ItemsAdapter, var viewModel: ShoppingViewModel? = null): Fragment(R.layout.fragment_shopping) {
    private val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, LEFT or RIGHT) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.layoutPosition
            val item = itemsAdapter.items[position]
            viewModel?.deleteShoppingItem(item)
            Snackbar.make(requireView(), "Successfully deleted item", Snackbar.LENGTH_LONG).apply {
                setAction("Undo") {
                    viewModel?.insertShoppingItem(item)
                }
                show()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = viewModel ?: ViewModelProvider(requireActivity())[ShoppingViewModel::class.java]

        val binding = FragmentShoppingBinding.bind(view)
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(ShoppingFragmentDirections.actionShoppingFragmentToAddItemFragment())
        }

        binding.items.apply {
            adapter = itemsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            ItemTouchHelper(itemTouchCallback).attachToRecyclerView(this)
        }

        viewModel?.items?.observe(viewLifecycleOwner) { items ->
            itemsAdapter.items = items
        }
        viewModel?.price?.observe(viewLifecycleOwner) {
            val price = it ?: 0f
            binding.price.text = "Total price: $price"
        }
    }
}