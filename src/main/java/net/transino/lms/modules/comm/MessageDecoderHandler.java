package net.transino.lms.modules.comm;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import net.transino.lms.modules.comm.cfg.Message;
import net.transino.lms.modules.comm.client.AbstractMessageAdapter;

/**
 * @author veggieg
 * @since 5.0
 */
@Slf4j
public class MessageDecoderHandler extends SimpleChannelInboundHandler<Message> {
    private AbstractMessageAdapter adapter;
    public MessageDecoderHandler(AbstractMessageAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message bean) throws Exception {
        log.info("--->接收 head:" + bean.getHead());
        log.info("--->接收 body:" + bean.getBody());
//        this.adapter.getOutput().setHead(bean.getHead());
//        this.adapter.getOutput().setBody(bean.getBody());
    }
}
