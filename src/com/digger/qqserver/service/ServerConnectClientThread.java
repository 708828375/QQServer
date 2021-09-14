package com.digger.qqserver.service;

import com.digger.qqcommon.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * @Author: Digger
 * @Description: 该类的一个对象和某个客户端保持通信
 * @Date: create in 2021/9/14 19:53
 */
public class ServerConnectClientThread extends Thread{
    private Socket socket;
    private String userId;

    public ServerConnectClientThread(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }

    @Override
    public void run() {//这里线程处于运行状态，可以发送、接收消息
        while (true){
            try {
                System.out.println("服务端和客户端"+ userId + "保持通信，读取数据。。。");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message)ois.readObject();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
