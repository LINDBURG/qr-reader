package com.linbug.qrreader

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UrlDao {
    @Query("SELECT * FROM url_table ORDER BY url ASC")
    fun getAlphabetizedUrls(): Flow<List<Url>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(url: Url)

    @Query("DELETE FROM url_table")
    suspend fun deleteAll()
}