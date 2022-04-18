package com.linbug.qrreader

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "url_table")
data class Url(@PrimaryKey @ColumnInfo(name = "url")val url: String)