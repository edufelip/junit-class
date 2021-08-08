package com.example.junitclass.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.example.junitclass.adapters.CartAdapter
import com.example.junitclass.adapters.ImageAdapter
import com.example.junitclass.repositories.FakeItemRepositoryAndroidTest
import com.example.junitclass.ui.fragments.AddItemFragment
import com.example.junitclass.ui.fragments.ImagePickFragment
import com.example.junitclass.ui.fragments.MainFragment
import com.example.junitclass.ui.viewmodel.ItemViewModel
import javax.inject.Inject

class TestMainFragmentFactory @Inject constructor(
    private val imageAdapter: ImageAdapter,
    private val glide: RequestManager,
    private val cartAdapter: CartAdapter
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            MainFragment::class.java.name -> {
                MainFragment(cartAdapter, ItemViewModel(FakeItemRepositoryAndroidTest()))
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