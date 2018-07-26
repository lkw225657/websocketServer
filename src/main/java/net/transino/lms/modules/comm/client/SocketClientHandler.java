package net.transino.lms.modules.comm.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import net.transino.core.Consts;
import net.transino.core.util.Collections3;
import net.transino.lms.modules.comm.DefaultDecoderHandler;
import net.transino.lms.modules.comm.cfg.FileField;
import net.transino.lms.modules.comm.cfg.Message;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author veggieg
 * @since 5.0
 */
@Slf4j
public class SocketClientHandler extends DefaultDecoderHandler {
    private AbstractMessageAdapter adapter;

    public SocketClientHandler(AbstractMessageAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> 服务器连接开始");
        AbstractMessageAdapter.Packet input = this.adapter.getInput();

        ctxWrite(ctx, input);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {
        super.decode(ctx, in, list);
    }
}
