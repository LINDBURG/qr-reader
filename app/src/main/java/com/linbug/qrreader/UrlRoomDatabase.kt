package com.linbug.qrreader

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Url::class], version = 1, exportSchema = false)
public abstract class UrlRoomDatabase : RoomDatabase() {

    abstract fun urlDao(): UrlDao

    private class UrlDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.urlDao())
                }
            }
        }

        suspend fun populateDatabase(urlDao: UrlDao) {
            // Delete all content here.
            urlDao.deleteAll()

        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: UrlRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): UrlRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UrlRoomDatabase::class.java,
                    "url_database"
                )
                    .addCallback(UrlDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}