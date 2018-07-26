package net.transino.lms.modules.comm.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import net.transino.core.Consts;
import net.transino.core.util.Util;
import net.transino.lms.modules.comm.DefaultDecoderHandler;
import net.transino.lms.modules.comm.cfg.FileField;
import net.transino.lms.modules.comm.cfg.Message;
import net.transino.lms.modules.comm.client.AbstractMessageAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lee
 * @since 5.0
 */
@Slf4j
public class SocketServerHandler extends DefaultDecoderHandler {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {
        super.decode(ctx, in, list);
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> 客户端连接开始");
        Message message = (Message) list.remove(0);
        Map<String,List<?>> files = new HashMap<>(list.size());
        for (Object obj : list) {
            FileField field = (FileField)obj;
            String fileText = new String(field.getByteArray(), Consts.ENCODING);
            files.put(field.getName().trim(), Util.splitToList(fileText, "\n"));
        }
        String transCode = message.getHead().substring(0,4);
        AbstractMessageProcessor processor = AbstractMessageProcessor.newInstance(transCode);

        AbstractMessageAdapter adapter = processor.execServiceMethod(transCode, message.getBody(), files);

        this.ctxWrite(ctx, adapter.getInput()).addListener(ChannelFutureListener.CLOSE);
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> 客户端连接断开");
    }
}