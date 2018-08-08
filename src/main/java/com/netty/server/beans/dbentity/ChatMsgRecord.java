package com.netty.server.beans.dbentity;

/**
 * @Author: gaofeng
 * @Date: 2018-07-20
 * @Description:
 */
public class ChatMsgRecord {
    private Long id;
    private Long spokerId;
    private String spokerName;
    private Long receiverId;
    private String receiverName;
    private Long createTime;
    private Long updateTime;
    private Integer isGroupChat;
    private String content;
    private String topic;
    private Long msgTimestamp; // 和topic组成标识一条消息的唯一标识，时间戳ms
    private Integer msgType; // 消息类型 0：未使用 1：文本 2：图片 3：语音 4：视频
    private Integer status; // 状态 0：未使用 1：已发送 2：已送达 3：已读

    public ChatMsgRecord() {
        this.spokerId = 0L;
        this.spokerName = "";
        this.receiverId = 0L;
        this.receiverName = "";
        this.createTime = System.currentTimeMillis();
        this.isGroupChat = 0;
        this.content = "";
        this.topic = "";
        this.msgTimestamp = 0L;
        this.msgType = 0;
        this.status = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSpokerId() {
        return spokerId;
    }

    public void setSpokerId(Long spokerId) {
        this.spokerId = spokerId;
    }

    public String getSpokerName() {
        return spokerName;
    }

    public void setSpokerName(String spokerName) {
        this.spokerName = spokerName;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsGroupChat() {
        return isGroupChat;
    }

    public void setIsGroupChat(Integer isGroupChat) {
        this.isGroupChat = isGroupChat;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
