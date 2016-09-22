package org.redNbt.annotation;

import java.lang.annotation.*;

/**
 * @author Bug<3050429487@qq.com>
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ImportCompoundTag {

    /**
     * 输入compound tag的名字
     *
     * @return
     *      一个字符串表示输入compound tag的名字
     */
    String name();

}
