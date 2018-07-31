package com.netty.server.beans.mqtt;

import io.netty.handler.codec.mqtt.*;

/**
 * @Author: gaofeng
 * @Date: 2018-07-16
 * @Description:
 */
public class MqttConnAck {

    public static MqttConnAckMessage getMqttConnAckMessage() {
        MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.CONNACK, false, MqttQoS.AT_MOST_ONCE,
                false, 10);

        MqttConnAckVariableHeader mqttConnAckVariableHeader = new MqttConnAckVariableHeader(MqttConnectReturnCode
                .CONNECTION_ACCEPTED, false);
        MqttConnAckMessage mqttConnAckMessage = new MqttConnAckMessage(mqttFixedHeader, mqttConnAckVariableHeader);
        return mqttConnAckMessage;
    }
}
