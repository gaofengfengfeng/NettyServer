package com.netty.server.endserver;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttEncoder;

/**
 * @Author: gaofeng
 * @Date: 2018-06-01
 * @Description: 用来增加多个的处理类到ChannelPipeline上:包括编码,解码,SimpleChatServerHandler
 */
public class SimpleChatServerInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * 初始化channel
     *
     * @param ch
     *
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        // pipeline.addLast("decoder", new StringDecoder());
        // pipeline.addLast("encoder", new StringEncoder());
        // pipeline.addLast("com.netty.server.websocket", new SimpleChatServerHandler());

        // 支持mqtt协议
        //pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast(new MqttDecoder(65355));
        pipeline.addLast(MqttEncoder.INSTANCE);
        pipeline.addLast("com/netty/server/websocket", new MqttServerHandler());
        System.out.println("SimpleChatClient:" + ch.remoteAddress() + "连接上服务器");
    }
}
