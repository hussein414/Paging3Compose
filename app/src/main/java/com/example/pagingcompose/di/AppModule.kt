package com.example.pagingcompose.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.pagingcompose.data.client.BeerApi
import com.example.pagingcompose.data.db.BeerDataBase
import com.example.pagingcompose.data.model.db.BeerEntity
import com.example.pagingcompose.data.paging.BeerRemoteMediator
import com.example.pagingcompose.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton


@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesBeerDatabase(@ApplicationContext context: Context): BeerDataBase =
        Room.databaseBuilder(
            context = context, klass = BeerDataBase::class.java, name = "beers.db"
        ).build()

    @Provides
    @Singleton
    fun providesBeerApi(): BeerApi = Retrofit.Builder().baseUrl(Constants.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create()).build().create()

    @Provides
    @Singleton
    fun provideBeerPager(beerDataBase: BeerDataBase, beerApi: BeerApi): Pager<Int, BeerEntity>{
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = BeerRemoteMediator(beerDataBase, beerApi),
            pagingSourceFactory = {
                beerDataBase.getDao.pagingSource()
            }
        )
    }

}