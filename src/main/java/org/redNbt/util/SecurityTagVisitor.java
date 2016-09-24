package org.redNbt.util;

/**
 * 此接口是{@link TagVisitor}一个无异常的安全实现.
 * 其约定和{@link TagVisitor}一样，只不过所有的visit*方法将不会抛出任何非运行时异常，
 * 因此使用此接口不需要做异常捕捉和处理.
 *
 * @author Bug [3050429487@qq.com]
 *
 * @see TagVisitor
 */
public interface SecurityTagVisitor extends TagVisitor {

    /**
     * 开始一个访问序列.
     */
    void visitBegin();

    /**
     * 访问一个byte类型的nbt tag
     *
     * @param name
     *      tag的名字，或为null 如果此tag是一个list tag的元素
     * @param value
     *      tag的值
     */
    void visitByteTag(String name, byte value);

    /**
     * 访问一个short类型的nbt tag
     *
     * @param name
     *      tag的名字，或为null 如果此tag是一个list tag的元素
     * @param value
     *      tag的值
     */
    void visitShortTag(String name, short value);

    /**
     * 访问一个int类型的nbt tag
     *
     * @param name
     *      tag的名字，或为null 如果此tag是一个list tag的元素
     * @param value
     *      tag的值
     */
    void visitIntTag(String name, int value);

    /**
     * 访问一个long类型的nbt tag
     *
     * @param name
     *      tag的名字，或为null 如果此tag是一个list tag的元素
     * @param value
     *      tag的值
     */
    void visitLongTag(String name, long value);

    /**
     * 访问一个float类型的nbt tag
     *
     * @param name
     *      tag的名字，或为null 如果此tag是一个list tag的元素
     * @param value
     *      tag的值
     */
    void visitFloatTag(String name, float value);

    /**
     * 访问一个double类型的nbt tag
     *
     * @param name
     *      tag的名字，或为null 如果此tag是一个list tag的元素
     * @param value
     *      tag的值
     */
    void visitDoubleTag(String name, double value);

    /**
     * 访问一个byte数组类型的nbt tag
     *
     * @param name
     *      tag的名字，或为null 如果此tag是一个list tag的元素
     * @param value
     *      tag的值
     */
    void visitByteArrayTag(String name, byte[] value);

    /**
     * 访问一个字符串类型的nbt tag
     *
     * @param name
     *      tag的名字，或为null 如果此tag是一个list tag的元素
     * @param value
     *      tag的值
     */
    void visitStringTag(String name, String value);

    /**
     * 访问一个list类型的nbt tag
     *
     * @param name
     *      tag的名字，或为null 如果此tag是一个list tag的元素
     * @param tagType
     *      list所包含的tag的类型，相当于伪代码<code>List&lt;tagType&gt;</code>
     * @param size
     *      list的大小
     *
     * @return
     *      用于访问list tag内部的secure tag visitor
     */
    SecurityTagVisitor visitListTag(String name, TagType tagType, int size);

    /**
     * 访问一个compound类型的nbt tag
     *
     * @param name
     *      tag的名字，或为null 如果此tag是一个list tag的元素
     *
     * @return
     *      用于访问compound tag内部的secure tag visitor
     */
    SecurityTagVisitor visitCompoundTag(String name);

    /**
     * 访问一个int数组类型的nbt tag
     *
     * @param name
     *      tag的名字，或为null 如果此tag是一个list tag的元素
     * @param value
     *      tag的值
     */
    void visitIntArrayTag(String name, int[] value);

    /**
     * 结束访问序列.
     */
    void visitEnd();

}
