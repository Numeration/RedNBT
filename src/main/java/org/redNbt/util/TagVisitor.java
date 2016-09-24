package org.redNbt.util;

/**
 * 此接口抽象的表示了一个Tag Tree树.
 * 采用Visitor设计模式，可以自定义该接口的实现来访问/分析/传输Tag数据.<br>
 * 此接口的方法访问顺序为:<code>visitBegin(visitByteTag|visitShortTag|visitIntTag|
 * visitLongTag|visitFloatTag|visitDoubleTag|visitByteArrayTag|visitStringTag|
 * visitListTag|visitCompoundTag|visitIntArrayTag)*visitEnd</code><br><br>
 *
 * 如果当前的<code>TagVisitor</code>表示为一个List tag visitor的话，那么访问其内
 * 部tag时需要将name设置为null并且严格按照list中顺序来依次调用visit*方法，例如:
 * <br>
 * <code>
 *     &emsp;TagVisitor listVisitor = *.visitListTag(...);    <br>
 *     &emsp;listVisitor.visitBegin();                        <br>
 *     &emsp;listVisitor.visit*Tag(null, ...); //此tag在list tag中索引为0 <br>
 *     &emsp;listVisitor.visit*Tag(null, ...); //此tag在list tag中索引为1 <br>
 *     &emsp;listVisitor.visit*Tag(null, ...); //此tag在list tag中索引为2 <br>
 *     &emsp;....                                             <br>
 *     &emsp;listVisitor.visitEnd();                          <br>
 * </code>
 * <br>
 *
 * <b>注意!</b> 通过<code>{@link TagVisitor#visitListTag visitListTag}</code>和
 * <code>{@link TagVisitor#visitCompoundTag(String) visitCompoundTag}</code>获得
 * <code>TagVisitor</code>后只有将它从头到位的访问完成后才能继续访问
 * 当前的<code>TagVisitor</code>例如:<br>
 * <code>
 *     &emsp;TagVisitor currentVisitor = ....                 <br>
 *     &emsp;currentVisitor.visitBegin();                     <br>
 *     &emsp;TagVisitor listVisitor = currentVisitor.visitListTag(...);<br>
 *     &emsp;&emsp;//此处不能对currentVisitor进行任何访问操作，因为listVisitor还未访问完成<br>
 *     &emsp;listVisitor.visitBegin();                        <br>
 *     &emsp;....  //此处对listVisitor进行访问                <br>
 *     &emsp;listVisitor.visitEnd();                          <br>
 *     &emsp;....  //此处可以对currentVisitor进行访问操作了   <br>
 *     &emsp;currentVisitor.visitEnd();                       <br>
 * </code>
 *
 *
 * @author Bug[3050429487@qq.com]
 *
 * @see SecurityTagVisitor
 * @see org.redNbt.io.TagWriter
 * @see org.redNbt.io.TagReader
 */
public interface TagVisitor {

    /**
     * 开始一个访问序列.
     *
     * @throws Exception
     *      此处可以抛出任何异常，通常I/O相关的实现会抛出<code>{@link java.io.IOException}</code>
     *      ,而在tag结构验证相关的实现则更倾向于抛出<code>{@link TagException}</code>
     *      此处究竟该抛出何种异常要视具体情况而定.
     */
    void visitBegin() throws Exception;

    /**
     * 访问一个byte类型的nbt tag
     *
     * @param name
     *      tag的名字，或为null 如果此tag是一个list tag的元素
     * @param value
     *      tag的值
     *
     * @throws Exception
     *      此处可以抛出任何异常，通常I/O相关的实现会抛出<code>{@link java.io.IOException}</code>
     *      ,而在tag结构验证相关的实现则更倾向于抛出<code>{@link TagException}</code>
     *      此处究竟该抛出何种异常要视具体情况而定.
     */
    void visitByteTag(String name, byte value) throws Exception;

    /**
     * 访问一个short类型的nbt tag
     *
     * @param name
     *      tag的名字，或为null 如果此tag是一个list tag的元素
     * @param value
     *      tag的值
     *
     * @throws Exception
     *      此处可以抛出任何异常，通常I/O相关的实现会抛出<code>{@link java.io.IOException}</code>
     *      ,而在tag结构验证相关的实现则更倾向于抛出<code>{@link TagException}</code>
     *      此处究竟该抛出何种异常要视具体情况而定.
     */
    void visitShortTag(String name, short value) throws Exception;

    /**
     * 访问一个int类型的nbt tag
     *
     * @param name
     *      tag的名字，或为null 如果此tag是一个list tag的元素
     * @param value
     *      tag的值
     *
     * @throws Exception
     *      此处可以抛出任何异常，通常I/O相关的实现会抛出<code>{@link java.io.IOException}</code>
     *      ,而在tag结构验证相关的实现则更倾向于抛出<code>{@link TagException}</code>
     *      此处究竟该抛出何种异常要视具体情况而定.
     */
    void visitIntTag(String name, int value) throws Exception;

