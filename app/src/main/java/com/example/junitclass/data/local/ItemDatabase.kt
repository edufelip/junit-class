package com.example.junitclass.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dagger.Provides
import javax.inject.Singleton

@Database(
    entities = [Item::class],
    version = 1,
    exportSchema = false
)

abstract class ItemDatabase: RoomDatabase() {
    abstract fun itemDao(): ItemDao
}