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
}
