package com.example.pagingcompose.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.pagingcompose.data.model.db.BeerEntity

@Dao
interface BeerDao {
    @Upsert
    suspend fun upsertAll(beers: List<BeerEntity>)

    @Query("SELECT * FROM Beers")
    fun pagingSource(): PagingSource<Int, BeerEntity>


    @Query("DELETE FROM Beers")
    suspend fun clearAll()
}