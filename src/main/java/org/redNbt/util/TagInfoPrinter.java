package org.redNbt.util;

/**
 * @author Bug[3050429487@qq.com]
 */
public class TagInfoPrinter implements SecurityTagVisitor {

    private static final String BYTE_TAG_NAME         = "ByteTag ------> ";
    private static final String SHORT_TAG_NAME        = "ShortTag -----> ";
    private static final String INT_TAG_NAME          = "IntTag -------> ";
    private static final String LONG_TAG_NAME         = "LongTag ------> ";
    private static final String FLOAT_TAG_NAME        = "FloatTag -----> ";
    private static final String DOUBLE_TAG_NAME       = "DoubleTag ----> ";
    private static final String BYTE_ARRAY_TAG_NAME   = "ByteArrayTag -> ";
    private static final String STRING_TAG_NAME       = "StringTag ----> ";
    private static final String LIST_TAG_NAME         = "ListTag ------> ";
    private static final String COMPOUND_TAG_NAME     = "CompoundTag --> ";
    private static final String INT_ARRAY_TAG_NAME    = "IntArrayTag --> ";


    private final int layer;
    private final boolean isListVisitor;
    private final StringBuilder stringBuilder;


    public TagInfoPrinter(StringBuilder stringBuilder, boolean isListVisitor) {
        this(stringBuilder, isListVisitor, 0);
    }

    private TagInfoPrinter(StringBuilder stringBuilder, boolean isListVisitor, int layer) {
        this.layer = layer;
        this.isListVisitor = isListVisitor;
        this.stringBuilder = stringBuilder;
    }

    int count;

    private StringBuilder _append_tab(int length) {
        while (length > 0) {
            stringBuilder.append('\t');
            length--;
        }
        return stringBuilder;
    }

    private StringBuilder _append_space(int length) {
        while (length > 0) {
            stringBuilder.append(' ');
            length--;
        }
        return stringBuilder;
    }

    private String getTagAlias(String name) {
        return isListVisitor ? Integer.toString(count) : name;
    }

    @Override
    public void visitBegin() {

    }

    @Override
    public void visitByteTag(String name, byte value) {
        _append_tab(layer)
                .append(BYTE_TAG_NAME)
                .append(getTagAlias(name))
                .append(" : ")
                .append(value)
                .append('\n');
        count++;
    }

    @Override
    public void visitShortTag(String name, short value) {
        _append_tab(layer)
                .append(SHORT_TAG_NAME)
                .append(getTagAlias(name))
                .append(" : ")
                .append(value)
                .append('\n');
        count++;
    }

    @Override
    public void visitIntTag(String name, int value) {
        _append_tab(layer)
                .append(INT_TAG_NAME)
                .append(getTagAlias(name))
                .append(" : ")
                .append(value)
                .append('\n');
        count++;
    }

    @Override
    public void visitLongTag(String name, long value) {
        _append_tab(layer)
                .append(LONG_TAG_NAME)
                .append(getTagAlias(name))
                .append(" : ")
                .append(value)
                .append('\n');
        count++;
    }

    @Override
    public void visitFloatTag(String name, float value) {
        _append_tab(layer)
                .append(FLOAT_TAG_NAME)
                .append(getTagAlias(name))
                .append(" : ")
                .append(value)
                .append('\n');
        count++;
    }

    @Override
    public void visitDoubleTag(String name, double value) {
        _append_tab(layer)
                .append(DOUBLE_TAG_NAME)
                .append(getTagAlias(name))
                .append(" : ")
                .append(value)
                .append('\n');
        count++;
    }

