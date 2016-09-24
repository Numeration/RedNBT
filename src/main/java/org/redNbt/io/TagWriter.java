package org.redNbt.io;

import org.redNbt.util.TagException;
import org.redNbt.util.TagType;
import org.redNbt.util.TagVisitor;

import java.io.IOException;

/**
 * 此接口是{@link TagVisitor}一个输出IO的实现.
 * 该实现中规范化了每个visit*方法的异常
 *
 * @author Bug[3050429487@qq.com]
 *
 * @see TagVisitor
 */
public interface TagWriter extends TagVisitor {

    @Override
    void visitBegin() throws IOException;

    @Override
    void visitByteTag(String name, byte value) throws IOException, TagException;

    @Override
    void visitShortTag(String name, short value) throws IOException, TagException;

    @Override
    void visitIntTag(String name, int value) throws IOException, TagException;

    @Override
    void visitLongTag(String name, long value) throws IOException, TagException;

    @Override
    void visitFloatTag(String name, float value) throws IOException, TagException;

    @Override
    void visitDoubleTag(String name, double value) throws IOException, TagException;

    @Override
    void visitByteArrayTag(String name, byte[] value) throws IOException, TagException;

    @Override
    void visitStringTag(String name, String value) throws IOException, TagException;

    @Override
    TagVisitor visitListTag(String name, TagType tagType, int size) throws IOException, TagException;

    @Override
    TagVisitor visitCompoundTag(String name) throws IOException, TagException;

    @Override
    void visitIntArrayTag(String name, int[] value) throws IOException, TagException;

    @Override
    void visitEnd() throws IOException, TagException;

}
