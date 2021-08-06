package com.example.junitclass.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.junitclass.R
import com.example.junitclass.data.local.ItemDao
import com.example.junitclass.data.local.ItemDatabase
import com.example.junitclass.data.remote.PixabayApi
import com.example.junitclass.others.Constants.BASE_URL
import com.example.junitclass.others.Constants.DATABASE_NAME
import com.example.junitclass.repositories.DefaultItemRepository
import com.example.junitclass.repositories.IItemRepository
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
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ItemDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideDefaultShoppingRepository(
        dao: ItemDao,
        api: PixabayApi
    ) = DefaultItemRepository(dao, api) as IItemRepository

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_background)
    )

    @Singleton
    @Provides
    fun provideShoppingDao(
        database: ItemDatabase
    ) = database.itemDao()

    @Singleton
    @Provides
    fun providePixabayApi(): PixabayApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PixabayApi::class.java)
    }
}