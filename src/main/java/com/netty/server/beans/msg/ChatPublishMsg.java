package com.netty.server.beans.msg;

/**
 * @Author: gaofeng
 * @Date: 2018-07-19
 * @Description:
 */
public class ChatPublishMsg {

    private Integer pubMsgType; // 发布消息的级别 0：未使用 1：publish已发送 2：puback已送达 3：已读 在这里没有用mqtt的PubAck
    private String content;
    private String username;
    private boolean isSelf;
    private Long msgTimestamp;
    private Integer msgType;

    public Integer getPubMsgType() {
        return pubMsgType;
    }

    public void setPubMsgType(Integer pubMsgType) {
        this.pubMsgType = pubMsgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(boolean isSelf) {
        this.isSelf = isSelf;
    }

    public Long getMsgTimestamp() {
        return msgTimestamp;
    }

    public void setMsgTimestamp(Long msgTimestamp) {
        this.msgTimestamp = msgTimestamp;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    @Override
    public String toString() {
        return "ChatPublishMsg{" +
                "pubMsgType=" + pubMsgType +
                ", content='" + content + '\'' +
                ", username='" + username + '\'' +
                ", isSelf=" + isSelf +
                ", msgTimestamp=" + msgTimestamp +
                ", msgType=" + msgType +
                '}';
    }
}
