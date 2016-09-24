package org.redNbt.annotation.tag.compound;

import org.redNbt.util.TagType;

import java.lang.annotation.*;

/**
 * @author Bug[3050429487@qq.com]
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface OutTag {

    /**
     * 输出tag的名字
     *
     * @return
     *      一个字符串表示输出tag的名字
     */
    String name();

    /**
     * 输出tag的类型.
     *
     * @return
     *      一个{@link TagType 枚举类型}表示输出tag的类型
     * @see
     *      TagType
     */
    TagType type();

}
