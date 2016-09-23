package org.redNbt.io;

import org.redNbt.util.TagException;
import org.redNbt.util.TagType;
import org.redNbt.util.TagVisitor;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 从{@link InputStream InputStream}中读取nbt数据.
 * 此写入器是{@link TagReader TagReader}接口的一个实现，所有意味着可以
 * 使用{@link TagVisitor TagVisitor}来从当前接口中读取nbt tag数据
 *
 * @author Bug[3050429487@qq.com]
 */
public final class NbtReader extends TagReader implements Closeable {

    private final DataInputStream dataInputStream;

    public NbtReader(InputStream inputStream) {
        this.dataInputStream = new DataInputStream(inputStream);
    }

    public void readTag(TagVisitor visitor) throws Exception {
        _load_tag(visitor);
    }


    private void _load_tag(TagVisitor visitor) throws Exception {
        visitor.visitBegin();
        int id = dataInputStream.readByte();

        if(id > 0)
            _load_tag(id, visitor);

        visitor.visitEnd();
    }

    private void _load_tag(int id, TagVisitor visitor) throws Exception {
        String name = dataInputStream.readUTF();

        switch (id) {
            case  1:
                visitor.visitByteTag(name,   dataInputStream.readByte());
                break;

            case  2:
                visitor.visitShortTag(name,  dataInputStream.readShort());
                break;

            case  3:
                visitor.visitIntTag(name,    dataInputStream.readInt());
                break;

            case  4:
                visitor.visitLongTag(name,   dataInputStream.readLong());
                break;

            case  5:
                visitor.visitFloatTag(name,  dataInputStream.readFloat());
                break;

            case  6:
                visitor.visitDoubleTag(name, dataInputStream.readDouble());
                break;

            case  7:
                int length = dataInputStream.readInt();
                byte[] bytes = new byte[length];
                dataInputStream.readFully(bytes);
                visitor.visitByteArrayTag(name, bytes);
                break;

            case  8:
                visitor.visitStringTag(name, dataInputStream.readUTF());
                break;

            case  9:
                final int itemTypeId = dataInputStream.readUnsignedByte();
                length = dataInputStream.readInt();
                TagVisitor tv = visitor.visitListTag(name, TagType.BY_ID[itemTypeId], length);
                tv.visitBegin();
                for(int i = 0; i < length; i++) {
                    _load_tag(itemTypeId, tv);
                }
                tv.visitEnd();
                break;

            case 10:
                tv = visitor.visitCompoundTag(name);
                tv.visitBegin();
                while(true) {
                    int entryId = dataInputStream.readUnsignedByte();
                    if(entryId == 0)
                        break;
                    _load_tag(entryId, tv);
                }
                tv.visitEnd();
                break;

            case 11:
                length = dataInputStream.readInt();
                int[] integers = new int[length];
                for (int i = 0; i < length; i++) {
                    integers[i] = dataInputStream.readInt();
                }
                visitor.visitIntArrayTag(name, integers);
                break;

            default:
                _throw_unknown_type_tag(id);
        }

    }

    private void _throw_unknown_type_tag(int id) throws TagException {
        throw new TagException("未知的Tag类型ID " + id);
    }

    @Override
    public void close() throws IOException {
        dataInputStream.close();
    }

}
