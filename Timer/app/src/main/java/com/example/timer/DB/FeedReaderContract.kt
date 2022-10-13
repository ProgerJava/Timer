package com.example.timer.DB

import android.provider.BaseColumns

object FeedReaderContract {

    object FeedEntry {
        const val TABLE_NAME = "timerTable"
        const val COLUMN_NAME_TITLE = "simple"

        const val SQL_CREATE_ENTRIES =
            "CREATE TABLE $TABLE_NAME (" + "${FeedEntry.COLUMN_NAME_TITLE} TEXT UNIQUE)"

        const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${FeedEntry.TABLE_NAME}"
    }
}