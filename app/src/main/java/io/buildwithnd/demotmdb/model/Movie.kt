package io.buildwithnd.demotmdb.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Movie(
    @PrimaryKey
    val id: Int,
    val title: String?,
    val overview: String?,
    val popularity: Double,
    val poster_path: String,
    val genre_ids: List<Int>
)