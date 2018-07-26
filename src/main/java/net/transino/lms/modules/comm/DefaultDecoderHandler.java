package net.transino.lms.modules.comm;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import net.transino.core.Consts;
import net.transino.core.util.Collections3;
import net.transino.lms.modules.comm.cfg.FileField;
import net.transino.lms.modules.comm.cfg.Message;
import net.transino.lms.modules.comm.client.AbstractMessageAdapter;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author veggieg
 * @since 5.0
 */
@Slf4j
public class DefaultDecoderHandler extends ByteToMessageDecoder {
    private static final int INTEGER_LENGTH = 8;
    private static final int HEAD_LENGTH = 60;
    private static final int FILE_NAME_LENGTH = 30;
    private static final int FILE_BYTES_LENGTH = 4;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {
        log.info("---> 接收数据 start ...");
        int readLength = in.readableBytes();
        if (readLength < INTEGER_LENGTH) {
            return;
        }
        int length = this.getInt(in);
        readLength = in.readableBytes();
        if (readLength < length) {
            in.resetReaderIndex();
            return;
        }
        in.readerIndex(INTEGER_LENGTH + length);

        byte end = in.readByte();
        boolean inFile = (end == 0x7f);
        while (inFile) {
            readLength = in.readableBytes();
            if (readLength < FILE_NAME_LENGTH + FILE_BYTES_LENGTH) {
                in.resetReaderIndex();
                return;
            }
            in.readerIndex(INTEGER_LENGTH + length + FILE_NAME_LENGTH + 1);
            int lengthFile = this.getInt(in);
            readLength = in.readableBytes();
            if (readLength < lengthFile) {
                in.resetReaderIndex();
                return;
            }
            end = in.readByte();
            inFile = (end == 0x7f);
        }
        in.readerIndex(0);
        byte[] datas = new byte[in.readableBytes()];
        in.readBytes(datas);
        String out = new String(datas,"UTF-8");
        log.info(out);

        in.readerIndex(INTEGER_LENGTH);
        Message message = new Message();
        message.setHead(this.getString(in, HEAD_LENGTH));
        message.setBody(this.getString(in, length - HEAD_LENGTH));
        list.add(message);

        end = in.readByte();
        inFile = (end == 0x7f);
        while (inFile) {
            String fileName = this.getString(in, FILE_NAME_LENGTH);
            int fileLength = this.getInt(in);
            FileField field = new FileField(fileLength);
            field.setName(fileName);
            field.setBytes(this.getBytes(in, fileLength));
            list.add(field);
            end = in.readByte();
            inFile = (end == 0x7f);
        }
        log.info("---> 接收数据 end ...");
    }

    protected ChannelFuture ctxWrite(ChannelHandlerContext ctx, AbstractMessageAdapter.Packet input) {
        final ChannelFuture[] future = new ChannelFuture[1];
        Message bean = new Message();
        bean.setHead(input.getHead());
        bean.setBody(input.getBody());
        if (Collections3.isEmpty(input.getFiles())) {
            bean.setEof(true);
            future[0] = ctx.writeAndFlush(bean);
        } else {
            bean.setEof(false);
            ctx.write(bean);
            final int[] count = {0};
            input.getFiles().forEach((String k, List<String> v) -> {
                FileField f = new FileField(k);
                v.forEach((String str) -> {
                    f.setBytes(str.getBytes(Charset.forName(Consts.ENCODING)));
                });
                if (++count[0] == input.getFiles().size()) {
                    f.setEof(true);
                    future[0] = ctx.writeAndFlush(f);
                } else {
                    ctx.write(f);
                }
                f.getBytes().release();
            });
        }
        return future[0];
    }

    private String getString(ByteBuf byteBuf, int length) throws UnsupportedEncodingException {
        byte[] byteArray = new byte[length];
        byteBuf.readBytes(byteArray);
        return new String(byteArray, Consts.ENCODING);
    }

    private int getInt(ByteBuf byteBuf) throws UnsupportedEncodingException {
        byte[] byteArray = new byte[INTEGER_LENGTH];
        byteBuf.readBytes(byteArray);
        return Integer.parseInt(new String(byteArray));
    }

    private byte[] getBytes(ByteBuf byteBuf, int length) throws UnsupportedEncodingException {
        byte[] byteArray = new byte[length];
        byteBuf.readBytes(byteArray);
        return byteArray;
    }
}