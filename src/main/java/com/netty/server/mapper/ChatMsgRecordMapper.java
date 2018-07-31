package com.netty.server.mapper;

import com.netty.server.beans.dbentity.ChatMsgRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

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
            "content) values(#{spokerId}, #{spokerName}, #{receiverId}, #{receiverName}, #{createTime}, " +
            "#{isGroupChat}, #{content})")
    Integer insertChatMsgRecord(ChatMsgRecord chatMsgRecord);
}
