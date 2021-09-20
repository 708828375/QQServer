package com.digger.qqserver.service;

import com.digger.qqcommon.Message;
import com.digger.qqcommon.MessageType;
import com.digger.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Digger
 * @Description: 服务器在9999端口监听，并保持通信
 * @Date: create in 2021/9/14 19:38
 */
public class QQServer {
    private ServerSocket ss = null;

    //使用ConcurrentHashMap，可以处理并发的集合，没有线程安全问题；线程同步处理，在多线程情况下线程安全
    //创建一个HashMap来存放合法用户
    private static ConcurrentHashMap<String,User> validUsers = new ConcurrentHashMap<>();

    //在静态代码块中初始化validUsers
    static {
        validUsers.put("100",new User("100","123456"));
        validUsers.put("200",new User("200","123456"));
        validUsers.put("300",new User("300","123456"));
        validUsers.put("至尊宝",new User("至尊宝","123456"));
        validUsers.put("紫霞仙子",new User("紫霞仙子","123456"));
        validUsers.put("赤脚大仙",new User("赤脚大仙","123456"));
    }

    //验证用户登录
    public boolean checkUserLogin(String userId,String password){
        if(validUsers.get(userId) != null && validUsers.get(userId).getPassword().equals(password)){
            return true;
        }else{
            return false;
        }
    }

    public QQServer() {
        //注意：端口可以写在配置文件
        try {
            System.out.println("服务端在9999端口监听。。。");
            //启动推送服务
            new Thread(new SendNewsToAllService()).start();
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
                if(checkUserLogin(u.getId(),u.getPassword())){//登录成功
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
