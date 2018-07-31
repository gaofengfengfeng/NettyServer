package com.netty.server.endserver;

import com.netty.server.beans.mqtt.MqttPublish;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.mqtt.*;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: gaofeng
 * @Date: 2018-06-05
 * @Description:
 */
public class MqttServerHandler extends SimpleChannelInboundHandler<MqttMessage> {

    private static ConcurrentHashMap<String, Channel> userChannel = new ConcurrentHashMap<String, Channel>();
    private static ConcurrentHashMap<String, List<Channel>> topicChannel = new ConcurrentHashMap<String,
            List<Channel>>();

    /**
     * 服务端监听到客户端活动
     *
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MqttMessage mqttMessage) throws Exception {
        System.out.println("channelRead0");
        System.out.println(mqttMessage.fixedHeader().messageType());
        System.out.println("channelHandlerContext:" + channelHandlerContext.channel().id().asLongText());
        switch (mqttMessage.fixedHeader().messageType()) {
            case CONNECT:
                doConnectMessage(channelHandlerContext, mqttMessage);
                return;
            case SUBSCRIBE:
                doSubscribeMessage(channelHandlerContext, mqttMessage);
                return;
            case PUBLISH:
                doPublishMessage(mqttMessage);
                return;
            case PINGREQ:
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
     * @param channelHandlerContext
     * @param mqttMessage
     */
    public void doConnectMessage(ChannelHandlerContext channelHandlerContext, MqttMessage mqttMessage) {
        MqttConnectMessage mqttConnectMessage = (MqttConnectMessage) mqttMessage;
        MqttConnectPayload mqttConnectPayload = mqttConnectMessage.payload();

        System.out.println("doConnectMessage: " + mqttConnectPayload.userName());
        userChannel.put(mqttConnectPayload.userName(), channelHandlerContext.channel());
    }

    /**
     * 处理订阅消息
     *
     * @param channelHandlerContext
     * @param mqttMessage
     */
    public void doSubscribeMessage(ChannelHandlerContext channelHandlerContext, MqttMessage mqttMessage) {
        MqttSubscribeMessage mqttSubscribeMessage = (MqttSubscribeMessage) mqttMessage;
        MqttTopicSubscription mqttTopicSubscription = mqttSubscribeMessage.payload().topicSubscriptions().get(0);
        System.out.println("doSubscribeMessage topic=" + mqttTopicSubscription.topicName());
        List<Channel> channels = topicChannel.get(mqttTopicSubscription.topicName());
        if (channels == null) {
            channels = new ArrayList<Channel>();
        }
        channels.add(channelHandlerContext.channel());
        topicChannel.put(mqttTopicSubscription.topicName(), channels);
    }

    /**
     * 处理消息发布
     * 点对点聊天：singleChat/B/A A给B发消息
     * 群聊：groupChat/C/A A给群聊C发消息
     *
     * @param mqttMessage
     */
    public void doPublishMessage(MqttMessage mqttMessage) {
        MqttPublishMessage mqttPublishMessage = (MqttPublishMessage) mqttMessage;
        String topic = mqttPublishMessage.variableHeader().topicName();
        Integer msgId = mqttPublishMessage.variableHeader().messageId();
        System.out.println("doPublishMessage topic=" + topic);
        Charset utf8 = Charset.forName("UTF-8");
        String msg = mqttPublishMessage.payload().toString(utf8);
        List<Channel> channels = topicChannel.get(getComplianceTopic(topic));
        if (channels != null) {
            for (Channel channel : channels) {
                channel.writeAndFlush(new MqttPublish().mqttPubish(topic, msgId, msg));
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
    public String getComplianceTopic(String topic) {
        String newTopic = topic.substring(0, topic.lastIndexOf("/")) + "/+";
        return newTopic;
    }

}
