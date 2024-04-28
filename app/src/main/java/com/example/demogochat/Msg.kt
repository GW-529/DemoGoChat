package com.example.demogochat
// 定义了一个名为 Msg 的数据类，用于表示消息对象。
// 通过 Msg.TYPE_RECEIVED 等方式，我们可以直接访问到这些消息类型的常量，而不需要创建 Msg 的实例。
class Msg(val content: String, val type: Int, val userId: Int)
{
    companion object{
        const val TYPE_RECEIVED = 0  // 表示接收到的文本消息类型。
        const val TYPE_SENT = 1      // 表示发送的文本消息类型。
        const val TYPE_IMG_RECEIVED = 2 // 表示接收到的图片消息类型。
        const val TYPE_IMG_SENT = 3     // 表示发送的图片消息类型。
    }
}