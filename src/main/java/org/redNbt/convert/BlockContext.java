package org.redNbt.convert;

import org.objectweb.asm.Label;

/**
 * @author Bug[3050429487@qq.com]
 */
public class BlockContext {

    final Label startLabel;
    final Label endLabel;

    int localSize;

    BlockContext(Label startLabel, Label endLabel, int localSize) {
        this.startLabel = startLabel;
        this.endLabel = endLabel;
        this.localSize = localSize;
    }

    int defVar(String desc) {
        int index = localSize;
        switch (desc.charAt(0)) {
            case 'J':
            case 'D':
                localSize += 1;break;
            default:
                localSize += 2;
        }

        return index;
    }

}
