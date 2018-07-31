package com.netty.server.beans.mqtt;


import io.netty.handler.codec.mqtt.*;

/**
 * @Author: gaofeng
 * @Date: 2018-07-17
 * @Description:
 */
public class MqttPingResp {

    public static MqttMessage mqttPingRespMsg() {
        MqttFixedHeader mqttPingFixedHeader = new MqttFixedHeader(MqttMessageType.PINGRESP, false, MqttQoS
                .AT_MOST_ONCE, false, 0);
        MqttMessage mqttPingRespMessage = MqttMessageFactory.newMessage(mqttPingFixedHeader, null, null);
        return mqttPingRespMessage;
    }
}
