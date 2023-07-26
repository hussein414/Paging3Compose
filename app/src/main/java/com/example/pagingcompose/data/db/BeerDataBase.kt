package com.example.pagingcompose.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pagingcompose.data.model.db.BeerEntity

@Database(entities = [BeerEntity::class], version = 1)
abstract class BeerDataBase() : RoomDatabase() {
    abstract val getDao: BeerDao
}