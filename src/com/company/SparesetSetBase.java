package com.company;

public class SparesetSetBase {
    // 用于and运算的mask外侧环以1
    long[] andMask;
    // 用于or运算的mask外侧环以0
    long[] orMask;
    int bitSize;
    int longSize;
    int startIndex;
    int endIndex;

    // 高位和低位的掩码
    long endMask;
    long startMask;

    private final static int ADDRESS_BITS_PER_WORD = 6;
    private final static int BITS_PER_WORD = 1 << ADDRESS_BITS_PER_WORD;
    private final static int BIT_INDEX_MASK = BITS_PER_WORD - 1;
    /* Used to shift left or right for a partial word mask */
    private static final long WORD_MASK = 0xffffffffffffffffL;
    private static final long MOD_MASK = 0x3fL;
    private static final int MOD_MASK_INT = 0x3f;

    SparesetSetBase(int s, int nbits) {
        this.bitSize = nbits;
        this.startIndex = s;
        this.endIndex = s + nbits;
        this.longSize = wordIndex(nbits - 1) + 1;
        this.andMask = new long[longSize];
        this.orMask = new long[longSize];
        this.endMask = WORD_MASK >>> endIndex;
        this.startMask = WORD_MASK << startIndex;
        this.andMask[0] = ~startIndex;
        this.andMask[longSize - 1] = ~endIndex;
    }

    private static int wordIndex(int bitIndex) {
        return bitIndex >> ADDRESS_BITS_PER_WORD;
    }

    private static int wordOffset(int bitIndex) {
        return bitIndex & MOD_MASK_INT;
    }
}
