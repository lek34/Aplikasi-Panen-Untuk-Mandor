package com.example.gpapanen

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "ItemDatabase"
        private const val TABLE_NAME = "items"
        private const val KEY_ID = "id"
        private const val KEY_NAMA = "nama"
        private const val KEY_PEKERJAAN = "pekerjaan"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($KEY_ID INTEGER PRIMARY KEY, $KEY_NAMA TEXT, $KEY_PEKERJAAN TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addItem(item: Item): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_NAMA, item.nama)
            put(KEY_PEKERJAAN, item.pekerjaan)
        }
        val id = db.insert(TABLE_NAME, null, values)
        db.close()
        return id
    }

    @SuppressLint("Range")
    fun getAllItems(): MutableList<Item> {
        val itemList = mutableListOf<Item>()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                val nama = cursor.getString(cursor.getColumnIndex(KEY_NAMA))
                val pekerjaan = cursor.getString(cursor.getColumnIndex(KEY_PEKERJAAN))
                itemList.add(Item(id, nama, pekerjaan))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return itemList
    }

    fun updateItem(item: Item): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_NAMA, item.nama)
            put(KEY_PEKERJAAN, item.pekerjaan)
        }
        val rowsAffected = db.update(TABLE_NAME, values, "$KEY_ID=?", arrayOf(item.id.toString()))
        db.close()
        return rowsAffected
    }

    fun deleteItem(item: Item): Int {
        val db = this.writableDatabase
        val rowsAffected = db.delete(TABLE_NAME, "$KEY_ID=?", arrayOf(item.id.toString()))
        db.close()
        return rowsAffected
    }
}

