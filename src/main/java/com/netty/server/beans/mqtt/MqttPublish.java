package com.netty.server.beans.mqtt;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.mqtt.*;

import java.nio.charset.Charset;

/**
 * @Author: gaofeng
 * @Date: 2018-06-26
 * @Description:
 */
public class MqttPublish {

    /**
     * 发送订阅消息
     *
     * @param topicName
     * @param msgId
     * @param msg
     *
     * @return
     */
    public MqttPublishMessage mqttPubish(String topicName, Integer msgId, String msg) {
        MqttFixedHeader fixedHeader = new MqttFixedHeader(MqttMessageType.PUBLISH, false, MqttQoS.AT_MOST_ONCE, false,
                10);

        MqttPublishVariableHeader variableHeader = new MqttPublishVariableHeader(topicName, msgId);

        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf payload = Unpooled.copiedBuffer(msg, utf8);//创建一个ByteBuf

        return new MqttPublishMessage(fixedHeader, variableHeader, payload);
    }
}
