package com.example.pagingcompose.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.pagingcompose.data.client.BeerApi
import com.example.pagingcompose.data.db.BeerDataBase
import com.example.pagingcompose.data.mappers.toBeerEntity
import com.example.pagingcompose.data.model.db.BeerEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class BeerRemoteMediator(private val beerDataBase: BeerDataBase, private val beerApi: BeerApi) :
    RemoteMediator<Int, BeerEntity>() {
    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, BeerEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    1
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) 1 else (lastItem.id / state.config.pageSize) + 1
                }
            }
            val beers = beerApi.getBeers(page = loadKey, perPage = state.config.pageSize)
            beerDataBase.withTransaction {
                if (loadType == LoadType.REFRESH) beerDataBase.getDao.clearAll()
                val beerEntity = beers.map { it.toBeerEntity() }
                beerDataBase.getDao.upsertAll(beerEntity)
            }
            MediatorResult.Success(endOfPaginationReached = beers.isEmpty())
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}