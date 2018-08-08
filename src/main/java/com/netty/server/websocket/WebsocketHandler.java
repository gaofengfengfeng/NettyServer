package com.netty.server.websocket;
/**
 * @Author: gaofeng
 * @Date: 2018-07-10
 * @Description:
 */

import com.netty.server.beans.dbentity.ChatMsgRecord;
import com.netty.server.beans.msg.ChatPublishMsg;
import com.alibaba.fastjson.JSON;
import com.netty.server.service.ChatMsgRecordService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.mqtt.*;
import com.netty.server.beans.mqtt.MqttConnAck;
import com.netty.server.beans.mqtt.MqttPingResp;
import com.netty.server.beans.mqtt.MqttPublish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.java.JavaLoggingSystem;
import org.springframework.stereotype.Component;
import io.netty.channel.ChannelHandler.Sharable;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Sharable
public class WebsocketHandler extends SimpleChannelInboundHandler<MqttMessage> {

    private static ConcurrentHashMap<String, Channel> userChannel = new ConcurrentHashMap<String, Channel>();
    private static ConcurrentHashMap<Channel, String> channelUser = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, List<Channel>> topicChannel = new ConcurrentHashMap<>();

    @Autowired
    private ChatMsgRecordService chatMsgRecordService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MqttMessage mqttMessage) throws Exception {

        System.out.println(mqttMessage.fixedHeader().messageType() + " time=" + System.currentTimeMillis());
        switch (mqttMessage.fixedHeader().messageType()) {
            case CONNECT:
                processConnectMsg(ctx, mqttMessage);
                return;
            case SUBSCRIBE:
                processSubscribeMsg(ctx, mqttMessage);
                return;
            case PUBLISH:
                processPublishMsg(ctx, mqttMessage);
                return;
            case PINGREQ:
                proecssPingreqMsg(ctx, mqttMessage);
                return;
            case PUBACK:
                return;
            case PUBREC:
            case PUBREL:
            case PUBCOMP:
            case UNSUBACK:
                return;
            case PINGRESP:
                return;
            case DISCONNECT:
                return;
            default:
                return;


        }
    }

    /**
     * 处理连接消息
     *
     * @param ctx
     */
    private void processConnectMsg(ChannelHandlerContext ctx, MqttMessage mqttMessage) {
        MqttConnectMessage mqttConnectMessage = (MqttConnectMessage) mqttMessage;
        MqttConnectPayload mqttConnectPayload = mqttConnectMessage.payload();

        System.out.println("doConnectMessage: " + mqttConnectPayload.userName());
        userChannel.put(mqttConnectPayload.userName(), ctx.channel());
        channelUser.put(ctx.channel(), mqttConnectPayload.userName());
        ctx.channel().writeAndFlush(MqttConnAck.getMqttConnAckMessage());
    }

    /**
     * 处理订阅消息
     *
     * @param ctx
     * @param mqttMessage
     */
    private void processSubscribeMsg(ChannelHandlerContext ctx, MqttMessage mqttMessage) {
        MqttSubscribeMessage mqttSubscribeMessage = (MqttSubscribeMessage) mqttMessage;
        MqttTopicSubscription mqttTopicSubscription = mqttSubscribeMessage.payload().topicSubscriptions().get(0);
        System.out.println("doSubscribeMessage topic=" + mqttTopicSubscription.topicName());
        List<Channel> channels = topicChannel.get(mqttTopicSubscription.topicName());
        if (channels == null) {
            channels = new ArrayList<Channel>();
        }
        channels.add(ctx.channel());
        topicChannel.put(mqttTopicSubscription.topicName(), channels);

        processHistoryMsg(ctx, channelUser.get(ctx.channel()), mqttTopicSubscription.topicName());

        System.out.println("processSubscribeMsg success");

    }

    /**
     * 处理消息发布
     * 点对点聊天：singleChat/B/A A给B发消息
     * 群聊：groupChat/C/A A给群聊C发消息
     *
     * @param mqttMessage
     */
    private void processPublishMsg(ChannelHandlerContext ctx, MqttMessage mqttMessage) {
        MqttPublishMessage mqttPublishMessage = (MqttPublishMessage) mqttMessage;
        String topic = mqttPublishMessage.variableHeader().topicName();
        Integer msgId = mqttPublishMessage.variableHeader().packetId();
        System.out.println("doPublishMessage topic=" + topic);
        Charset utf8 = Charset.forName("UTF-8");
        String msg = mqttPublishMessage.payload().toString(utf8);
        System.out.println(msg);

        Long msgTimestamp = System.currentTimeMillis();
        // 入库
        ChatMsgRecord chatMsgRecord = new ChatMsgRecord();
        chatMsgRecord.setSpokerId(1L);
        chatMsgRecord.setSpokerName(channelUser.get(ctx.channel()));
        chatMsgRecord.setReceiverId(2L);
        chatMsgRecord.setReceiverName(getReceiverName(topic));
        chatMsgRecord.setIsGroupChat(0);
        chatMsgRecord.setContent(msg);
        chatMsgRecord.setMsgTimestamp(msgTimestamp);
        chatMsgRecord.setTopic(getComplianceTopic(topic));
        chatMsgRecord.setStatus(1);
        chatMsgRecord.setMsgType(1);
        try {
            chatMsgRecordService.insertChatMsgRecord(chatMsgRecord);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Channel> channels = topicChannel.get(getComplianceTopic(topic));
        if (channels != null) {
            for (Channel channel : channels) {
                // 构造需要发送给接收端的对象，并转化成json字符串
                ChatPublishMsg ChatPublishMsg = new ChatPublishMsg();
                ChatPublishMsg.setContent(msg);
                ChatPublishMsg.setUsername(channelUser.get(ctx.channel()));
                ChatPublishMsg.setIsSelf(ctx.channel().equals(channel));
                ChatPublishMsg.setMsgTimestamp(msgTimestamp);
                ChatPublishMsg.setMsgType(1);
                String jsonStr = JSON.toJSONString(ChatPublishMsg);

                // 将发送的消息push给订阅者
                channel.writeAndFlush(new MqttPublish().mqttPubish(topic, msgId, jsonStr));
            }
        }
    }

    /**
     * publish消息时得到合规的topic
     *
     * @param topic
     *
     * @return
     */
    private String getComplianceTopic(String topic) {
        String newTopic = topic.substring(0, topic.lastIndexOf("/")) + "/+";
        System.out.println("newTopic=" + newTopic);
        return newTopic;
    }

    /**
     * publish消息时，获得接受人的名字
     *
     * @param topic
     *
     * @return
     */
    public String getReceiverName(String topic) {
        String[] parsedTopic = topic.split("/");
        String receiverName = parsedTopic[parsedTopic.length - 2];
        return receiverName;
    }

    /**
     * 处理将历史消息发送给新订阅者
     *
     * @param ctx
     * @param receiverName
     * @param topic
     *
     * @return
     */
    public boolean processHistoryMsg(ChannelHandlerContext ctx, String receiverName, String topic) {
        System.out.println("processHistoryMsg receiverName=" + receiverName + " topic=" + topic);
        List<ChatMsgRecord> chatMsgRecords = chatMsgRecordService.getHistoryMsg(receiverName, topic);
        if (chatMsgRecords.size() != 0) {
            // 构造需要发送给接收端的对象，并转化成json字符串
            ChatPublishMsg ChatPublishMsg = new ChatPublishMsg();
            ChatPublishMsg.setContent(chatMsgRecords.get(0).getContent());
            ChatPublishMsg.setUsername(chatMsgRecords.get(0).getSpokerName());
            ChatPublishMsg.setIsSelf(false);
            ChatPublishMsg.setMsgTimestamp(chatMsgRecords.get(0).getMsgTimestamp());
            ChatPublishMsg.setMsgType(1);
            String jsonStr = JSON.toJSONString(ChatPublishMsg);
            ctx.channel().writeAndFlush(new MqttPublish().mqttPubish(topic, 0, jsonStr));
        }
        return true;
    }

    /**
     * 处理ping请求消息
     *
     * @param ctx
     * @param mqttMessage
     */
    private void proecssPingreqMsg(ChannelHandlerContext ctx, MqttMessage mqttMessage) {
        ctx.channel().writeAndFlush(MqttPingResp.mqttPingRespMsg());
    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ChannelId" + ctx.channel().id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }
}
