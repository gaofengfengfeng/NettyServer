package com.netty.server.beans.msg;

/**
 * @Author: gaofeng
 * @Date: 2018-07-19
 * @Description:
 */
public class ChatMsgRecord {

    private Long id;
    private Long spokerId;
    private String spokerName;
    private Long receiverId;
    private String receiverName;
    private Long createTime;
    private Integer isGroupChat;
    private String content;

    public ChatMsgRecord() {
        this.spokerId = 0L;
        this.spokerName = "";
        this.receiverId = 0L;
        this.receiverName = "";
        this.createTime = 0L;
        this.isGroupChat = 0;
        this.content = "";
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
}
