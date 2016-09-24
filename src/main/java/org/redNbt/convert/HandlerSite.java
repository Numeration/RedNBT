package org.redNbt.convert;

import org.objectweb.asm.Type;

import java.lang.reflect.Method;

/**
 * @author Bug[3050429487@qq.com]
 */
public class HandlerSite {

    HandlerSite() {

    }

    HandlerSite(Method method) {
        setMethod(method);
    }

    void setMethod(Method method) {
        this.name = method.getName();
        this.desc = Type.getMethodDescriptor(method);
    }

    String name;
    String desc;

}
