package net.transino.lms.modules.comm.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import net.transino.core.util.DateUtil;
import net.transino.lms.DemoSendMessage;
import net.transino.lms.modules.comm.*;
import net.transino.lms.modules.comm.cfg.DefaultBody;
import net.transino.lms.modules.comm.cfg.Message;
import net.transino.lms.modules.comm.client.AbstractMessageAdapter;
import net.transino.lms.modules.comm.client.DefaultMessageAdapter;
import net.transino.lms.server.BytePrintHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lee
 * @since 5.0
 */
@Slf4j
public class SocketServer {

    private String port;
    private String groupParent;
    private String groupChild;

    public void start(){
        EventLoopGroup parentGroup = new NioEventLoopGroup(Integer.parseInt(this.groupParent));
        EventLoopGroup childGroup = new NioEventLoopGroup(Integer.parseInt(this.groupChild));

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new ChannelInitializer<SocketChannel>(){
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
//                            pipeline.addLast(new BytePrintHandler());
                            pipeline.addLast(new MessageEncoderHandler());
                            pipeline.addLast(new FileEncoderHandler());
                            pipeline.addLast(new ReadTimeoutHandler(60));
                            pipeline.addLast(new SocketServerHandler());
                        }
                    });
            log.info("服务端启动 Start ...");
            ChannelFuture channelFuture = serverBootstrap.bind(Integer.parseInt(this.port)).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setGroupParent(String groupParent) {
        this.groupParent = groupParent;
    }

    public void setGroupChild(String groupChild) {
        this.groupChild = groupChild;
    }

    public void init() {
        this.start();
    }
}