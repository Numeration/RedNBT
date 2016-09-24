package org.redNbt.util;

/**
 * 分离Tag数据处理逻辑与Tag数据访问异常处理逻辑.
 * 这个tag visitor exception separator包装一个tag visitor并拦截其抛出的异常然后使用
 * {@link TVExceptionHandler TVExceptionHandler}来处理被拦截的异常
 *
 * @author Bug[3050429487@qq.com]
 *
 * @see TagVisitor
 * @see SecurityTagVisitor
 * @see TVExceptionHandler
 */
public class TVExceptionSeparator implements SecurityTagVisitor {

    private final TagVisitor tagVisitor;
    private final TVExceptionHandler exceptionHandler;
    private final boolean isListVisitor;

    public TVExceptionSeparator(TagVisitor tagVisitor, TVExceptionHandler exceptionHandler, boolean isListVisitor) {
        this.tagVisitor = tagVisitor;
        this.exceptionHandler = exceptionHandler;
        this.isListVisitor = isListVisitor;
    }

    private int count;

    @Override
    public void visitBegin() {
        exceptionHandler.onReady();
        try {
            tagVisitor.visitBegin();
        } catch (Exception e) {
            exceptionHandler.onVisitBeginException(e);
        }
    }

    @Override
    public void visitByteTag(String name, byte value) {
        try {
            tagVisitor.visitByteTag(name, value);
        } catch (Exception e) {
            exceptionHandler.onVisitTagException((isListVisitor ? Integer.toString(count) : name), TagType.BYTE, e);
        }

        count++;
    }

    @Override
    public void visitShortTag(String name, short value) {
        try {
            tagVisitor.visitShortTag(name, value);
        } catch (Exception e) {
            exceptionHandler.onVisitTagException((isListVisitor ? Integer.toString(count) : name), TagType.SHORT, e);
        }

        count++;
    }

    @Override
    public void visitIntTag(String name, int value) {
        try {
            tagVisitor.visitIntTag(name, value);
        } catch (Exception e) {
            exceptionHandler.onVisitTagException((isListVisitor ? Integer.toString(count) : name), TagType.INT, e);
        }

        count++;
    }

    @Override
    public void visitLongTag(String name, long value) {
        try {
            tagVisitor.visitLongTag(name, value);
        } catch (Exception e) {
            exceptionHandler.onVisitTagException((isListVisitor ? Integer.toString(count) : name), TagType.LONG, e);
        }

        count++;
    }

    @Override
    public void visitFloatTag(String name, float value) {
        try {
            tagVisitor.visitFloatTag(name, value);
        } catch (Exception e) {
            exceptionHandler.onVisitTagException((isListVisitor ? Integer.toString(count) : name), TagType.FLOAT, e);
        }

        count++;
    }

    @Override
    public void visitDoubleTag(String name, double value) {
        try {
            tagVisitor.visitDoubleTag(name, value);
        } catch (Exception e) {
            exceptionHandler.onVisitTagException((isListVisitor ? Integer.toString(count) : name), TagType.DOUBLE, e);
        }

        count++;
    }

    @Override
    public void visitByteArrayTag(String name, byte[] value) {
        try {
            tagVisitor.visitByteArrayTag(name, value);
        } catch (Exception e) {
            exceptionHandler.onVisitTagException((isListVisitor ? Integer.toString(count) : name), TagType.BYTE_ARRAY, e);
        }

        count++;
    }

    @Override
    public void visitStringTag(String name, String value) {
        try {
            tagVisitor.visitStringTag(name, value);
        } catch (Exception e) {
            exceptionHandler.onVisitTagException((isListVisitor ? Integer.toString(count) : name), TagType.SHORT, e);
        }

        count++;
    }

    @Override
    public SecurityTagVisitor visitListTag(String name, TagType tagType, int size) {
        TagVisitor listTagVisitor = null;
        String tagAlias = (isListVisitor ? Integer.toString(count) : name);

        try {
            listTagVisitor = tagVisitor.visitListTag(name, tagType, size);
        } catch (Exception e) {
            exceptionHandler.onVisitTagException(tagAlias, TagType.LIST, e);
        }

        count++;

        return listTagVisitor == null ?
                null : new TVExceptionSeparator
                (listTagVisitor, exceptionHandler.toListTagVisitor(tagAlias, tagType, size), true);
    }

    @Override
    public SecurityTagVisitor visitCompoundTag(String name) {
        TagVisitor compoundTagVisitor = null;
        String tagAlias = (isListVisitor ? Integer.toString(count) : name);

        try {
            compoundTagVisitor = tagVisitor.visitCompoundTag(name);
        } catch (Exception e) {
            exceptionHandler.onVisitTagException(tagAlias, TagType.COMPOUND, e);
        }

        count++;

        return compoundTagVisitor == null ?
                null : new TVExceptionSeparator
                (compoundTagVisitor, exceptionHandler.toCompoundTagVisitor(tagAlias), false);
    }

    @Override
    public void visitIntArrayTag(String name, int[] value) {
        try {
            tagVisitor.visitIntArrayTag(name, value);
        } catch (Exception e) {
            exceptionHandler.onVisitTagException((isListVisitor ? Integer.toString(count) : name), TagType.INT_ARRAY, e);
        }

        count++;
    }

    @Override
    public void visitEnd() {
        try {
            tagVisitor.visitEnd();
        } catch (Exception e) {
            exceptionHandler.onVisitEndException(e);
        }
        exceptionHandler.onEnd();
    }
}
