package com.netty.server.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.util.List;

/**
 * @Author: liuwenbin
 * @Date: 2018-06-19
 * @Description:
 */
public class WebSocketFrameToByteBufDecoder extends MessageToMessageDecoder<WebSocketFrame> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, WebSocketFrame textWebSocketFrame,
                          List<Object> out) throws Exception {
        ByteBuf content = textWebSocketFrame.content();
        out.add(content);
    }
}
