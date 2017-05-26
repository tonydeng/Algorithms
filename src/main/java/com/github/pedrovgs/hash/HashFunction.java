package com.github.pedrovgs.hash;

/**
 * Created by tonydeng on 2017/5/23.
 */
public interface HashFunction {
    long hash(String value);

    default long getLong(final byte[] array, final int offset){
        long value = 0;
        for (int i = 0; i < 8; i++) {
            value = ((value << 8) | (array[offset + i] & 0xFF));
        }
        return Math.abs(value);
    }
}
