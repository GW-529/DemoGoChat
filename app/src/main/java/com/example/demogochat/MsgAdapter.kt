package com.example.demogochat

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MsgAdapter(val msgList: List<Msg>, val context: Context):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    inner class LeftViewHolder(view: View):
        RecyclerView.ViewHolder(view) {
            val friendFace:ImageView = view.findViewById(R.id.msg_friend_face)
            val contentLeft: TextView = view.findViewById(R.id.msg_content_left)
        }

    inner class RightViewHolder(view: View):
        RecyclerView.ViewHolder(view) {
            val myFace: ImageView = view.findViewById(R.id.msg_my_face)
            val contentRight: TextView = view.findViewById(R.id.msg_content_right)
        }
    // 只在多类别的adapter里使用
    override fun getItemViewType(position: Int): Int {
        val msg = msgList[position]
        return msg.type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == Msg.TYPE_RECEIVED) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.msg_text_left_item, parent, false)
            val leftViewHolder = LeftViewHolder(view)
            return leftViewHolder
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.msg_text_right_item, parent, false)
            val rightViewHolder = RightViewHolder(view)
            return rightViewHolder
        }
    }

    override fun getItemCount(): Int = msgList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val msg = msgList[position]
        when(holder) {
            is LeftViewHolder -> {
                holder.contentLeft.text = msg.content
            }
            is RightViewHolder -> {
                holder.contentRight.text = msg.content
            }
            else -> {

            }
        }
    }
}