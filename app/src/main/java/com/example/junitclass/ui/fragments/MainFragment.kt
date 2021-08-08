package com.example.junitclass.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.junitclass.R
import com.example.junitclass.adapters.CartAdapter
import com.example.junitclass.ui.viewmodel.ItemViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment @Inject constructor(
    val cartAdapter: CartAdapter,
    var viewModel: ItemViewModel? = null
) : Fragment(R.layout.fragment_cart) {
    private lateinit var fab: FloatingActionButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = viewModel ?: ViewModelProvider(requireActivity()).get(ItemViewModel::class.java)
        subscribeToObservers()
        setupRecyclerView()

        fab = view.findViewById(R.id.floatingActionButton)

        fab.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToAddItemFragment())
        }
    }

    private val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
        0, LEFT or RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ) = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val pos = viewHolder.layoutPosition
            val item = cartAdapter.itemList[pos]
            viewModel?.deleteItem(item)
            Snackbar.make(requireView(), "DELETED ITEM", Snackbar.LENGTH_LONG).apply {
                setAction("Undo") {
                    viewModel?.insertItemIntoDb(item)
                }
                show()
            }
        }
    }

    private fun subscribeToObservers() {
        viewModel?.items?.observe(viewLifecycleOwner, Observer {
            cartAdapter.itemList = it
        })
        viewModel?.totalPrice?.observe(viewLifecycleOwner, Observer {
            val price = it ?: 0f
            val priceText = "Total price $$price"
            requireView().findViewById<TextView>(R.id.tvShoppingItemPrice).text = priceText
        })
    }

    private fun setupRecyclerView() {
        requireView().findViewById<RecyclerView>(R.id.rvShoppingItems).apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(requireContext())
            ItemTouchHelper(itemTouchCallback).attachToRecyclerView(this)
        }
    }
}