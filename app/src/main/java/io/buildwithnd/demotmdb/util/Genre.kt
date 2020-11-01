package io.buildwithnd.demotmdb.util

/**
 * static map of genre id and genre name
 */
class Genre {
    companion object{

        private val genreMap = mapOf<Int, String>(
            28 to "Action",
            12 to "Adventure",
            16 to "Animation",
            35 to "Comedy",
            80 to "Crime",
            99 to "Documentary",
            18 to "Drama",
            10751 to "Family",
            14 to "Fantasy",
            36 to "History",
            27 to "Horror",
            10402 to "Music",
            9648 to "Mystery",
            10749 to "Romance",
            878 to "Science Fiction",
            10770 to "TV Movie",
            53 to "Thriller",
            10752 to "War",
            37 to "Western"
        )

        fun getGenre(ids: List<Int>?) : String? {
            ids?.let {
                val genreStrs = mutableListOf<String>()
                ids.forEach {
                    print("foreach $it")
                    genreStrs.add(getGenre(it))
                }
                return genreStrs.joinToString(separator = ", ")
            } ?: return  ""
        }

        private fun getGenre(id: Int?) : String {
            genreMap[id].let {
                return it ?: ""
            }
        }
    }
}