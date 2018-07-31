package com.netty.server.controller;

import com.netty.server.beans.dbentity.ChatMsgRecord;
import com.netty.server.mapper.ChatMsgRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: gaofeng
 * @Date: 2018-07-20
 * @Description:
 */
@RestController
public class HelloController {

    @Autowired
    private ChatMsgRecordMapper chatMsgRecordMapper;

    @RequestMapping(value = "/hello")
    public String hello(HttpServletRequest request) {

        ChatMsgRecord insert = new ChatMsgRecord();
        insert.setSpokerId(1L);
        insert.setSpokerName("gaofeng");
        insert.setReceiverId(2L);
        insert.setReceiverName("sanmu");
        insert.setCreateTime(System.currentTimeMillis());
        insert.setIsGroupChat(0);
        insert.setContent("hello");
        chatMsgRecordMapper.insertChatMsgRecord(insert);

        ChatMsgRecord chatMsgRecord = chatMsgRecordMapper.findChatMsgRecordBySpokerIdAndReceiverId(1L, 2L);
        if (chatMsgRecord == null) {
            System.out.println("null");
        }
        return "how are you";

    }
}
