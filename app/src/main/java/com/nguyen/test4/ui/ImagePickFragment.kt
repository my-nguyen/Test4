package com.nguyen.test4.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.nguyen.test4.Constants.GRID_SPAN_COUNT
import com.nguyen.test4.R
import com.nguyen.test4.adapters.ImagesAdapter
import com.nguyen.test4.databinding.FragmentImagePickBinding
import javax.inject.Inject

class ImagePickFragment @Inject constructor(val imagesAdapter: ImagesAdapter): Fragment(R.layout.fragment_image_pick) {
    lateinit var viewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentImagePickBinding.bind(view)
        binding.recycler.apply {
            adapter = imagesAdapter
            layoutManager = GridLayoutManager(requireContext(), GRID_SPAN_COUNT)
        }

        viewModel = ViewModelProvider(requireActivity())[ShoppingViewModel::class.java]
        imagesAdapter.setOnClickListener {
            findNavController().popBackStack()
            viewModel.setImageUrl(it)
        }
    }
}