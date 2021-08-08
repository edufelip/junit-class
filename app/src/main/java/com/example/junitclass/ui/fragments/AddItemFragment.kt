package com.example.junitclass.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.example.junitclass.R
import com.example.junitclass.others.Status
import com.example.junitclass.ui.interfaces.OnBackPressed
import com.example.junitclass.ui.viewmodel.ItemViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddItemFragment @Inject constructor(
    val glide: RequestManager
) : Fragment(R.layout.fragment_add_item), OnBackPressed {
    lateinit var viewModel: ItemViewModel
    lateinit var imageView: ImageView
    lateinit var addButton: MaterialButton
    lateinit var etItemName: TextInputEditText
    lateinit var etItemAmount: TextInputEditText
    lateinit var etItemPrice: TextInputEditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ItemViewModel::class.java)

        setupComponents(view)
//        subscribeToObservers()

        addButton.setOnClickListener{
            viewModel.insertItem(
                etItemName.text.toString(),
                etItemAmount.text.toString(),
                etItemPrice.text.toString()
            )
        }

        imageView.setOnClickListener{
            findNavController().navigate(AddItemFragmentDirections.actionAddItemFragmentToImagePickFragment())
        }
    }

//    private fun subscribeToObservers() {
//        viewModel.currentImageUrl.observe(viewLifecycleOwner, Observer {
//            glide.load(it).into(imageView)
//        })
//        viewModel.insertItemStatus.observe(viewLifecycleOwner, Observer {
//            it.getContentIfNotHandled()?.let { result ->
//                when(result.status) {
//                    Status.SUCCESS -> {
//                        Snackbar.make(requireActivity().findViewById(R.id.rootLayout),
//                            "Added Item",
//                            Snackbar.LENGTH_LONG
//                        ).show()
//                        findNavController().popBackStack()
//                    }
//                    Status.ERROR -> {
//                        Snackbar.make(requireActivity().findViewById(R.id.rootLayout),
//                            result.message ?: "Unknown error",
//                            Snackbar.LENGTH_LONG
//                        ).show()
//
//                    }
//                    Status.LOADING -> {
//                        // Do nothing
//                    }
//                }
//            }
//        })
//    }

    private fun setImageUrl() {
        viewModel.setCurrentImageUrl("")
    }

    override fun onBackPressed() {
        setImageUrl()
    }

    private fun setupComponents(view: View) {
        imageView = view.findViewById(R.id.ivShoppingImage)
        addButton = view.findViewById(R.id.btnAddShoppingItem)
        etItemName = view.findViewById(R.id.etShoppingItemName)
        etItemAmount = view.findViewById(R.id.etShoppingItemAmount)
        etItemPrice = view.findViewById(R.id.etShoppingItemPrice)
    }
}