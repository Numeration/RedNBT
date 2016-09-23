package org.redNbt.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Nbt tag类型的描述.
 * 用于在tag的底层类型<i>id</i>和java类型之间转换
 *
 * @author Bug[3050429487@qq.com]
 */
public enum TagType {

    END        ( 0,           "end",    void.class),
    BYTE       ( 1,          "byte",    byte.class),
    SHORT      ( 2,         "short",   short.class),
    INT        ( 3,           "int",     int.class),
    LONG       ( 4,          "long",    long.class),
    FLOAT      ( 5,         "float",   float.class),
    DOUBLE     ( 6,        "double",  double.class),
    BYTE_ARRAY ( 7,    "byte_array",  byte[].class),
    STRING     ( 8,        "string",  String.class),
    LIST       ( 9,          "list",    List.class),
    COMPOUND   (10,      "compound",     Map.class),
    INT_ARRAY  (11,     "int_array",   int[].class);


    /**
     * 通过<i>name</i>索引tag type
     */
    public static final Map<String, TagType> BY_NAME = new HashMap<>();

    /**
     * 通过<i>java type</i>索引tag type
     */
    public static final Map<Class,  TagType> BY_TYPE = new HashMap<>();

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
            BY_TYPE.put(tagType.type, tagType);
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
     * Tag type的java类型
     */
    public final Class type;

    /**
     * Tag type唯一的构造器.
     *
     * @param id
     *      tag id
     * @param name
     *      tag 的名字
     * @param type
     *      tag 的java类型
     */
    TagType(int id, String name, Class type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

}
