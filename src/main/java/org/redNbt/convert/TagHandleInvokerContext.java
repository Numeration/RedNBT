package org.redNbt.convert;

/**
 * @author Bug[3050429487@qq.com]
 */
public class TagHandleInvokerContext {

    final String owner;
    final String invokerStateFieldName;
    final int invokerStateBit;

    final String tagEntryName;
    final String tagEntryDesc;
    final String tagEntryFieldName;

    final String hootArrayFieldName;

    final String tagHandlerName;
    final String tagHandlerDesc;

    public TagHandleInvokerContext(String owner,
                                   String invokerStateFieldName, int invokerStateBit,
                                   String tagEntryName, String tagEntryDesc, String tagEntryFieldName,
                                   String hootArrayFieldName,
                                   String tagHandlerName, String tagHandlerDesc)
    {
        this.owner = owner;
        this.invokerStateFieldName = invokerStateFieldName;
        this.invokerStateBit = invokerStateBit;
        this.tagEntryName = tagEntryName;
        this.tagEntryDesc = tagEntryDesc;
        this.tagEntryFieldName = tagEntryFieldName;
        this.hootArrayFieldName = hootArrayFieldName;
        this.tagHandlerName = tagHandlerName;
        this.tagHandlerDesc = tagHandlerDesc;
    }
}
