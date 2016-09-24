package org.redNbt.convert;

import org.objectweb.asm.ClassVisitor;
import org.redNbt.annotation.tag.TagExceptionType;
import org.redNbt.util.TagType;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Bug[3050429487@qq.com]
 */
public class CompoundEntryBuilder {

    private final String name;
    private final ClassVisitor classVisitor;

    private final String declaringClassName;

    private final HashMap<String, TagHandlerSite> tagHandlerMap;
    private final HashMap<TagExceptionType, ExceptionHandlers> exceptionHandlerMap;


    private final class TagHandlerSite extends HandlerSite {
        TagType tagType;
        Set<TaskHandlerSite> taskHandlerSiteSet = new HashSet<>();
    }

    private final class TaskHandlerSite extends HandlerSite {
        TaskHandlerSite(Method method, int targetLocation) {
            super(method);
            this.targetLocation = targetLocation;
        }
        int targetLocation;
    }


    public CompoundEntryBuilder(String name, ClassVisitor classVisitor, String declaringClassName) {
        this.name = name;
        this.classVisitor = classVisitor;
        this.declaringClassName = declaringClassName;

        this.tagHandlerMap = new HashMap<>();
        this.exceptionHandlerMap = new HashMap<>();
    }


    public void makeTagHandler(Method method, String name, TagType tagType) {
        TagHandlerSite handler = tagHandlerMap.get(name);

        if(handler == null) {
            handler = new TagHandlerSite();
            tagHandlerMap.put(name, handler);
        } else if(handler.name != null){
            throw new RuntimeException("重复定义Tag处理器");
        }

        handler.setMethod(method);
        handler.tagType = tagType;
    }

    public void makeTaskHandler(Method method, String[] waits) {
        TaskHandlerSite taskHandlerSite = new TaskHandlerSite(method, waits.length);

        for(String wait : waits) {
            TagHandlerSite handler = tagHandlerMap.get(wait);

            if(handler == null) {
                handler = new TagHandlerSite();
                tagHandlerMap.put(name, handler);
            }

            handler.taskHandlerSiteSet.add(taskHandlerSite);
        }
    }

    public void makeExceptionHandler(Method method, TagExceptionType exceptionType) {
        ExceptionHandlers handlers = exceptionHandlerMap.get(exceptionType);
        if(handlers == null) {
            handlers = new ExceptionHandlers();
            exceptionHandlerMap.put(exceptionType, handlers);
        }

        HandlerSite handler = new HandlerSite();
        handler.setMethod(method);

        handlers.exceptionHandlerSet.add(handler);
    }


}
