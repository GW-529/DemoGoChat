package com.example.demogochat

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ChatDBHelper(val context: Context, dbName:String, version:Int):
    SQLiteOpenHelper(context,dbName,null,version){
    private val createFriend = "create table Friend (" +
            "id integer primary key autoincrement," +
            "name text ," +
            "face_id integer )"


    private val createMsg = "create table chat(" +
            "id integer primary key autoincrement," +
            "friend_id integer," +
            "type integer," +
            "content text)"


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createFriend)
        db.execSQL(createMsg)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVer: Int, newVer: Int) {
        TODO("Not yet implemented")
    }
    companion object{
        const val DB_NAME = "Msg.db"
        const val DB_VERSION = 1
    }
}