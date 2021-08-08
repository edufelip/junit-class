package com.example.junitclass.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.example.junitclass.adapters.CartAdapter
import com.example.junitclass.adapters.ImageAdapter
import javax.inject.Inject

class MainFragmentFactory @Inject constructor(
    private val imageAdapter: ImageAdapter,
    private val glide: RequestManager,
    private val cartAdapter: CartAdapter
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            MainFragment::class.java.name -> {
                MainFragment(cartAdapter)
            }
            ImagePickFragment::class.java.name -> {
                ImagePickFragment(imageAdapter)
            }
            AddItemFragment::class.java.name -> {
                AddItemFragment(glide)
            }
            else -> super.instantiate(classLoader, className)
        }
    }
}