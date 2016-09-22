package org.redNbt.io;

import org.redNbt.util.TagException;
import org.redNbt.util.TagType;
import org.redNbt.util.TagVisitor;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Bug<3050429487@qq.com>
 */
public class NbtInputStream extends InputStream {

    private final DataInputStream dataInputStream;

    public NbtInputStream(InputStream inputStream) {
        this.dataInputStream = new DataInputStream(inputStream);
    }

    @Override
    public int read() throws IOException {
        return dataInputStream.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        return dataInputStream.read(b);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return dataInputStream.read(b, off, len);
    }

    @Override
    public long skip(long n) throws IOException {
        return dataInputStream.skip(n);
    }

    @Override
    public int available() throws IOException {
        return dataInputStream.available();
    }

    @Override
    public void close() throws IOException {
        dataInputStream.close();
    }

    @Override
    public synchronized void mark(int readlimit) {}

    @Override
    public synchronized void reset() throws IOException {
        throw new IOException("mark/reset not supported");
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    public void readTag(TagVisitor visitor) throws Exception {
        _load_tag(visitor);
    }

    private void _load_tag(TagVisitor visitor) throws Exception {
        int id = dataInputStream.readByte();

        if(id == 0)
            _throw_end_tag_exception();

        _load_tag(id, visitor);
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
                for(int i = 0; i < length; i++)
                {
                    _load_tag(itemTypeId, tv);
                }
                break;

            case 10:
                tv = visitor.visitCompoundTag(name);
                while(true) {
                    int entryId = dataInputStream.readUnsignedByte();
                    if(entryId == 0)
                        break;
                    _load_tag(entryId, tv);
                }
                tv.visitEndTag();
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

    private void _throw_end_tag_exception() throws TagException {
        throw new TagException("EndTag与CompoundTag不匹配");
    }

    private void _throw_unknown_type_tag(int id) throws TagException {
        throw new TagException("未知的Tag类型ID " + id);
    }

}
