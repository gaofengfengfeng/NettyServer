package com.netty.server;

import com.netty.server.websocket.WebsocketChannelInitialiizer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import com.netty.server.endserver.SimpleChatServerInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @Author: gaofeng
 * @Date: 2018-06-01
 * @Description:
 */
@Service
public class SimpleChatServer {

    private static Integer port = 11012;

    @Autowired
    private WebsocketChannelInitialiizer websocketChannelInitialiizer;

    @PostConstruct
    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {

            // com.netty.server.endserver
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new SimpleChatServerInitializer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // com.netty.server.websocket
            ServerBootstrap webSocketServerBootstrap = new ServerBootstrap();
            webSocketServerBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(websocketChannelInitialiizer)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            System.out.println("com.netty.server.SimpleChatServer 启动了");

            ChannelFuture future;
            ChannelFuture websocketFuture;

            future = serverBootstrap.bind(port).sync();
            websocketFuture = webSocketServerBootstrap.bind(11011).sync();
            future.channel().closeFuture().sync();
            websocketFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            System.out.println("com.netty.server.SimpleChatServer 关闭了");
        }
    }
}
