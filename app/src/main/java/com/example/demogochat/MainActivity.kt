package com.example.demogochat

import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    // 朋友列表数据，创建一个列表
    val friendList = ArrayList<Friend>()
    //savedInstanceState是onCreate的父类，是一个Bundle对象
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) //调用父类的 onCreate() 方法，确保执行了父类的初始化逻辑。
        setContentView(R.layout.activity_main) //设置 Activity 的布局文件为 activity_main.xml。
        //创建数据库(Book)
        val dbHelper = BookDatabaseHelper(this,"Book db",1)
        val db = dbHelper.writableDatabase
        // 插入数据
        // #1
        db.execSQL("insert into Book values (null,'Tom',19.99,200,'Android')")
        // #2  更常用的方法
        val values = ContentValues().apply {
            put("name","Linux")
            put("author","jonh")
            put("pages",500)
            put("price",29.99)
        }
        db.insert("Book",null,values)



        iniFriendList() //自定义的函数调用，用于初始化好友列表的数据。
        // 使用recyclerview的方法
        // 在布局文件中找到一个名为 friend_list 的 RecyclerView 控件，并将其实例化为一个名为 friendListView 的变量。
        val friendListView: RecyclerView = findViewById(R.id.friend_list)
        // 创建了一个 LinearLayoutManager 对象，并将其赋值给名为 layoutManager 的变量。
        // LinearLayoutManager 是 RecyclerView 中的一种布局管理器，用于确定 RecyclerView 中的子项是如何排列的。
        // 在这里，它被创建并设置为垂直方向
        val layoutManager = LinearLayoutManager(this)
        // 创建了一个名为 adapter 的 FriendAdapter 对象，并将其实例化为一个变量。
        val adapter = FriendAdapter(friendList, this)
        // 将之前创建的 layoutManager（垂直方向的线性布局管理器）赋值给 friendListView 的 layoutManager 属性。
        friendListView.layoutManager = layoutManager
        // 将之前创建的适配器对象 adapter 赋值给 friendListView 的 adapter 属性。
        // 通过这行代码，我们将自定义的适配器与 RecyclerView 关联起来，从而实现了将数据绑定到 RecyclerView 中显示的功能。
        friendListView.adapter = adapter

    }
    // 私有函数 iniFriendList()，用于初始化好友列表的数据。
    private fun iniFriendList() {
        // 将一个名为 "张三" 的好友对象添加到 friendList 列表中。这个好友对象包括名字、一个代表苹果图片的资源 ID 和一个唯一的 ID。
        friendList.add(Friend("张三", R.drawable.apple_pic, 1))
        friendList.add(Friend("李四", R.drawable.banana_pic, 2))
        friendList.add(Friend("张三", R.drawable.apple_pic,3))
        friendList.add(Friend("李四", R.drawable.banana_pic, 4))
        friendList.add(Friend("张三", R.drawable.apple_pic, 5))
        friendList.add(Friend("李四", R.drawable.banana_pic,6 ))
        friendList.add(Friend("张三", R.drawable.apple_pic,7))
        friendList.add(Friend("李四", R.drawable.banana_pic,8))

    }
}