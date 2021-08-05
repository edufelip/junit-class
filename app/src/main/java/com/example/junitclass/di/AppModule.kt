package com.example.junitclass.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.junitclass.data.local.ItemDao
import com.example.junitclass.data.local.ItemDatabase
import com.example.junitclass.data.remote.PixaBayAPI
import com.example.junitclass.others.Constants.BASE_URL
import com.example.junitclass.others.Constants.DATABASE_NAME
import com.example.junitclass.repositories.DefaultItemRepository
import com.example.junitclass.repositories.IItemRepository
import com.example.junitclass.ui.viewmodel.ItemViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ItemDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideDefaultItemRepository(
        dao: ItemDao,
        api: PixaBayAPI
    ) = DefaultItemRepository(dao, api) as IItemRepository

    @Singleton
    @Provides
    fun provideItemDao(
        database: ItemDatabase
    ) = database.itemDao()

    @Singleton
    @Provides
    fun providePixabayApi(): PixaBayAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PixaBayAPI::class.java)
    }

}