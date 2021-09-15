package com.digger.qqserver.service;

import com.digger.qqcommon.Message;
import com.digger.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
                //判断客户端消息的内容
                if(message.getMessageType().equals(MessageType.MESSAGE_GET_ONLINE_FRIEND)){//客户端请求获取在线用户列表
                    System.out.println(message.getSender() + "要在线用户列表");
                    //初始化message
                    Message message1 = new Message();
                    message1.setMessageType(MessageType.MESSAGE_RET_ONLINE_FRIEND);
                    message1.setContent(ManageServerConnectClientThread.getOnlineUser());
                    message1.setGetter(message.getSender());
                    //返回给客户端
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(message1);
                }else{
                    System.out.println("其它类型的消息，暂时不做处理。。。。");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
