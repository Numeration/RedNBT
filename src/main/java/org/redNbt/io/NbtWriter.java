package org.redNbt.io;

import org.redNbt.util.TagException;
import org.redNbt.util.TagType;
import org.redNbt.util.TagVisitor;

import java.io.*;

/**
 * 将nbt数据写入到一个{@link OutputStream OutputStream}中.
 * 该写入器是{@link TagVisitor TagVisitor}接口的一个实现，因此所有的方法调
 * 用约定都与{@link TagVisitor TagVisitor}接口的一模一样
 *
 * @author Bug[3050429487@qq.com]
 *
 * @see TagWriter
 * @see TagVisitor
 */
public final class NbtWriter implements TagWriter, Closeable, Flushable {

    private final DataOutputStream dataOutputStream;

    /**
     * 使用输出流创建一个NbtWriter.
     *
     * @param outputStream
     *      此nbt写入器将会把nbt数据写入到此流
     */
    public NbtWriter(OutputStream outputStream) {
        this.dataOutputStream = new DataOutputStream(outputStream);
    }


    private final RootTagWriter rootTagWriter = new RootTagWriter();
    private final CompoundTagWriter compoundTagWriter = new CompoundTagWriter();
    private final ListTagWriter listTagWriter = new ListTagWriter();

    @Override
    public void visitBegin() {

    }

    @Override
    public void visitByteTag(String name, byte value) throws IOException, TagException {
        rootTagWriter.visitByteTag(name, value);
    }

    @Override
    public void visitShortTag(String name, short value) throws IOException, TagException {
        rootTagWriter.visitShortTag(name, value);
    }

    @Override
    public void visitIntTag(String name, int value) throws IOException, TagException {
        rootTagWriter.visitIntTag(name, value);
    }

    @Override
    public void visitLongTag(String name, long value) throws IOException, TagException {
        rootTagWriter.visitLongTag(name, value);
    }

    @Override
    public void visitFloatTag(String name, float value) throws IOException, TagException {
        rootTagWriter.visitFloatTag(name, value);
    }

    @Override
    public void visitDoubleTag(String name, double value) throws IOException, TagException {
        rootTagWriter.visitDoubleTag(name, value);
    }

    @Override
    public void visitByteArrayTag(String name, byte[] value) throws IOException, TagException {
        rootTagWriter.visitByteArrayTag(name, value);
    }

    @Override
    public void visitStringTag(String name, String value) throws IOException, TagException {
        rootTagWriter.visitStringTag(name, value);
    }

    @Override
    public TagVisitor visitListTag(String name, TagType tagType, int size) throws IOException, TagException {
        return rootTagWriter.visitListTag(name, tagType, size);
    }

    @Override
    public TagVisitor visitCompoundTag(String name) throws IOException, TagException {
        return rootTagWriter.visitCompoundTag(name);
    }


    @Override
    public void visitIntArrayTag(String name, int[] value) throws IOException, TagException {
        rootTagWriter.visitIntArrayTag(name, value);
    }


    @Override
    public void visitEnd() throws IOException, TagException {
        rootTagWriter.visitEnd();
    }

    /**
     * 刷新此写入器的数据到输出流.
     *
     * @throws IOException
     *      若发生io异常
     */
    @Override
    public void flush() throws IOException {
        dataOutputStream.flush();
    }

    /**
     * 关闭此写入器和其输出流.
     *
     * @throws IOException
     *      若发生io异常
     */
    @Override
    public void close() throws IOException {
        dataOutputStream.close();
    }



    abstract class AbstractTagWriter implements TagWriter {

        @Override
        public void visitBegin() {

        }

        @Override
        public void visitByteTag(String name, byte value) throws IOException, TagException {
            dataOutputStream.writeByte(1);
            dataOutputStream.writeUTF(name);
            dataOutputStream.writeByte(value);
        }

        @Override
        public void visitShortTag(String name, short value) throws IOException, TagException {
            dataOutputStream.writeByte(2);
            dataOutputStream.writeUTF(name);
            dataOutputStream.writeShort(value);
        }

        @Override
        public void visitIntTag(String name, int value) throws IOException, TagException {
            dataOutputStream.writeByte(3);
            dataOutputStream.writeUTF(name);
            dataOutputStream.writeInt(value);
        }

        @Override
        public void visitLongTag(String name, long value) throws IOException, TagException {
            dataOutputStream.writeByte(4);
            dataOutputStream.writeUTF(name);
            dataOutputStream.writeLong(value);
        }

        @Override
        public void visitFloatTag(String name, float value) throws IOException, TagException {
            dataOutputStream.writeByte(5);
            dataOutputStream.writeUTF(name);
            dataOutputStream.writeFloat(value);
        }

        @Override
        public void visitDoubleTag(String name, double value) throws IOException, TagException {
            dataOutputStream.writeByte(6);
            dataOutputStream.writeUTF(name);
            dataOutputStream.writeDouble(value);
        }

