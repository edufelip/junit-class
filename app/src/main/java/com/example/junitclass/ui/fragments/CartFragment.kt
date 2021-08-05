package com.example.junitclass.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.junitclass.R
import com.example.junitclass.ui.viewmodel.ItemViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment: Fragment(R.layout.fragment_cart) {
    val viewModel: ItemViewModel by viewModels()
    private lateinit var fab: FloatingActionButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewModel = ViewModelProvider(requireActivity()).get(ItemViewModel::class.java)

        fab = view.findViewById(R.id.floatingActionButton)

        fab.setOnClickListener {
            findNavController().navigate(CartFragmentDirections.actionCartFragmentToAddItemFragment())
        }
    }
}