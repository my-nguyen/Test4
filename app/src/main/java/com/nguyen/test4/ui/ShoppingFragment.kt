package com.nguyen.test4.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nguyen.test4.R
import com.nguyen.test4.databinding.FragmentShoppingBinding

class ShoppingFragment: Fragment(R.layout.fragment_shopping) {
    lateinit var viewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[ShoppingViewModel::class.java]

        val binding = FragmentShoppingBinding.bind(view)
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(ShoppingFragmentDirections.actionShoppingFragmentToAddItemFragment())
        }
    }
}