    private StringBuilder appendByteArray(int layer, byte[] array) {
        int alen = array.length;
        _append_tab(layer).append("length=").append(alen).append('\n');

        int yLen = alen / 16 + ((alen & 0xF) > 0 ? 1 : 0);

        //构建表头
        _append_tab(layer);
        _append_space(8 + 1);
        for(int i = 0; i < alen && i < 16; i++) {
            if(i < 10)
                stringBuilder.append('0');
            stringBuilder.append(i).append(' ');
        }
        stringBuilder.append('\n');

        //构建16进制数据展示窗口
        int index = 0;
        for(int y = 0; y < yLen; y++) {
            _append_tab(layer);

            //构建行号
            String line = Integer.toHexString(y);
            for(int i = 0; i < 8 - line.length(); i++)
                stringBuilder.append('0');
            stringBuilder.append(line).append(' ');

            //构建该行数据
            for (int x = 0; (x < 16) && (index < alen); x++, index++) {
                byte value = array[index];
                String valueHex = Integer.toHexString(value);
                if(valueHex.length() == 1) stringBuilder.append('0');
                stringBuilder.append(valueHex).append(' ');
            }

            stringBuilder.append('\n');
        }

        return stringBuilder;
    }

    @Override
    public void visitByteArrayTag(String name, byte[] value) {
        _append_tab(layer)
                .append(BYTE_ARRAY_TAG_NAME)
                .append(getTagAlias(name))
                .append(" : ")
                .append('\n');
        appendByteArray(layer + 1, value);
        count++;
    }

    @Override
    public void visitStringTag(String name, String value) {
        _append_tab(layer)
                .append(STRING_TAG_NAME)
                .append(getTagAlias(name))
                .append(" : ")
                .append(value)
                .append('\n');
        count++;
    }

    @Override
    public SecurityTagVisitor visitListTag(String name, TagType tagType, int size) {
        int clayer = this.layer;
        _append_tab(clayer)
                .append(LIST_TAG_NAME)
                .append(getTagAlias(name))
                .append(" : \n");

        _append_tab(clayer + 1).append("size=").append(size).append('\n');
        _append_tab(clayer + 1).append("{\n");

        return new TagInfoPrinter(stringBuilder, true, clayer + 2) {
            public void visitEnd() {
                _append_tab(clayer + 1).append("}\n");
            }
        };
    }

    @Override
    public SecurityTagVisitor visitCompoundTag(String name) {
        int clayer = this.layer;
        _append_tab(clayer)
                .append(COMPOUND_TAG_NAME)
                .append(getTagAlias(name))
                .append(" : \n");

        _append_tab(clayer + 1).append("{\n");

        return new TagInfoPrinter(stringBuilder, false, clayer + 2) {
            public void visitEnd() {
                _append_tab(clayer + 1).append("}\n");
            }
        };
    }

    private StringBuilder appendIntArray(int layer, int[] array) {
        int alen = array.length;
        _append_tab(layer).append("length=").append(alen).append('\n');

        int yLen = alen / 16 + ((alen & 0xF) > 0 ? 1 : 0);

        //构建表头
        _append_tab(layer);
        _append_space(8 + 1);
        for(int i = 0; i < alen && i < 16; i++) {
            if(i < 10)
                stringBuilder.append('0');
            stringBuilder.append(i).append(' ');
        }
        stringBuilder.append('\n');

        //构建16进制数据展示窗口
        int index = 0;
        for(int y = 0; y < yLen; y++) {
            _append_tab(layer);

            //构建行号
            String line = Integer.toHexString(y);
            for(int i = 0; i < 8 - line.length(); i++)
                stringBuilder.append('0');
            stringBuilder.append(line).append(' ');

            //构建该行数据
            for (int x = 0; (x < 16 / 2) && (index < alen); x++, index++) {
                int value = array[index];

                byte valueb1 = (byte) value;
                String valueHex = Integer.toHexString(valueb1);
                if(valueHex.length() == 1) stringBuilder.append('0');
                stringBuilder.append(valueHex).append(' ');

                byte value2 = (byte) (value >>> 8);
                valueHex = Integer.toHexString(value2);
                if(valueHex.length() == 1) stringBuilder.append('0');
                stringBuilder.append(valueHex).append(' ');
            }

            stringBuilder.append('\n');
        }

        return stringBuilder;
    }

    @Override
    public void visitIntArrayTag(String name, int[] value) {
        _append_tab(layer)
                .append(INT_ARRAY_TAG_NAME)
                .append(getTagAlias(name))
                .append(" : ")
                .append('\n');
        appendIntArray(layer + 1, value);
        count++;
    }

    @Override
    public void visitEnd() {

    }
}
