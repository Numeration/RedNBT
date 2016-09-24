package org.redNbt.annotation.tag;

import java.lang.annotation.*;

/**
 * @author Bug[3050429487@qq.com]
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(NbtExceptionHandlers.class)
@Documented
@Inherited
public @interface NbtExceptionHandler {
    Class<Exception> capture();
}