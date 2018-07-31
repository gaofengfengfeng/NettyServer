package com.netty.server.service;

import com.netty.server.beans.dbentity.ChatMsgRecord;
import com.netty.server.mapper.ChatMsgRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
