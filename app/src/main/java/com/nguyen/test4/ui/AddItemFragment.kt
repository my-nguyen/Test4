package com.nguyen.test4.ui

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.google.android.material.snackbar.Snackbar
import com.nguyen.test4.R
import com.nguyen.test4.Status
import com.nguyen.test4.databinding.FragmentAddItemBinding
import javax.inject.Inject

class AddItemFragment @Inject constructor(val glide: RequestManager): Fragment(R.layout.fragment_add_item) {
    lateinit var viewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[ShoppingViewModel::class.java]

        val binding = FragmentAddItemBinding.bind(view)

        viewModel.imageUrl.observe(viewLifecycleOwner) { url ->
            glide.load(url).into(binding.addImage)
        }
        viewModel.insertStatus.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        Snackbar.make(requireActivity().findViewById(R.id.rootLayout), "Added shopping item", Snackbar.LENGTH_LONG).show()
                        findNavController().popBackStack()
                    }
                    Status.ERROR -> {
                        Snackbar.make(requireActivity().findViewById(R.id.rootLayout), resource.message ?: "Unknown error", Snackbar.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        /* NO-OP */
                    }
                }
            }
        }

        binding.addItem.setOnClickListener {
            viewModel.insertShoppingItem(binding.name.text.toString(), binding.amount.text.toString(), binding.price.text.toString())
        }

        binding.addImage.setOnClickListener {
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

    /*private fun subscribe() {
        viewModel.imageUrl.observe(viewLifecycleOwner) { url ->
            glide.load(url).into(binaddImage)
        }
        viewModel.insertStatus.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { resource ->  
                when (resource.status) {
                    Status.SUCCESS -> {
                        Snackbar.make(requireActivity().rootLayout, "Added shopping item", Snackbar.LENGTH_LONG).show()
                        findNavController().popBackStack()
                    }
                    Status.ERROR -> {
                        Snackbar.make(requireActivity().rootLayout, resource.message ?: "Unknown error", Snackbar.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        *//* NO-OP *//*
                    }
                }
            }
        }
    }*/
}