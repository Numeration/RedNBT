package org.redNbt.convert;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


/**
 * @author Bug[3050429487@qq.com]
 */
public class AsmInstructionUtils {

    private AsmInstructionUtils() {}

    public static final BlockContext defBlock(MethodVisitor visitor, BlockContext context) {
        Label start = new Label();
        Label end   = new Label();

        visitor.visitLabel(start);
        return new BlockContext(start, end, context.localSize);
    }

    public static final void endBlock(MethodVisitor visitor, BlockContext context) {
        visitor.visitLabel(context.endLabel);
    }

    public static final int defVar(MethodVisitor visitor, BlockContext context, String name, String desc, String signature) {
        int index = context.defVar(desc);
        visitor.visitLocalVariable(name, desc, signature, context.startLabel, context.endLabel, index);
        return index;
    }

    public static final void newObject(MethodVisitor visitor, String name) {
        visitor.visitTypeInsn(Opcodes.NEW, name);
    }

    interface Coder {
        void coding(MethodVisitor visitor, BlockContext context);
    }

    interface ConditionCoder {
        void coding(MethodVisitor visitor, BlockContext context, Label falseLabel);
    }

    interface LoopCoder {
        void coding(MethodVisitor visitor, BlockContext context, Label loopLabel, Label endLabel);
    }

    public static final void defLoop(MethodVisitor visitor, BlockContext context,
                                     Coder before, ConditionCoder condition, Coder after,
                                     LoopCoder loop)
    {

        BlockContext loopBlock = defBlock(visitor, context);

        before.     coding(visitor, loopBlock);

        Label LOOP = new Label();
        Label END = new Label();

        visitor.visitLabel(LOOP);
        condition.  coding(visitor, loopBlock, END);
        loop.       coding(visitor, loopBlock, LOOP, END);
        after.      coding(visitor, loopBlock);
        visitor.visitJumpInsn(Opcodes.GOTO, LOOP);
        visitor.visitLabel(END);

        endBlock(visitor, loopBlock);
    }

    interface CatchCoder {
        void coding(MethodVisitor visitor, BlockContext context, ExceptionStatement... exceptionStatement);
    }

    public static final void defSimpleTryCatch(MethodVisitor visitor, BlockContext context,
                                               Coder tryCoder,
                                               ExceptionStatement[] exceptionStatements, CatchCoder[] handleCoder)
    {
        Label START = new Label();
        Label END = new Label();

        Label[] handlers = new Label[handleCoder.length];

        for (int i = 0; i < exceptionStatements.length; i++) {
            Label HANDLER = new Label();
            visitor.visitTryCatchBlock(START, END, HANDLER, exceptionStatements[i].getExceptionName());
            handlers[i] = HANDLER;
        }


        {
            BlockContext tryBlock = defBlock(visitor, context);

            visitor.visitLabel(START);
            tryCoder.coding(visitor, tryBlock);
            visitor.visitJumpInsn(Opcodes.GOTO, END);

            endBlock(visitor, tryBlock);
        }

        for (int i = 0; i < handleCoder.length; i++) {
            BlockContext catchBlock = defBlock(visitor, context);

            visitor.visitLabel(handlers[i]);
            handleCoder[i].coding(visitor, catchBlock, exceptionStatements[i]);
            visitor.visitJumpInsn(Opcodes.GOTO, END);

            endBlock(visitor, catchBlock);
        }

        visitor.visitLabel(END);

    }

    public static final void defSimpleIf(MethodVisitor visitor, BlockContext context,
                                         ConditionCoder condition, Coder coder)
    {
        Label END = new Label();
        BlockContext ifBlock = defBlock(visitor, context);

        condition. coding(visitor, ifBlock, END);
        coder.     coding(visitor, ifBlock);

        endBlock(visitor, ifBlock);
        visitor.visitLabel(END);
    }

    public static final void defSimpleIfElse(MethodVisitor visitor, BlockContext context,
                                             ConditionCoder condition, Coder coder, Coder elseCoder)
    {

        Label ELSE = new Label();
        Label END = new Label();
        {
            BlockContext ifBlock = defBlock(visitor, context);

            condition. coding(visitor, ifBlock, ELSE);
            coder.     coding(visitor, ifBlock);
            visitor.visitJumpInsn(Opcodes.GOTO, END);

            endBlock(visitor, ifBlock);
        }

        visitor.visitLabel(ELSE);
        {
            BlockContext elseBlock = defBlock(visitor, context);

            elseCoder.  coding(visitor, context);

            endBlock(visitor, elseBlock);
        }

        visitor.visitLabel(END);
    }

}
