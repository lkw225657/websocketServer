package net.transino.lms.modules.comm;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import net.transino.core.Consts;
import net.transino.core.util.Util;
import net.transino.lms.modules.comm.cfg.FileField;
import net.transino.lms.modules.comm.client.AbstractMessageAdapter;

/**
 * @author veggieg
 * @since 5.0
 */
@Slf4j
public class FileDecoderHandler extends SimpleChannelInboundHandler<FileField> {

    private AbstractMessageAdapter adapter;

    public FileDecoderHandler(AbstractMessageAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FileField field) throws Exception {
        String fileName = field.getName().trim();
        String fileText = new String(field.getByteArray(), Consts.ENCODING);
        log.info("--->接收文件名称: {}", fileName);
        log.info("--->接收文件内容: {}", fileText);

        this.adapter.getOutput().getFiles().put(fileName, Util.splitToList(fileText, "\n"));
    }
}
