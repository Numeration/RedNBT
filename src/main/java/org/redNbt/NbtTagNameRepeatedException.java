package org.redNbt;

import org.objectweb.asm.Type;
import org.redNbt.util.TagType;

/**
 * @author Bug[3050429487@qq.com]
 */
public class NbtTagNameRepeatedException extends NbtException {

    public static final String ASM_NAME = Type.getInternalName(NbtTagNameRepeatedException.class);
    public static final String ASM_DESC = Type.getDescriptor(NbtTagNameRepeatedException.class);

    private final String repeatedName;
    private final TagType tagType;

    public NbtTagNameRepeatedException(String repeatedName, TagType tagType) {
        super("tag name重复: " + repeatedName);

        this.repeatedName = repeatedName;
        this.tagType = tagType;
    }

    public String getRepeatedName() {
        return repeatedName;
    }

    public TagType getTagType() {
        return tagType;
    }

}
