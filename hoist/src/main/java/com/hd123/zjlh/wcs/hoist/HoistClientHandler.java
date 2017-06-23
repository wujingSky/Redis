/**
 * 版权所有(C)，LHWMS项目组，2017，所有权利保留。
 * 
 * 项目名：	netty1
 * 文件名：	HoistClientHandler.java
 * 模块说明：	
 * 修改历史：
 * 2017年5月4日 - Jing - 创建。
 */
package com.hd123.zjlh.wcs.hoist;

import java.util.concurrent.locks.LockSupport;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author Jing
 *
 */
public class HoistClientHandler extends SimpleChannelInboundHandler<String>{
    private MCResponse reponseData = new MCResponse();
    private Thread thread;
    
    public HoistClientHandler() {
        super(false);
    }
    
    public void setThread(Thread thread) {
        this.thread = thread;
    }
    
    public MCResponse getResponse(){
        return reponseData;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        if(reponseData != null)
            reponseData.setReponseData(msg);
        LockSupport.unpark(this.thread);
    }
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("远程主机" + ctx.channel().remoteAddress() + " 处于活动......");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println("发生异常" + cause.toString());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("断开远程主机" + ctx.channel().remoteAddress() + " 连接......");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
            case READER_IDLE:
                handleReaderIdle(ctx);
                break;
            case WRITER_IDLE:
                handleWriterIdle(ctx);
                break;
            case ALL_IDLE:
                handleAllIdle(ctx,evt);
                break;
            default:
                break;
            }
        }
    }
    
    protected void handleReaderIdle(ChannelHandlerContext ctx) {
        System.err.println("---READER_IDLE---");
    }

    protected void handleWriterIdle(ChannelHandlerContext ctx) {
        System.err.println("---WRITER_IDLE---");
    }

    protected void handleAllIdle(ChannelHandlerContext ctx, Object evt) {
        System.err.println("---ALL_IDLE---");
        if(evt instanceof IdleStateEvent){
            System.out.println("准备发送心跳数据");
            ctx.writeAndFlush("XXX");
        }
    }
}
