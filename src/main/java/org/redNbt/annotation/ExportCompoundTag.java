package org.redNbt.annotation;

import java.lang.annotation.*;

/**
 * @author Bug[3050429487@qq.com]
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ExportCompoundTag {

    /**
     * 输出compound tag的名字
     *
     * @return
     *      一个字符串表示输出compound tag的名字
     */
    String name();

}
