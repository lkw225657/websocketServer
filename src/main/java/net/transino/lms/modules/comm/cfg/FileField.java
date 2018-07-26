package net.transino.lms.modules.comm.cfg;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author veggieg
 * @since 5.0
 */
public class FileField {
    private String name;
    private ByteBuf bytes;
    private boolean eof = false;

    public FileField(int initialCapacity) {
        this.bytes = Unpooled.buffer(initialCapacity);
    }

    public FileField(String name) {
        this.name = name;
        this.bytes = Unpooled.buffer(1024);
    }

    public boolean isEof() {
        return eof;
    }

    public void setEof(boolean eof) {
        this.eof = eof;
    }

    public ByteBuf getBytes() {
        return bytes;
    }

    public byte[] getByteArray(){
        if (!this.bytes.hasArray()){
            return null;
        }
        return this.bytes.array();
    }

    public void setBytes(byte[] bytes) {
        this.bytes.writeBytes(bytes);
    }

    public void setByte(byte b) {
        this.bytes.writeByte(b);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
