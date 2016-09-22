package org.redNbt.util;

/**
 * 此接口抽象的表示了一个Tag Tree树.
 * 采用Visitor设计模式，可以自定义该接口的实现来访问/分析/传输Tag数据.<br/>
 * 由于NBT数据结构是顺序无关的，因此本接口的方法调用的顺序是随机的.   <br/>
 *
 * <B>注意!</B><code>{@link TagVisitor#visitEndTag() visitEndTag()}<code/>
 * 方法只能且必须在{@link TagVisitor#visitCompoundTag visitCompoundTag(name)}
 * 方法所返回的<code>TagVisitor<code/>对象上调用以将其用于表示CompoundTag的
 * 结束
 *
 * @author Bug<3050429487@qq.com>
 */
public interface TagVisitor {

    /**
     * 访问一个byte类型的nbt tag
     *
     * @param name
     *      tag的名字
     * @param value
     *      tag的值
     *
     * @throws Exception
     *      此处可以抛出任何异常，通常I/O相关的实现会抛出<code>{@link java.io.IOException}<code/>
     *      ,而在tag结构验证相关的实现则更倾向于抛出<code>{@link TagException}<code/>
     *      此处究极该抛出何种异常要视具体情况而定.
     */
    void visitByteTag(String name, byte value) throws Exception;

    /**
     * 访问一个short类型的nbt tag
     *
     * @param name
     *      tag的名字
     * @param value
     *      tag的值
     *
     * @throws Exception
     *      此处可以抛出任何异常，通常I/O相关的实现会抛出<code>{@link java.io.IOException}<code/>
     *      ,而在tag结构验证相关的实现则更倾向于抛出<code>{@link TagException}<code/>
     *      此处究极该抛出何种异常要视具体情况而定.
     */
    void visitShortTag(String name, short value) throws Exception;

    /**
     * 访问一个int类型的nbt tag
     *
     * @param name
     *      tag的名字
     * @param value
     *      tag的值
     *
     * @throws Exception
     *      此处可以抛出任何异常，通常I/O相关的实现会抛出<code>{@link java.io.IOException}<code/>
     *      ,而在tag结构验证相关的实现则更倾向于抛出<code>{@link TagException}<code/>
     *      此处究极该抛出何种异常要视具体情况而定.
     */
    void visitIntTag(String name, int value) throws Exception;

    /**
     * 访问一个long类型的nbt tag
     *
     * @param name
     *      tag的名字
     * @param value
     *      tag的值
     *
     * @throws Exception
     *      此处可以抛出任何异常，通常I/O相关的实现会抛出<code>{@link java.io.IOException}<code/>
     *      ,而在tag结构验证相关的实现则更倾向于抛出<code>{@link TagException}<code/>
     *      此处究极该抛出何种异常要视具体情况而定.
     */
    void visitLongTag(String name, long value) throws Exception;

    /**
     * 访问一个float类型的nbt tag
     *
     * @param name
     *      tag的名字
     * @param value
     *      tag的值
     *
     * @throws Exception
     *      此处可以抛出任何异常，通常I/O相关的实现会抛出<code>{@link java.io.IOException}<code/>
     *      ,而在tag结构验证相关的实现则更倾向于抛出<code>{@link TagException}<code/>
     *      此处究极该抛出何种异常要视具体情况而定.
     */
    void visitFloatTag(String name, float value) throws Exception;

    /**
     * 访问一个double类型的nbt tag
     *
     * @param name
     *      tag的名字
     * @param value
     *      tag的值
     *
     * @throws Exception
     *      此处可以抛出任何异常，通常I/O相关的实现会抛出<code>{@link java.io.IOException}<code/>
     *      ,而在tag结构验证相关的实现则更倾向于抛出<code>{@link TagException}<code/>
     *      此处究极该抛出何种异常要视具体情况而定.
     */
    void visitDoubleTag(String name, double value) throws Exception;

    /**
     * 访问一个byte数组类型的nbt tag
     *
     * @param name
     *      tag的名字
     * @param value
     *      tag的值
     *
     * @throws Exception
     *      此处可以抛出任何异常，通常I/O相关的实现会抛出<code>{@link java.io.IOException}<code/>
     *      ,而在tag结构验证相关的实现则更倾向于抛出<code>{@link TagException}<code/>
     *      此处究极该抛出何种异常要视具体情况而定.
     */
    void visitByteArrayTag(String name, byte[] value) throws Exception;

    /**
     * 访问一个字符串类型的nbt tag
     *
     * @param name
     *      tag的名字
     * @param value
     *      tag的值
     *
     * @throws Exception
     *      此处可以抛出任何异常，通常I/O相关的实现会抛出<code>{@link java.io.IOException}<code/>
     *      ,而在tag结构验证相关的实现则更倾向于抛出<code>{@link TagException}<code/>
     *      此处究极该抛出何种异常要视具体情况而定.
     */
    void visitStringTag(String name, String value) throws Exception;

    /**
     * 访问一个list类型的nbt tag
     *
     * @param name
     *      tag的名字
     * @param tagType
     *      list所包含的tag的类型，相当于伪代码<code>List&lt;tagType&gt;</code>
     * @param size
     *      list的大小
     *
     * @return
     *      用于访问list tag内部的tag visitor
     *
     * @throws Exception
     *      此处可以抛出任何异常，通常I/O相关的实现会抛出<code>{@link java.io.IOException}<code/>
     *      ,而在tag结构验证相关的实现则更倾向于抛出<code>{@link TagException}<code/>
     *      此处究极该抛出何种异常要视具体情况而定.
     */
    TagVisitor visitListTag(String name, TagType tagType, int size) throws Exception;

    /**
     * 访问一个compound类型的nbt tag
     *
     * @param name
     *      tag的名字
     *
     * @return
     *      用于访问compound tag内部的tag visitor，此visitor的
     *      <code>{@link TagVisitor#visitEndTag()}<code/>方法
     *      必须被调用，用于表示compound tag的结束
     *
     * @throws Exception
     *      此处可以抛出任何异常，通常I/O相关的实现会抛出<code>{@link java.io.IOException}<code/>
     *      ,而在tag结构验证相关的实现则更倾向于抛出<code>{@link TagException}<code/>
     *      此处究极该抛出何种异常要视具体情况而定.
     */
    TagVisitor visitCompoundTag(String name) throws Exception;

    /**
     * 访问一个end tag.
     * 该方法只能且必须在<code>{@link TagVisitor#visitCompoundTag(String)}<code/>
     * 方法返回的<code>TagVisitor<code/>对象上调用，以将其用于表
     * 示CompoundTag的结束
     *
     * @throws Exception
     *      此处可以抛出任何异常，通常I/O相关的实现会抛出<code>{@link java.io.IOException}<code/>
     *      ,而在tag结构验证相关的实现则更倾向于抛出<code>{@link TagException}<code/>
     *      此处究极该抛出何种异常要视具体情况而定.
     *
     * @see TagVisitor#visitCompoundTag
     */
    void visitEndTag() throws Exception;

    /**
     * 访问一个int数组类型的nbt tag
     *
     * @param name
     *      tag的名字
     * @param value
     *      tag的值
     *
     * @throws Exception
     *      此处可以抛出任何异常，通常I/O相关的实现会抛出<code>{@link java.io.IOException}<code/>
     *      ,而在tag结构验证相关的实现则更倾向于抛出<code>{@link TagException}<code/>
     *      此处究极该抛出何种异常要视具体情况而定.
     */
    void visitIntArrayTag(String name, int[] value) throws Exception;

}
