package org.redNbt.io;

import org.redNbt.util.TagException;
import org.redNbt.util.TagType;
import org.redNbt.util.TagVisitor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Bug<3050429487@qq.com>
 */
public class NbtOutputStream extends OutputStream {

    DataOutputStream dataOutputStream;

    public NbtOutputStream(OutputStream inputStream) {
        this.dataOutputStream = new DataOutputStream(inputStream);
    }

    @Override
    public void write(int b) throws IOException {
        dataOutputStream.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        dataOutputStream.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        dataOutputStream.write(b);
    }

    @Override
    public void flush() throws IOException {
        dataOutputStream.flush();
    }

    @Override
    public void close() throws IOException {
        dataOutputStream.close();
    }

    public TagVisitor getTagWriter() {
        return tagWriter;
    }

    private final TagWriter tagWriter = new TagWriter();

    private class TagWriter implements TagVisitor {

        private int layer = 0;

        @Override
        public void visitByteTag(String name, byte value) throws IOException {
            dataOutputStream.writeByte(1);
            dataOutputStream.writeUTF(name);
            dataOutputStream.writeByte(value);
        }

        @Override
        public void visitShortTag(String name, short value) throws IOException {
            dataOutputStream.writeByte(2);
            dataOutputStream.writeUTF(name);
            dataOutputStream.writeShort(value);
        }

        @Override
        public void visitIntTag(String name, int value) throws IOException {
            dataOutputStream.writeByte(3);
            dataOutputStream.writeUTF(name);
            dataOutputStream.writeInt(value);
        }

        @Override
        public void visitLongTag(String name, long value) throws IOException {
            dataOutputStream.writeByte(4);
            dataOutputStream.writeUTF(name);
            dataOutputStream.writeLong(value);
        }

        @Override
        public void visitFloatTag(String name, float value) throws IOException {
            dataOutputStream.writeByte(5);
            dataOutputStream.writeUTF(name);
            dataOutputStream.writeFloat(value);
        }

        @Override
        public void visitDoubleTag(String name, double value) throws IOException {
            dataOutputStream.writeByte(6);
            dataOutputStream.writeUTF(name);
            dataOutputStream.writeDouble(value);
        }

        @Override
        public void visitByteArrayTag(String name, byte[] value) throws IOException {
            dataOutputStream.writeByte(7);
            dataOutputStream.writeUTF(name);
            dataOutputStream.writeInt(value.length);
            dataOutputStream.write(value);
        }

        @Override
        public void visitStringTag(String name, String value) throws IOException {
            dataOutputStream.writeByte(8);
            dataOutputStream.writeUTF(name);
            dataOutputStream.writeUTF(value);
        }

        @Override
        public TagVisitor visitListTag(String name, TagType tagType, int size) throws IOException {
            dataOutputStream.writeByte(9);
            dataOutputStream.writeUTF(name);
            dataOutputStream.writeByte(tagType.id);
            dataOutputStream.writeInt(size);
            return this;
        }

        @Override
        public TagVisitor visitCompoundTag(String name) throws IOException {
            dataOutputStream.writeByte(10);
            dataOutputStream.writeUTF(name);
            layer++;
            return this;
        }

        @Override
        public void visitEndTag() throws IOException, TagException {
            if(layer != 1)
                dataOutputStream.writeByte(0);
            else if(layer >= 0)
                throw new TagException("错误的End Tag");
            layer--;
        }

        @Override
        public void visitIntArrayTag(String name, int[] value) throws IOException {
            dataOutputStream.writeByte(11);
            dataOutputStream.writeUTF(name);
            dataOutputStream.writeInt(value.length);
            for (int i = 0; i < value.length; i++) {
                dataOutputStream.writeInt(value[i]);
            }
        }
    }
}
