package org.redNbt.convert;

import org.objectweb.asm.Type;

/**
 * @author Bug[3050429487@qq.com]
 */
public abstract class TaskMethodInvoker {

    static final String ASM_NAME = Type.getInternalName(TaskMethodInvoker.class);
    static final String ASM_DESC = Type.getDescriptor(TaskMethodInvoker.class);
    static final String ASM_ARRAY_DESC = Type.getDescriptor(TaskMethodInvoker[].class);

    private int location = 0;

    public abstract int getTargetLocation();

    public int getCurrentLocation() {
        return location;
    }

    public void tryActivate() throws Exception{
        location++;
        if(location == getTargetLocation())
            onTask();
    }

    public abstract void onTask() throws Exception;

}
