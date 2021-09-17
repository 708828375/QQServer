package com.digger.qqcommon;

/**
 * @Description : 消息的类型
 * @Author : 孙梦琼
 * @Date : 2021/9/12 22:02
 * @Version : 1.0
 **/
public interface MessageType {
    String MESSAGE_LOGIN_SUCCEED = "1";//表示登录成功
    String MESSAGE_LOGIN_FAIL = "2";//表示登陆失败
    String MESSAGE_COMM_MES = "3"; //普通的信息对象
    String MESSAGE_GET_ONLINE_FRIEND = "4"; //要求返回在线好友列表
    String MESSAGE_RET_ONLINE_FRIEND = "5"; //返回在线用户列表
    String MESSAGE_CLIENT_EXIT = "6"; //客户端请求退出
    String MESSAGE_PRIVATE_CHAT = "7"; //用户私聊的消息
    String MESSAGE_GROUP_CHAT = "8"; //用户的群聊消息
}
