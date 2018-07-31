package com.netty.server.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: gaofeng
 * @Date: 2018-07-10
 * @Description:
 */
@Component
public class WebsocketChannelInitialiizer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private WebsocketHandler websocketHandler;

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline channelPipeline = channel.pipeline();

        //HttpServerCodec: 针对http协议进行编解码
        channelPipeline.addLast("httpServerCodec", new HttpServerCodec());
        //ChunkedWriteHandler分块写处理，文件过大会将内存撑爆
        //channelPipeline.addLast("chunkedWriteHandler", new ChunkedWriteHandler());
        /**
         * 作用是将一个Http的消息组装成一个完成的HttpRequest或者HttpResponse，那么具体的是什么
         * 取决于是请求还是响应, 该Handler必须放在HttpServerCodec后的后面
         */
        channelPipeline.addLast("httpObjectAggregator", new HttpObjectAggregator(8192));

        channelPipeline.addLast("httpRequestHandler", new HttpRequestHandler());

        //用于处理websocket, /ws为访问websocket时的uri
        //channelPipeline.addLast("webSocketServerProtocolHandler", new WebSocketServerProtocolHandler("/ws"));
        //channelPipeline.addLast("websocketHandler", new WebsocketHandler());
        //channelPipeline.addLast(new MqttServerHandler());

        // com.netty.server.endserver Over
        channelPipeline.addLast("WebSocketFrameToByteBufDecoder", new WebSocketFrameToByteBufDecoder());
        channelPipeline.addLast("ByteBufToWebSocketFrameEncoder", new ByteBufToWebSocketFrameEncoder());
        channelPipeline.addLast("MqttDecoder", new MqttDecoder());
        channelPipeline.addLast("MqttEncoder", MqttEncoder.INSTANCE);
        channelPipeline.addLast("WebsocketHandler", websocketHandler);
    }
}
