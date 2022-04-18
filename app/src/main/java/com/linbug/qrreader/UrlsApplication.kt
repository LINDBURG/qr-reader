package com.linbug.qrreader

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class UrlsApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    private val database by lazy { UrlRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { UrlRepository(database.urlDao()) }
}