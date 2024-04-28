package com.example.demogochat

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class BookDatabaseHelper(val context: Context, dbName: String, version: Int): SQLiteOpenHelper(context,dbName,null,version){

    private val createBook = "create table book (" +
            "id integer primary key autoincrement," +
            "author text," +
            "price real," +
            "pages integer," +
            "name text)"



    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createBook)
        Log.d("BookDatabaseHelper","db book created!")
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

}