package org.redNbt.convert;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.redNbt.NbtTagNameRepeatedException;
import org.redNbt.annotation.tag.TagExceptionType;
import org.redNbt.util.TagType;

import java.util.Map;

import static org.redNbt.convert.AsmInstructionUtils.*;

/**
 * @author Bug[3050429487@qq.com]
 */

abstract class TagHandleInvokerBuilder {

    final Map<TagExceptionType,ExceptionHandlers> exceptionHandlersMap;
    final TagHandleInvokerContext tagHandleInvokerContext;

    public TagHandleInvokerBuilder(Map<TagExceptionType, ExceptionHandlers> exceptionHandlersMap,
                                   TagHandleInvokerContext tagHandleInvokerContext)
    {
        this.exceptionHandlersMap = exceptionHandlersMap;
        this.tagHandleInvokerContext = tagHandleInvokerContext;
    }

    public final AsmInstructionUtils.Coder def(int tagNameVar, TagType tagType, int valueVar, String valueDesc) {
        return (visitor, context) ->
                defSimpleIfElse(visitor, context,
                        defCheckRepeatedInvoke(),
                        defRepeatedInvoke(tagNameVar, tagType),
                        defInvoker(tagNameVar, tagType, valueVar, valueDesc));
    }

    protected final AsmInstructionUtils.ConditionCoder defCheckRepeatedInvoke() {
        return (visitor, context, falseLabel) -> {
            visitor.visitVarInsn  (Opcodes.ALOAD, 0);
            visitor.visitFieldInsn(Opcodes.GETFIELD,
                    tagHandleInvokerContext.owner,
                    tagHandleInvokerContext.invokerStateFieldName,
                    "I");
            visitor.visitLdcInsn(tagHandleInvokerContext.invokerStateBit);
            visitor.visitInsn     (Opcodes.IUSHR);
            visitor.visitInsn     (Opcodes.ICONST_1);
            visitor.visitInsn     (Opcodes.IAND);
            visitor.visitJumpInsn (Opcodes.IFNE, falseLabel);
        };
    }

    protected final AsmInstructionUtils.Coder defRepeatedInvoke(int tagNameVar, TagType tagType) {
        return (visitor, context) -> {
            int exceptionObjectVar = defVar(visitor, context, "_nbt_tag_name_repeated_exception__",
                    NbtTagNameRepeatedException.ASM_DESC, null);

            newObject(visitor, NbtTagNameRepeatedException.ASM_NAME);
            visitor.visitInsn      (Opcodes.DUP);
            visitor.visitVarInsn   (Opcodes.ASTORE, exceptionObjectVar);
            visitor.visitVarInsn   (Opcodes.ALOAD, tagNameVar);
            visitor.visitFieldInsn (Opcodes.GETSTATIC, tagType.ASM_NAME, tagType.name, tagType.ASM_DESC);
            visitor.visitMethodInsn(Opcodes.INVOKESPECIAL,
                    NbtTagNameRepeatedException.ASM_NAME,
                    "<init>",
                    "(Ljava/lang/String;Lorg/redNbt/util/TagType;)V",
                    false);


            boolean handled = applyExceptionHandler(visitor, context, exceptionObjectVar, TagExceptionType.ALL);
            handled |= applyExceptionHandler(visitor, context, exceptionObjectVar, TagExceptionType.NAME_REPEATED);

            if(!handled) {
                applyExceptionHandler(visitor, context, exceptionObjectVar, TagExceptionType.UNTREATED);
            }

            defRepeatedExceptionReturn().coding(visitor, context);
        };
    }

    protected final boolean applyExceptionHandler(MethodVisitor visitor, BlockContext context,
                                                  int exceptionObjectVar,
                                                  TagExceptionType tagExceptionType)
    {
        return applyExceptionHandler(visitor, context, exceptionObjectVar, exceptionHandlersMap.get(tagExceptionType));
    }

