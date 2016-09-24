package org.redNbt.util;

import org.objectweb.asm.Type;

import java.util.HashMap;
import java.util.Map;

/**
 * Nbt tag类型的描述.
 *
 * @author Bug[3050429487@qq.com]
 */
public enum TagType {

    END        ( 0,           "end"),
    BYTE       ( 1,          "byte"),
    SHORT      ( 2,         "short"),
    INT        ( 3,           "int"),
    LONG       ( 4,          "long"),
    FLOAT      ( 5,         "float"),
    DOUBLE     ( 6,        "double"),
    BYTE_ARRAY ( 7,    "byte_array"),
    STRING     ( 8,        "string"),
    LIST       ( 9,          "list"),
    COMPOUND   (10,      "compound"),
    INT_ARRAY  (11,     "int_array");

    public static final String ASM_DESC = Type.getDescriptor(TagType.class);
    public static final String ASM_NAME = Type.getInternalName(TagType.class);

    /**
     * 通过<i>ASM_NAME</i>索引tag type
     */
    public static final Map<String, TagType> BY_NAME = new HashMap<>();

    /**
     * 通过<i>id</i>索引tag type
     */
    public static final TagType[]            BY_ID = {
            END,BYTE,SHORT,INT,LONG,FLOAT,DOUBLE,BYTE_ARRAY,STRING,
            LIST,COMPOUND,INT_ARRAY
    };

    /*
    * 初始化BY_NAME与BY_TYPE静态字段
    */
    static {
        for(TagType tagType : TagType.values()) {
            BY_NAME.put(tagType.name, tagType);
        }
    }

    /**
     * Tag type的tag id.
     */
    public final int id;

    /**
     * Tag type的名字
     */
    public final String name;


    /**
     * Tag type唯一的构造器.
     *
     * @param id
     *      tag id
     * @param name
     *      tag 的名字
     */
    TagType(int id, String name) {
        this.id = id;
        this.name = name;
    }

}