    /**
     * 访问一个long类型的nbt tag
     *
     * @param name
     *      tag的名字，或为null 如果此tag是一个list tag的元素
     * @param value
     *      tag的值
     *
     * @throws Exception
     *      此处可以抛出任何异常，通常I/O相关的实现会抛出<code>{@link java.io.IOException}</code>
     *      ,而在tag结构验证相关的实现则更倾向于抛出<code>{@link TagException}</code>
     *      此处究竟该抛出何种异常要视具体情况而定.
     */
    void visitLongTag(String name, long value) throws Exception;

    /**
     * 访问一个float类型的nbt tag
     *
     * @param name
     *      tag的名字，或为null 如果此tag是一个list tag的元素
     * @param value
     *      tag的值
     *
     * @throws Exception
     *      此处可以抛出任何异常，通常I/O相关的实现会抛出<code>{@link java.io.IOException}</code>
     *      ,而在tag结构验证相关的实现则更倾向于抛出<code>{@link TagException}</code>
     *      此处究竟该抛出何种异常要视具体情况而定.
     */
    void visitFloatTag(String name, float value) throws Exception;

    /**
     * 访问一个double类型的nbt tag
     *
     * @param name
     *      tag的名字，或为null 如果此tag是一个list tag的元素
     * @param value
     *      tag的值
     *
     * @throws Exception
     *      此处可以抛出任何异常，通常I/O相关的实现会抛出<code>{@link java.io.IOException}</code>
     *      ,而在tag结构验证相关的实现则更倾向于抛出<code>{@link TagException}</code>
     *      此处究竟该抛出何种异常要视具体情况而定.
     */
    void visitDoubleTag(String name, double value) throws Exception;

    /**
     * 访问一个byte数组类型的nbt tag
     *
     * @param name
     *      tag的名字，或为null 如果此tag是一个list tag的元素
     * @param value
     *      tag的值
     *
     * @throws Exception
     *      此处可以抛出任何异常，通常I/O相关的实现会抛出<code>{@link java.io.IOException}</code>
     *      ,而在tag结构验证相关的实现则更倾向于抛出<code>{@link TagException}</code>
     *      此处究竟该抛出何种异常要视具体情况而定.
     */
    void visitByteArrayTag(String name, byte[] value) throws Exception;

    /**
     * 访问一个字符串类型的nbt tag
     *
     * @param name
     *      tag的名字，或为null 如果此tag是一个list tag的元素
     * @param value
     *      tag的值
     *
     * @throws Exception
     *      此处可以抛出任何异常，通常I/O相关的实现会抛出<code>{@link java.io.IOException}</code>
     *      ,而在tag结构验证相关的实现则更倾向于抛出<code>{@link TagException}</code>
     *      此处究竟该抛出何种异常要视具体情况而定.
     */
    void visitStringTag(String name, String value) throws Exception;

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
     *      用于访问list tag内部的tag visitor，可能为<code>null</code>如果不支持访问list tag
     *
     * @throws Exception
     *      此处可以抛出任何异常，通常I/O相关的实现会抛出<code>{@link java.io.IOException}</code>
     *      ,而在tag结构验证相关的实现则更倾向于抛出<code>{@link TagException}</code>
     *      此处究竟该抛出何种异常要视具体情况而定.
     */
    TagVisitor visitListTag(String name, TagType tagType, int size) throws Exception;

    /**
     * 访问一个compound类型的nbt tag
     *
     * @param name
     *      tag的名字，或为null 如果此tag是一个list tag的元素
     *
     * @return
     *      用于访问compound tag内部的tag visitor，可能为<code>null</code>如果不支持访问
     *      compound tag
     *
     * @throws Exception
     *      此处可以抛出任何异常，通常I/O相关的实现会抛出<code>{@link java.io.IOException}</code>
     *      ,而在tag结构验证相关的实现则更倾向于抛出<code>{@link TagException}</code>
     *      此处究竟该抛出何种异常要视具体情况而定.
     */
    TagVisitor visitCompoundTag(String name) throws Exception;

    /**
     * 访问一个int数组类型的nbt tag
     *
     * @param name
     *      tag的名字，或为null 如果此tag是一个list tag的元素
     * @param value
     *      tag的值
     *
     * @throws Exception
     *      此处可以抛出任何异常，通常I/O相关的实现会抛出<code>{@link java.io.IOException}</code>
     *      ,而在tag结构验证相关的实现则更倾向于抛出<code>{@link TagException}</code>
     *      此处究竟该抛出何种异常要视具体情况而定.
     */
    void visitIntArrayTag(String name, int[] value) throws Exception;

    /**
     * 结束访问序列.
     *
     * @throws Exception
     *      此处可以抛出任何异常，通常I/O相关的实现会抛出<code>{@link java.io.IOException}</code>
     *      ,而在tag结构验证相关的实现则更倾向于抛出<code>{@link TagException}</code>
     *      此处究竟该抛出何种异常要视具体情况而定.
     */
    void visitEnd() throws Exception;

}
