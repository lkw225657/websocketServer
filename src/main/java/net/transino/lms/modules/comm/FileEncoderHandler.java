package net.transino.lms.modules.comm;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import net.transino.core.Consts;
import net.transino.lms.modules.comm.cfg.FileField;

/**
 * @author veggieg
 * @since 5.0
 */
@Slf4j
public class FileEncoderHandler extends MessageToByteEncoder<FileField> {

    @Override
    protected void encode(ChannelHandlerContext ctx, FileField field, ByteBuf out) throws Exception {
        log.info("---> 发送文件：" + field.getName() + " start ...");
        out.writeByte(0x7f);
        // 文件名
        byte[] bytes = String.format("%-30s", field.getName()).getBytes(Consts.ENCODING);
        out.writeBytes(bytes);
        // 文件长度
        int fileLength = field.getBytes().readableBytes();
        bytes = String.format("%08d", fileLength).getBytes(Consts.ENCODING);
        out.writeBytes(bytes);
        bytes = new byte[fileLength];
        field.getBytes().readBytes(bytes);
        out.writeBytes(bytes);

        log.info("---> 发送文件：" + field.getName() + " end ...");
        if (field.isEof()) {
            log.info("---> 所有发送文件 end ...");
            out.writeByte(0xff);
        }
    }
}
