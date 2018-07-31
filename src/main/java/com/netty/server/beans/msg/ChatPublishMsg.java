package com.netty.server.beans.msg;

/**
 * @Author: gaofeng
 * @Date: 2018-07-19
 * @Description:
 */
public class ChatPublishMsg {

    private String content;
    private String username;
    private boolean isSelf;

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
}