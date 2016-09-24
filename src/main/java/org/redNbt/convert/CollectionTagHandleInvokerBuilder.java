package org.redNbt.convert;

import org.redNbt.annotation.tag.TagExceptionType;
import org.redNbt.util.TagType;

import java.util.Map;

/**
 * @author Bug[3050429487@qq.com]
 */
public class CollectionTagHandleInvokerBuilder extends TagHandleInvokerBuilder {

    public CollectionTagHandleInvokerBuilder(Map<TagExceptionType, ExceptionHandlers> exceptionHandlersMap,
                                             TagHandleInvokerContext tagHandleInvokerContext) {
        super(exceptionHandlersMap, tagHandleInvokerContext);
    }

    @Override
    protected AsmInstructionUtils.Coder defRepeatedExceptionReturn() {
        return null;
    }

    @Override
    protected AsmInstructionUtils.Coder defInvoker(int tagNameVar, TagType tagType, int valueVar, String valueDesc) {
        return null;
    }
}
