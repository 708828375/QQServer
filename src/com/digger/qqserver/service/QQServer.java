package com.digger.qqserver.service;

import com.digger.qqcommon.Message;
import com.digger.qqcommon.MessageType;
import com.digger.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: Digger
 * @Description: 服务器在9999端口监听，并保持通信
 * @Date: create in 2021/9/14 19:38
 */
public class QQServer {
    private ServerSocket ss = null;

    public QQServer() {
        //注意：端口可以写在配置文件
        try {
            System.out.println("服务端在9999端口监听。。。");
            ss = new ServerSocket(9999);

            while(true){//当和某个客户端建立连接后，继续保持监听
                Socket socket = ss.accept();
                //得到socket关联的对象输入流
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                User u = (User)ois.readObject(); //读取客户端发送的User对象

                //得到socket关联的输出流
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                //创建一个Message对象，回复给客户端
                Message message = new Message();
                if(u.getId().equals("100") && u.getPassword().equals("123")){//登录成功
                    message.setMessageType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    //将message对象回复给客户端
                    oos.writeObject(message);
                    //创建一个线程和客户端保持通信，该线程需要持有socket对象
                    ServerConnectClientThread serverConnectClientThread = new ServerConnectClientThread(socket, u.getId());
                    //启动该线程
                    serverConnectClientThread.start();
                    //把该线程放入集合中进行管理
                    ManageServerConnectClientThread.addServerConnectClientThread(u.getId(),serverConnectClientThread);
                }else{//登录失败
                    System.out.println("用户id="+u.getId() + "密码=" + u.getPassword() + "登录失败");
                    message.setMessageType(MessageType.MESSAGE_LOGIN_FAIL);
                    //将message对象回复给客户端
                    oos.writeObject(message);
                    socket.close();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //如果服务器退出了while，说明服务器端不再监听，关闭serversocket
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
