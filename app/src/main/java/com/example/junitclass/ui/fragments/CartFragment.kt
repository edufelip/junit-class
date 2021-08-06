package com.example.junitclass.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.junitclass.R
import com.example.junitclass.ui.viewmodel.ItemViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment: Fragment(R.layout.fragment_cart) {
    val viewModel: ItemViewModel by activityViewModels()
    private lateinit var fab: FloatingActionButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab = view.findViewById(R.id.floatingActionButton)

        fab.setOnClickListener {
            findNavController().navigate(CartFragmentDirections.actionCartFragmentToAddItemFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}