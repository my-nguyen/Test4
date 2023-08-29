package com.nguyen.test4.ui

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nguyen.test4.R
import com.nguyen.test4.databinding.FragmentAddItemBinding

class AddItemFragment: Fragment(R.layout.fragment_add_item) {
    lateinit var viewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[ShoppingViewModel::class.java]

        val binding = FragmentAddItemBinding.bind(view)
        binding.image.setOnClickListener {
            findNavController().navigate(AddItemFragmentDirections.actionAddItemFragmentToImagePickFragment())
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.setImageUrl("")
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }
}