package org.redNbt.io;

import org.redNbt.util.TagVisitor;

/**
 * 此接口表示可从实现类中读取tag.
 *
 * @author Bug[3050429487@qq.com]
 *
 */
public interface TagReader {

    /**
     * 读取tag.
     *
     * @param visitor
     *      此<code>{@link TagVisitor TagVisitor}</code>中的visit*方法将会被依次
     *      调用以达到传递tag数据的目的.
     *
     * @throws Exception
     *      此处可以抛出任何异常，并且在传入的<code>visitor</code>中抛出的异常也会
     *      由此抛出.
     *
     * @see TagVisitor
     */
    void readTag(TagVisitor visitor) throws Exception;

}