        @Override
        public void visitByteArrayTag(String name, byte[] value) throws IOException, TagException {
            dataOutputStream.writeByte(7);
            dataOutputStream.writeUTF(name);
            dataOutputStream.writeInt(value.length);
            dataOutputStream.write(value);
        }

        @Override
        public void visitStringTag(String name, String value) throws IOException, TagException {
            dataOutputStream.writeByte(8);
            dataOutputStream.writeUTF(name);
            dataOutputStream.writeUTF(value);
        }

        @Override
        public TagVisitor visitListTag(String name, TagType tagType, int size) throws IOException, TagException {
            dataOutputStream.writeByte(9);
            dataOutputStream.writeUTF(name);
            dataOutputStream.writeByte(tagType.id);
            dataOutputStream.writeInt(size);
            return listTagWriter.init(tagType);
        }

        @Override
        public TagVisitor visitCompoundTag(String name) throws IOException, TagException {
            dataOutputStream.writeByte(10);
            dataOutputStream.writeUTF(name);
            return compoundTagWriter;
        }

        @Override
        public void visitIntArrayTag(String name, int[] value) throws IOException, TagException {
            dataOutputStream.writeByte(11);
            dataOutputStream.writeUTF(name);
            dataOutputStream.writeInt(value.length);
            for (int i = 0; i < value.length; i++) {
                dataOutputStream.writeInt(value[i]);
            }
        }

        @Override
        public abstract void visitEnd() throws IOException, TagException;
    }

    class RootTagWriter extends AbstractTagWriter {

        @Override
        public void visitEnd() throws IOException, TagException {

        }
    }

    class ListTagWriter extends AbstractTagWriter {

        private TagType type = null;

        public ListTagWriter init(TagType type) {
            this.type = type;
            return this;
        }

        @Override
        public void visitByteTag(String name, byte value) throws IOException, TagException {
            if(type != TagType.BYTE) _throw_tag_type_error(type, TagType.BYTE);
            super.visitByteTag(name, value);
        }

        @Override
        public void visitShortTag(String name, short value) throws IOException, TagException {
            if(type != TagType.SHORT) _throw_tag_type_error(type, TagType.SHORT);
            super.visitShortTag(name, value);
        }

        @Override
        public void visitIntTag(String name, int value) throws IOException, TagException {
            if(type != TagType.INT) _throw_tag_type_error(type, TagType.INT);
            super.visitIntTag(name, value);
        }

        @Override
        public void visitLongTag(String name, long value) throws IOException, TagException {
            if(type != TagType.LONG) _throw_tag_type_error(type, TagType.LONG);
            super.visitLongTag(name, value);
        }

        @Override
        public void visitFloatTag(String name, float value) throws IOException, TagException {
            if(type != TagType.FLOAT) _throw_tag_type_error(type, TagType.FLOAT);
            super.visitFloatTag(name, value);
        }

        @Override
        public void visitDoubleTag(String name, double value) throws IOException, TagException {
            if(type != TagType.DOUBLE) _throw_tag_type_error(type, TagType.DOUBLE);
            super.visitDoubleTag(name, value);
        }

        @Override
        public void visitByteArrayTag(String name, byte[] value) throws IOException, TagException {
            if(type != TagType.BYTE_ARRAY) _throw_tag_type_error(type, TagType.BYTE_ARRAY);
            super.visitByteArrayTag(name, value);
        }

        @Override
        public void visitStringTag(String name, String value) throws IOException, TagException {
            if(type != TagType.STRING) _throw_tag_type_error(type, TagType.STRING);
            super.visitStringTag(name, value);
        }

        @Override
        public TagVisitor visitListTag(String name, TagType tagType, int size) throws IOException, TagException {
            if(type != TagType.LIST) _throw_tag_type_error(type, TagType.LIST);
            return super.visitListTag(name, tagType, size);
        }

        @Override
        public TagVisitor visitCompoundTag(String name) throws IOException, TagException {
            if(type != TagType.COMPOUND) _throw_tag_type_error(type, TagType.COMPOUND);
            return super.visitCompoundTag(name);
        }

        @Override
        public void visitIntArrayTag(String name, int[] value) throws IOException, TagException {
            if(type != TagType.INT_ARRAY) _throw_tag_type_error(type, TagType.INT_ARRAY);
            super.visitIntArrayTag(name, value);
        }

        @Override
        public void visitEnd() throws IOException, TagException {
            type = null;
        }
    }

    class CompoundTagWriter extends AbstractTagWriter {

        @Override
        public void visitEnd() throws IOException, TagException {
            dataOutputStream.writeByte(0);
        }
    }

    private void _throw_tag_type_error(TagType needType, TagType actualType) throws TagException {
        throw new TagException("此处需要的Tag类型是 " + needType.name + " 而不是 " + actualType.name);
    }
}
