package com.example.junitclass.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.junitclass.R
import com.example.junitclass.ui.interfaces.OnBackPressed
import com.example.junitclass.ui.viewmodel.ItemViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddItemFragment : Fragment(R.layout.fragment_add_item), OnBackPressed {
    val viewModel: ItemViewModel by activityViewModels()
    lateinit var imageView: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView = view.findViewById(R.id.ivShoppingImage)

        imageView.setOnClickListener{
            findNavController().navigate(AddItemFragmentDirections.actionAddItemFragmentToImagePickFragment())
        }
    }

    private fun setImageUrl() {
        viewModel.setCurrentImageUrl("")
    }

    override fun onBackPressed() {
        setImageUrl()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}