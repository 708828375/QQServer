package com.digger.qqserver.service;

import java.util.HashMap;

/**
 * @Author: Digger
 * @Description: 该类用于管理和客户端通信的线程
 * @Date: create in 2021/9/14 20:03
 */
public class ManageServerConnectClientThread {
    //用来存储用户id和对应得线程
    private static HashMap<String,ServerConnectClientThread> mp = new HashMap<>();

    public static void addServerConnectClientThread(String userId,ServerConnectClientThread serverConnectClientThread){
        mp.put(userId,serverConnectClientThread);
    }

    //从hashMap中获取一个线程
    public static ServerConnectClientThread getServerConnectClientThread(String userId){
        return mp.get(userId);
    }

    //返回在线用户列表
    public static String getOnlineUser(){
        String s = "";
        //集合遍历，遍历HashMap的key
        for (String key : mp.keySet()){
            s += key + " ";
        }
        return s;
    }
}
