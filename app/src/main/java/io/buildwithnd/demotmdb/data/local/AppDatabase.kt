package io.buildwithnd.demotmdb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.buildwithnd.demotmdb.data.GenreConverters
import io.buildwithnd.demotmdb.model.Movie

@Database(entities = [Movie::class], version = 1)
@TypeConverters(GenreConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}