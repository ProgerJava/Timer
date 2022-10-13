package com.example.timer.model

import android.content.ContentValues
import android.content.Context
import com.example.timer.DB.FeedReaderContract
import com.example.timer.DB.FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE
import com.example.timer.DB.FeedReaderContract.FeedEntry.TABLE_NAME
import com.example.timer.DB.FeedReaderDbHelper

class ModelTimer (private val context: Context) {

    private val dbHelper = FeedReaderDbHelper(context)

   fun addToDb (string: String) {
       val db = dbHelper.writableDatabase
       val values = ContentValues().apply {
           put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, string)
       }
       val newRowId = db?.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values)
   }

    fun readDb () : ArrayList <String> {
        val array = ArrayList <String>()
        val db = dbHelper.readableDatabase

        val projection = arrayOf(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE)

        val cursor = db.query(FeedReaderContract.FeedEntry.TABLE_NAME, projection, null, null, null, null, null)
        with(cursor) {
            while (moveToNext()) {
                val itemId = getString(getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE))
                array.add(itemId)
            }
        }
        cursor.close()

        return array
    }
    fun deleteElements (pos : String) {
        val db = dbHelper.writableDatabase
        val selection = "$COLUMN_NAME_TITLE LIKE ?"
        val selectionArgs = arrayOf(pos)
        val delCount: Int = db!!.delete(TABLE_NAME, selection, selectionArgs)
    }
}