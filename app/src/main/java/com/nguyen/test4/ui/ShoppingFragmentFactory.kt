package com.nguyen.test4.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.nguyen.test4.adapters.ImagesAdapter
import com.nguyen.test4.adapters.ItemsAdapter
import javax.inject.Inject

class ShoppingFragmentFactory @Inject constructor(val imagesAdapter: ImagesAdapter, val glide: RequestManager, val itemsAdapter: ItemsAdapter): FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            ImagePickFragment::class.java.name -> ImagePickFragment(imagesAdapter)
            AddItemFragment::class.java.name -> AddItemFragment(glide)
            ShoppingFragment::class.java.name -> ShoppingFragment(itemsAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}