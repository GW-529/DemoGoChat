package com.example.demogochat

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class MsgActivity : AppCompatActivity() {

    //所有消息的存储列表
    val msgList = ArrayList<Msg>()
    lateinit var dbHelper:ChatDBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_msg)
        // 创建数据库(msg)
        dbHelper = ChatDBHelper(this,ChatDBHelper.DB_NAME,ChatDBHelper.DB_VERSION)
        val db = dbHelper.writableDatabase

        loadMsgList()

        val friendInfo: TextView = findViewById(R.id.msg_friend_info)
        // 接受数据解包
        val id = intent.getIntExtra("id",0)
        val name = intent.getStringExtra("name")
        // 数据显示
        friendInfo.text = "朋友ID:${id} 姓名: ${name}"

        //实现RecyclerView的聊天列表
        //1.找到RecyclerView
        val recyclerView:RecyclerView = findViewById(R.id.msg_recycler_view)
        //2.视图布局
        val layoutManager = LinearLayoutManager(this)
        //3.数据的转换
        //initMsgList()
        val adapter = MsgAdapter(msgList,this)
        //4.给RecyclerView赋予属性
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        //实现消息发送效果
        //找到输入框和按钮对象
        val inputText:EditText = findViewById(R.id.msg_input_text)
        val sendButton: Button = findViewById(R.id.msg_send_btn)

        // 加载未发送的数据
        val tmpFileName = "tmp_$id"
        inputText.setText(loadTxt(tmpFileName))

        // 保存用户信息至独立的xml文件
        val xmlFileName = "info_$id"
        saveXml(id,name.toString(),xmlFileName)

        // 用户信息into_朋友ID.xml


        //生成点击事件
        sendButton.setOnClickListener {
            // 保存文本文件
            //saveTxt(inputText.text.toString())
            //保存xml文件
            loadXml()
            //生成一条Msg消息
            //聊天内容存储至数据库
            val db = dbHelper.writableDatabase
            val chatContent = ContentValues().apply {
                put("friend_id",id)
                put("type",Msg.TYPE_SENT)
                put("content",inputText.text.toString())
            }
            db.insert("chat",null,chatContent)

            val msg = Msg(inputText.text.toString(), Msg.TYPE_SENT, id)
            msgList.add(msg)
            //通知adapter更新列表
            adapter.notifyItemInserted(msgList.size - 1)
            //滚动列表至最末端
            recyclerView.scrollToPosition(msgList.size - 1)
            //清空输入框
            inputText.setText("")
        }
    }

    @SuppressLint("Range")
    private fun loadMsgList() {
        val db = dbHelper.writableDatabase
        val friend_id = intent.getIntExtra("id",0)
        val cursor = db.rawQuery("select * from chat where friend_id=?", arrayOf("$friend_id"))
        if (cursor.moveToFirst()){
            do {
                val type = cursor.getInt(cursor.getColumnIndex("type"))
                val content = cursor.getString(cursor.getColumnIndex("content"))
                msgList.add(Msg(content,type,friend_id))
            }while (cursor.moveToNext())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val inputText:EditText = findViewById(R.id.msg_input_text)
        val id = intent.getIntExtra("id",0)
        val tmpFileName = "temp_$id"
        saveTxt(inputText.text.toString(),tmpFileName)

    }
    private fun initMsgList() {
        msgList.add(Msg("你好！！！",Msg.TYPE_RECEIVED,1))
        msgList.add(Msg("我在！！！",Msg.TYPE_SENT,2))
        msgList.add(Msg("干啥呢！！！",Msg.TYPE_RECEIVED,1))
    }
    /*
    文本文件保存
     */
    private  fun saveTxt(inputText:String, fileName:String = "data"){
        // 错误捕捉
        try{
            // 打开文件
            val outPutFile = openFileOutput(fileName, Context.MODE_PRIVATE)
            // 打开“笔”
            val writer = BufferedWriter(OutputStreamWriter(outPutFile))
            //writer.write("Hello Android")
            // 相当于python中的with open，会自动关闭回收
            // Hello Android 写入到data/data/com.example.demogochat里面的date里
            writer.use {
                it.write(inputText)
            }
        }catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(
                this,
                "文件写入失败!",
                Toast.LENGTH_SHORT
            ).show()

        }
    }
    /*
    读取文本文件
     */
    private fun loadTxt(fileName: String="data"):String{
        val content = StringBuilder()

        try{
            // 打开文件
            val inputFile = openFileInput(fileName)
            val reader = BufferedReader(InputStreamReader(inputFile))
            reader.use {
                reader.forEachLine {
                    content.append(it)
                }
            }

        }catch (e: Exception){
            e.printStackTrace()
        }
        return content.toString()
    }

    private fun saveXml(Id:Int,name:String,filename:String){
        try{
            val editor = getSharedPreferences(filename,Context.MODE_PRIVATE).edit()
            editor.apply{
                putString("name",name)
                putInt("id",Id)
            }
            editor.apply()
        }catch (e: Exception){
            e.printStackTrace()

        }
    }
    private fun loadXml(){
        val loader = getSharedPreferences("data",Context.MODE_PRIVATE)
        val  name = loader.getString("name","无名氏")
        val age = loader.getInt("age",0)
        val score =  loader.getFloat("score",0.0f)
        Log.d("MsgActivity","$name $age $score")
    }
}