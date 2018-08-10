package com.netty.server.mapper;

import com.netty.server.beans.dbentity.ChatMsgRecord;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: gaofeng
 * @Date: 2018-07-20
 * @Description:
 */
@Mapper
@Repository
public interface ChatMsgRecordMapper {

    @Select("select * from chatMsgRecord where spokerId=#{spokerId} and receiverId=#{spokerId}")
    ChatMsgRecord findChatMsgRecordBySpokerIdAndReceiverId(@Param("spokerId") Long spokerId, @Param("receiverId")
            Long receiverId);

    @Insert("insert into chatMsgRecord(spokerId, spokerName, receiverId, receiverName, createTime, isGroupChat, " +
            "content, topic, msgTimestamp, msgType, status) values(#{spokerId}, #{spokerName}, #{receiverId}, " +
            "#{receiverName}, #{createTime}, #{isGroupChat}, #{content}, #{topic}, #{msgTimestamp}, #{msgType}, " +
            "#{status})")
    Integer insertChatMsgRecord(ChatMsgRecord chatMsgRecord);

    @Select("select * from chatMsgRecord where receiverName=#{receiverName} and topic=#{topic} and status!=3")
    List<ChatMsgRecord> getHistoryMsg(@Param("receiverName") String receiverName, @Param("topic") String topic);

    @Update("update chatMsgRecord set status=#{status} where msgTimestamp=#{msgTimestamp}")
    Integer changeChatRecordStatus(@Param("msgTimestamp") Long msgTimestamp, @Param("status") Integer status);

    @Select("select * from chatMsgRecord where msgTimestamp=#{msgTimestamp}")
    List<ChatMsgRecord> findChatMsgRecordByUniqueIdentifier(@Param("msgTimestamp") Long msgTimestamp);
}
