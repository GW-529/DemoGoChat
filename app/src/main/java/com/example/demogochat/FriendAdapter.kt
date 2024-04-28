package com.example.demogochat

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlin.io.path.fileVisitor

class FriendAdapter(val friendList: List<Friend>, val context: Context):
    RecyclerView.Adapter<FriendAdapter.ViewHolder>() {
       inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
           val friendName: TextView =
               view.findViewById(R.id.friend_name)
           val friendFace: ImageView =
               view.findViewById(R.id.friend_face)
       }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.friend_item, parent, false)
        val holder = ViewHolder(view)
//        holder.friendFace.setOnClickListener {
//            // 获取点击到的朋友的位置信息
//            val position = holder.adapterPosition
//            val friend = friendList[position]
//            Toast.makeText(
//                parent.context,
//                "${friend.id} ${position} ${friend.name}",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//
//        holder.friendName.setOnClickListener {
//            // 获取点击到的朋友的位置信息
//            val position = holder.adapterPosition
//            val friend = friendList[position]
//            Toast.makeText(
//                parent.context,
//                "你点到名字了！！！${friend.id} ${position} ${friend.name}",
//                Toast.LENGTH_SHORT
//            ).show()
//        }

        holder.itemView.setOnClickListener {
            // 获取点击到的朋友的位置信息
            val position = holder.adapterPosition
            val friend = friendList[position]

            // 跳转
            // val intent = Intent(context, MsgActivity::class.java)
            // val intent = Intent("隐式跳转地址，需要在Manifest里定义")

            val intent = Intent(context, MsgActivity::class.java)
            intent.putExtra("id", friend.id)
            intent.putExtra("name", friend.name)
            context.startActivity(intent)


            Toast.makeText(
                parent.context,
                "开始聊天吧${friend.id} ${position} ${friend.name}",
                Toast.LENGTH_SHORT
            ).show()
        }


        return holder
    }

    override fun getItemCount(): Int {
        return friendList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val friend = friendList[position]
        holder.friendName.text = friend.name
        holder.friendFace.setImageResource(friend.faceId)
    }


}