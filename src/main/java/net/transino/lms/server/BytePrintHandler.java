package net.transino.lms.server;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.ReferenceCountUtil;
import net.transino.core.Consts;
import net.transino.lms.modules.comm.cfg.FileField;
import net.transino.lms.modules.comm.cfg.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class BytePrintHandler extends ByteToMessageDecoder { // (1)
    private static final Logger log = LoggerFactory.getLogger(BytePrintHandler.class);
    private static final int INTEGER_LENGTH = 8;
    private static final int HEAD_LENGTH = 60;
    private static final int FILE_NAME_LENGTH = 30;
    private static final int FILE_BYTES_LENGTH = 4;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {


        log.info("---> 接收数据 start ...");
        int readLength = in.readableBytes();
        log.info("---> 接收数据 总长度 =" + readLength);
        if (readLength < INTEGER_LENGTH) {
            return;
        }
        int length = this.getInt(in);
        log.info("---> 接收数据 包头长度 =" + length);
        readLength = in.readableBytes();
        if (readLength < length) {
            in.resetReaderIndex();
            return;
        }
        in.readerIndex(INTEGER_LENGTH + length);

        byte end = in.readByte();
        boolean inFile = (end == 0x7f);
        log.info("---> 接收数据 是否有文件 =" + inFile);
        while (inFile) {
            readLength = in.readableBytes();
            if (readLength < FILE_NAME_LENGTH + FILE_BYTES_LENGTH) {
                in.resetReaderIndex();
                return;
            }
            in.readerIndex(INTEGER_LENGTH + length + FILE_NAME_LENGTH + 1);
            int lengthFile = this.getInt(in);
            log.info("---> 接收数据 文件长度 =" + inFile);
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


        log.info("---> 接收数据 end ...");



        in.readerIndex(0);

        ByteBuf otherByteBufRef = in.slice(0, in.readableBytes());

        otherByteBufRef.retain();

        in.readerIndex(in.readableBytes());

        ctx.writeAndFlush(otherByteBufRef).addListener(ChannelFutureListener.CLOSE);

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
//
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
//        // 当出现异常就关闭连接
////        cause.printStackTrace();
//        ctx.close();
//    }
}