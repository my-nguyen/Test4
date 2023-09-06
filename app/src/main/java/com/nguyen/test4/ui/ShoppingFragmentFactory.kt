package com.nguyen.test4.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.nguyen.test4.adapters.ImagesAdapter
import javax.inject.Inject

class ShoppingFragmentFactory @Inject constructor(val adapter: ImagesAdapter, val glide: RequestManager): FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            ImagePickFragment::class.java.name -> ImagePickFragment(adapter)
            AddItemFragment::class.java.name -> AddItemFragment(glide)
            else -> super.instantiate(classLoader, className)
        }
    }
}