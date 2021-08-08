package com.example.junitclass.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.junitclass.R
import com.example.junitclass.data.local.Item
import com.google.android.material.textview.MaterialTextView
import javax.inject.Inject

class CartAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    class CartViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id != newItem.id
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var itemList: List<Item>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_cart,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = itemList[position]
        holder.itemView.apply {
            glide.load(item.image).into(this.findViewById(R.id.ivShoppingImage))
            this.findViewById<MaterialTextView>(R.id.tvName).text = item.name
            this.findViewById<MaterialTextView>(R.id.tvShoppingItemAmount).text = item.quantity.toString()
            this.findViewById<MaterialTextView>(R.id.tvShoppingItemPrice).text = item.price.toString()
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}

