package com.digger.qqserver.service;

import com.digger.qqcommon.Message;
import com.digger.qqcommon.MessageType;
import com.digger.utils.Utility;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author: Digger
 * @Description: 一个专门负责推送消息的线程
 * @Date: create in 2021/9/20 15:56
 */
public class SendNewsToAllService implements Runnable{

    @Override
    public void run() {

        while (true){
            System.out.print("请输入服务端要推送的新闻/消息：[输入exit表示退出推送服务]");
            String news = Utility.readString(100);
            if("exit".equals(news)){
                break;
            }
            //构建一个群发消息
            Message message = new Message();
            message.setMessageType(MessageType.MESSAGE_GROUP_CHAT);
            message.setSender("服务器");
            message.setContent(news);
            message.setSendTime(new Date().toString());
            System.out.println("服务器推送消息给所有人说：" + news);
            //进行群发，遍历当前所有的通信线程
            HashMap<String, ServerConnectClientThread> mp = ManageServerConnectClientThread.getMp();
            Iterator<Map.Entry<String, ServerConnectClientThread>> iterator = mp.entrySet().iterator();
            while (iterator.hasNext()) {
                ServerConnectClientThread thread =  (ServerConnectClientThread)iterator.next().getValue();
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(thread.getSocket().getOutputStream());
                    oos.writeObject(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
