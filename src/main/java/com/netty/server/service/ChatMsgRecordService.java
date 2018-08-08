package com.netty.server.service;

import com.netty.server.beans.dbentity.ChatMsgRecord;
import com.netty.server.mapper.ChatMsgRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: gaofeng
 * @Date: 2018-07-20
 * @Description:
 */
@Service
public class ChatMsgRecordService {

    private ChatMsgRecordMapper chatMsgRecordMapper;

    @Autowired
    public ChatMsgRecordService(ChatMsgRecordMapper chatMsgRecordMapper) {
        this.chatMsgRecordMapper = chatMsgRecordMapper;
    }

    public Integer insertChatMsgRecord(ChatMsgRecord chatMsgRecord) {
        return chatMsgRecordMapper.insertChatMsgRecord(chatMsgRecord);
    }

    /**
     * 获取指定接收人指定话题的历史未读(包括已发送和已送达)聊天记录
     *
     * @param receiverName
     * @param topic
     *
     * @return
     */
    public List<ChatMsgRecord> getHistoryMsg(String receiverName, String topic) {
        return chatMsgRecordMapper.getHistoryMsg(receiverName, topic);
    }
}
