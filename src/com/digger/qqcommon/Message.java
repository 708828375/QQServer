package com.digger.qqcommon;

import java.io.Serializable;

/**
 * @Description : 消息对象
 * @Author : 孙梦琼
 * @Date : 2021/9/12 21:48
 * @Version : 1.0
 **/
public class Message implements Serializable {
    private static final long SerialVersionUID = 1L;
    private String sender;//发送方
    private String getter;//接收方
    private String content;//消息内容
    private String sendTime;//发送的时间
    private String messageType;//消息的类型[在接口中定义消息类型]

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
