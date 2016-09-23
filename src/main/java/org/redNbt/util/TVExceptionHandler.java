package org.redNbt.util;

/**
 * 处理{@link TagVisitor TagVisitor}中异常的处理器.
 * 此处理器目的是为了分离tag数据处理逻辑和tag数据访问异常逻辑，通常此处理
 * 器是配合{@link TVExceptionSeparator TVExceptionSeparator}一起使用的
 *
 * @author Bug[3050429487@qq.com]
 *
 * @see TagVisitor
 * @see TVExceptionSeparator
 */
public interface TVExceptionHandler {

    /**
     * 让处理器待命，准备应对随时都有可能发生的tag数据访问异常.
     * 此方法必须在调用此接口的其它方法之前被调用
     */
    void onReady();

    /**
     * 处理{@link TagVisitor#visitBegin()}抛出的异常.
     *
     * @param exception
     *      {@link TagVisitor#visitBegin()}抛出的异常
     */
    void onVisitBeginException(Exception exception);

    /**
     * 处理访问tag时所遇到的异常.
     * 也就是处理{@link TagVisitor TagVisitor}中visit..Tag方法
     * 所抛出的异常.
     *
     * @param name
     *      异常tag的名称，或在list tag中的索引
     * @param type
     *      异常tag的类型
     * @param exception
     *      访问tag时所遇到的异常
     */
    void onVisitTagException(String name, TagType type, Exception exception);

    /**
     * 在顺利访问list tag后将会调用此方法获得一个异常处理器.
     * 并处理list tag visitor中的异常
     *
     * @param name
     *      list tag的名称，或在list tag中的索引
     * @param tagType
     *      list tag所包含的tag的类型
     * @param size
     *      list tag的大小
     *
     * @return
     *      处理list tag visitor异常的异常处理器
     *
     * @see TagVisitor#visitListTag(String, TagType, int)
     */
    TVExceptionHandler toListTagVisitor(String name, TagType tagType, int size);

    /**
     * 在顺利访问compound tag后会调用此方法获得一个异常处理器.
     * 并处理compound tag visitor中的异常
     *
     * @param name
     *      compound tag的名称，或在list tag中的索引
     *
     * @return
     *      处理compound tag visitor异常的异常处理器
     */
    TVExceptionHandler toCompoundTagVisitor(String name);

    /**
     * 处理{@link TagVisitor#visitEnd()}抛出的异常.
     *
     * @param exception
     *      {@link TagVisitor#visitEnd()}抛出的异常
     */
    void onVisitEndException(Exception exception);

    /**
     * 让处理器结束工作.
     * 此方法必须在调用此接口其它方法之后被调用
     */
    void onEnd();

}
