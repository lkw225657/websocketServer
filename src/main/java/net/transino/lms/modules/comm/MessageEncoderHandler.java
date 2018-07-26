package net.transino.lms.modules.comm;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import net.transino.core.Consts;
import net.transino.lms.modules.comm.cfg.Message;

/**
 * @author veggieg
 * @since 5.0
 */
@Slf4j
public class MessageEncoderHandler extends MessageToByteEncoder<Message> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Message bean, ByteBuf out) throws Exception {
        log.info("---> 发送Body数据 start ...");
        byte[] headBytes = bean.getHead().getBytes(Consts.ENCODING);
        byte[] bodyBytes = bean.getBody().getBytes(Consts.ENCODING);
        long length = 60 + bodyBytes.length;
        String len = String.format("%08d", length);
        // 包头和报文体总长度
        out.writeBytes(len.getBytes());
        // 包头
        out.writeBytes(headBytes);
        // 包体长度
        len = String.format("%04d", bodyBytes.length);
        out.writeBytes(len.getBytes());
        // 包体
        out.writeBytes(bodyBytes);

        if (bean.isEof()) {
            log.info("--->发送文件数：count = 0 ...");
            out.writeByte(0xff);
        }
    }
}