    protected final boolean applyExceptionHandler(MethodVisitor visitor, BlockContext context,
                                                  int exceptionObjectVar,
                                                  ExceptionHandlers exceptionHandlers)
    {
        boolean handled = false;
        if(exceptionHandlers != null) {
            int teVar = defVar(visitor, context, "_tag_entry__",
                    "Ljava/lang/Object;", null);

            visitor.visitVarInsn  (Opcodes.ALOAD, 0);
            visitor.visitFieldInsn(Opcodes.GETFIELD,
                    tagHandleInvokerContext.owner,
                    tagHandleInvokerContext.tagEntryFieldName,
                    tagHandleInvokerContext.tagEntryDesc);
            visitor.visitVarInsn  (Opcodes.ASTORE, teVar);

            for(HandlerSite handlerSite : exceptionHandlers.exceptionHandlerSet) {
                visitor.visitVarInsn(Opcodes.ALOAD, teVar);
                visitor.visitVarInsn(Opcodes.ALOAD, exceptionObjectVar);
                visitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                        tagHandleInvokerContext.tagEntryName,
                        handlerSite.name, handlerSite.desc,
                        false);
                handled = true;
            }
        }

        return handled;
    }

    protected abstract AsmInstructionUtils.Coder defRepeatedExceptionReturn();

    protected abstract AsmInstructionUtils.Coder defInvoker(int tagNameVar, TagType tagType, int valueVar, String valueDesc);

    protected final AsmInstructionUtils.Coder defActivateTask() {
        return (visitor, context) -> {
            LoopContext loopContext = new LoopContext();

            AsmInstructionUtils.Coder before = (visitor1, context1) -> {
                int indexVar = defVar(visitor1, context1, "_index__", "I", null);
                visitor1.visitInsn    (Opcodes.ICONST_0);
                visitor1.visitVarInsn (Opcodes.ISTORE, indexVar);

                int arrayVar = defVar(visitor1, context1, "_array__", TaskMethodInvoker.ASM_ARRAY_DESC, null);
                visitor1.visitVarInsn  (Opcodes.ALOAD, 0);
                visitor1.visitFieldInsn(Opcodes.GETFIELD,
                        tagHandleInvokerContext.owner,
                        tagHandleInvokerContext.hootArrayFieldName,
                        TaskMethodInvoker.ASM_ARRAY_DESC);
                visitor1.visitInsn     (Opcodes.DUP);
                visitor1.visitVarInsn  (Opcodes.ASTORE, arrayVar);

                int lengthVar = defVar(visitor1, context1, "_length__", "I", null);
                visitor1.visitInsn     (Opcodes.ARRAYLENGTH);
                visitor1.visitVarInsn  (Opcodes.ISTORE, lengthVar);

                loopContext.indexVar = indexVar;
                loopContext.arrayVar = arrayVar;
                loopContext.lengthVar = lengthVar;

            };

            AsmInstructionUtils.ConditionCoder condition = (visitor1, context1, falseLabel) -> {
                int indexVar = loopContext.indexVar;
                int lengthVar = loopContext.lengthVar;

                visitor1.visitVarInsn (Opcodes.ILOAD, indexVar);
                visitor1.visitVarInsn (Opcodes.ILOAD, lengthVar);
                visitor1.visitJumpInsn(Opcodes.IF_ICMPLT, falseLabel);
            };

            AsmInstructionUtils.Coder after = (visitor1, context1)-> {
                int indexVar = loopContext.indexVar;
                visitor1.visitIincInsn(indexVar, 1);
            };

            AsmInstructionUtils.LoopCoder coder = (visitor1, context1, loopLabel, endLabel) -> {
                int indexVar = loopContext.indexVar;
                int arrayVar = loopContext.arrayVar;


                visitor1.visitVarInsn   (Opcodes.ALOAD, arrayVar);
                visitor1.visitIntInsn   (Opcodes.ILOAD, indexVar);
                visitor1.visitInsn      (Opcodes.AALOAD);

                visitor1.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                        TaskMethodInvoker.ASM_NAME,
                        "onTask", "()V",
                        false);
            };

            defLoop(visitor, context, before, condition, after, coder);
        };
    }

    private final class LoopContext {
        int indexVar;
        int arrayVar;
        int lengthVar;

    }

    protected static final int getLoadVarOpcodes(String desc) {
        switch(desc.charAt(0)) {
            case 'Z':
            case 'C':
            case 'B':
            case 'S':
            case 'I':
                return Opcodes.ILOAD;
            case 'F':
                return Opcodes.FLOAD;
            case 'J':
                return Opcodes.LLOAD;
            case 'D':
                return Opcodes.DLOAD;
            default:
                return Opcodes.AALOAD;
        }
    }

}
