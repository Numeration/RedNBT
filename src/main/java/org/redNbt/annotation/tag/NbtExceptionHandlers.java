package org.redNbt.annotation.tag;

import java.lang.annotation.*;

/**
 * @author Bug[3050429487@qq.com]
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface NbtExceptionHandlers {
    NbtExceptionHandler[] value();
}
