package com.netty.server.service;

import com.netty.server.beans.dbentity.ChatMsgRecord;
import com.netty.server.mapper.ChatMsgRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.java.JavaLoggingSystem;
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

    /**
     * 修改聊天记录的状态，可以将其修改为2 已送达 3 已读状态
     *
     * @param msgTimestamp
     * @param status
     *
     * @return
     */
    public boolean changeChatRecordStatus(Long msgTimestamp, Integer status) {
        Integer updateRet = chatMsgRecordMapper.changeChatRecordStatus(msgTimestamp, status);
        return updateRet.equals(1);
    }

    /**
     * 根据唯一标识查找聊天记录，如果查找不到记录或者查找到的记录超过1条，则返回null
     *
     * @param msgTimestamp
     *
     * @return
     */
    public ChatMsgRecord getChatMsgRecordByUniIden(Long msgTimestamp) {
        List<ChatMsgRecord> chatMsgRecords =
                chatMsgRecordMapper.findChatMsgRecordByUniqueIdentifier(msgTimestamp);
        if (chatMsgRecords.size() != 1) {
            System.out.println("occur error when find chatMsgRecord msgTimestamp=" + msgTimestamp
                    + " list size=" + chatMsgRecords.size());
            return null;
        } else {
            return chatMsgRecords.get(0);
        }
    }
}
