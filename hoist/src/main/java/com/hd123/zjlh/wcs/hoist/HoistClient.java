/**
 * 版权所有(C)，LHWMS项目组，2017，所有权利保留。
 * 
 * 项目名：	netty1
 * 文件名：	HoistClient.java
 * 模块说明：	
 * 修改历史：
 * 2017年5月4日 - Jing - 创建。
 */
package com.hd123.zjlh.wcs.hoist;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author Jing
 *
 */
public class HoistClient {
    private NioEventLoopGroup workGroup = new NioEventLoopGroup();
    private Channel channel;
    private Bootstrap bootStrap;
    private String host;
    private int port;
    private String responseData;
    private Thread thread = Thread.currentThread();
    private HoistClientHandler hoistHandler;

    private static volatile HoistClient instance;

    private HoistClient() {
    }

    private HoistClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static HoistClient getInstance(String host, int port) {
//        if (instance == null) {
//            synchronized (HoistClient.class) {
//                if (instance == null) {
//                    instance = new HoistClient(host, port);
//                }
//            }
//        }
        instance = new HoistClient(host, port);
        return instance;
    }

    public String sendData(String data) {
        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(data);
        }

        LockSupport.park(this.thread);
        return hoistHandler.getResponse().getReponseData();
    }

    public String getResponseData() {
        return responseData;
    }

    protected void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public void start() {
        if (channel != null && channel.isActive()) {
            return;
        }

        bootStrap = new Bootstrap();
        bootStrap.group(workGroup).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new IdleStateHandler(30, 30, 60));
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new StringDecoder());

                        hoistHandler = new HoistClientHandler();
                        hoistHandler.setThread(thread);
                        pipeline.addLast(hoistHandler);
                    }
                });

        doConnect();
    }

    public void doConnect() {
        if (channel != null && channel.isActive()) {
            return;
        }

        ChannelFuture future = bootStrap.connect(host, port);
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    channel = future.sync().channel();
                    System.out.println("连接成功!");
                } else {
                    System.out.println("连接失败，10s 后重试......");

                    future.sync().channel().eventLoop().schedule(new Runnable() {
                        public void run() {
                            doConnect();
                        }
                    }, 10, TimeUnit.SECONDS);
                }
            }
        });
    }

    public void close() {
        channel.close();
        workGroup.shutdownGracefully();
    }
}
