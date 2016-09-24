package org.redNbt.convert;

import org.objectweb.asm.Opcodes;
import org.redNbt.annotation.tag.TagExceptionType;
import org.redNbt.util.TagType;

import java.util.Map;

/**
 * @author Bug[3050429487@qq.com]
 */
class SimpleTagHandleInvokerBuilder extends TagHandleInvokerBuilder {

    public SimpleTagHandleInvokerBuilder(Map<TagExceptionType, ExceptionHandlers> exceptionHandlersMap,
                                         TagHandleInvokerContext tagHandleInvokerContext)
    {
        super(exceptionHandlersMap, tagHandleInvokerContext);
    }

    @Override
    protected AsmInstructionUtils.Coder defRepeatedExceptionReturn() {
        return (visitor, context) -> {};
    }

    @Override
    protected AsmInstructionUtils.Coder defInvoker(int tagNameVar, TagType tagType, int valueVar, String valueDesc) {
        return (visitor, context) -> {
            visitor.visitVarInsn  (Opcodes.ALOAD, 0);
            visitor.visitFieldInsn(Opcodes.GETFIELD,
                    tagHandleInvokerContext.owner,
                    tagHandleInvokerContext.tagEntryFieldName,
                    tagHandleInvokerContext.tagEntryDesc);
            visitor.visitVarInsn(Opcodes.AALOAD, tagNameVar);
            visitor.visitVarInsn(getLoadVarOpcodes(valueDesc), valueVar);
            visitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                    tagHandleInvokerContext.tagEntryFieldName,
                    tagHandleInvokerContext.tagHandlerName,
                    tagHandleInvokerContext.tagHandlerDesc,
                    false);
        };
    }
}
