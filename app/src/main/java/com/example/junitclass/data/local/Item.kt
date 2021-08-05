package com.example.junitclass.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
    var name: String,
    var quantity: Int,
    var price: Float,
    var image: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
) {
}