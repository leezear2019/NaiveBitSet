package com.company;

import java.util.BitSet;

public class NaiveBitSet {

    private long[] words;
    private int longSize;
    private int bitSize;
    private int limit;
    private long lastMask;

    private final static int ADDRESS_BITS_PER_WORD = 6;
    private final static int BITS_PER_WORD = 1 << ADDRESS_BITS_PER_WORD;
    private final static int BIT_INDEX_MASK = BITS_PER_WORD - 1;
    /* Used to shift left or right for a partial word mask */
    private static final long WORD_MASK = 0xffffffffffffffffL;
    private static final long MOD_MASK = 0x3fL;
    private static final int MOD_MASK_INT = 0x3f;

    NaiveBitSet(int nbits) {
        this.bitSize = nbits;
        longSize = wordIndex(nbits - 1) + 1;
        this.limit = nbits % BITS_PER_WORD;
        this.lastMask = WORD_MASK >> (BITS_PER_WORD - limit);
        this.words = new long[longSize];
    }

    private static int wordIndex(int bitIndex) {
        return bitIndex >> ADDRESS_BITS_PER_WORD;
    }

    private static int wordOffset(int bitIndex) {
        return bitIndex & MOD_MASK_INT;
    }

    int longSize() {
        return longSize;
    }

    int bitSize() {
        return bitSize;
    }

    void flip() {
        for (int i = 0; i < longSize; ++i) {
            words[i] = ~words[i];
        }
        words[longSize - 1] &= lastMask;
    }

    void set(int bitIndex) {
        this.words[wordIndex(bitIndex)] |= 1L << bitIndex;
    }

    void set(NaiveBitSet s) {
        for (int i = 0; i < longSize; ++i) {
            this.words[i] = s.words[i];
        }
    }

    void set(int startIndex, NaiveBitSet b) {
//        for(int i = wordIndex(startIndex);)
    }

    void naiveSet(int s, NaiveBitSet nbs) {
        int a = wordIndex(s);
        int b = wordOffset(s);
        // 先确定怎么偏移
        if (nbs.longSize == 1) {
            // 即 nbs.longSize =1
            if ((b + nbs.bitSize) < BITS_PER_WORD) {
                // 如果偏移量和整个bitset长度相加之后仍小于64，直接做
                // 取反码
                // long rmask = (WORD_MASK << (nbs.bitSize - b)) | (WORD_MASK >>> (BITS_PER_WORD - b));
                // 取掩码, 先左移取反再左移，移之后是：111...10..01...11这样的形式
//                long rMask = (~(~(WORD_MASK << nbs.bitSize) << b));
                this.words[a] = this.words[a] & (~(~(WORD_MASK << nbs.bitSize) << b)) | (nbs.words[0] << b);
            } else {
                int offset1 = nbs.bitSize + b - BITS_PER_WORD;
//                long rMask =
//                int offset2 = BITS_PER_WORD - offset1;
                // 如果偏移量和整个bitset长度相加之后仍大于等于64，则需要用到this.words的下一个
                this.words[a] = ((~(WORD_MASK << b)) & this.words[a]) | (nbs.words[0] << b);
                this.words[a + 1] = (this.words[a + 1] & (WORD_MASK << offset1)) | (nbs.words[0] >> offset1);
            }
        } else {
            int endIndex = s + nbs.bitSize;
            int c = wordIndex(endIndex);
            int d = wordOffset(endIndex);
            int offset1 = nbs.bitSize + s - BITS_PER_WORD;
            // 先不计算最后一个影响位
            for (int i = 0, len = c - 1; i < len; ++i) {
                int j = a + i;
                this.words[j] = ((~(WORD_MASK << s)) & this.words[j]) | (nbs.words[i] << s);
                this.words[j + 1] = (this.words[j + 1] & (WORD_MASK << offset1)) | (nbs.words[i] >> offset1);
            }
        }
//        for (int i = 0, len = s.longSize; i < len; ++i) {
//            int j = b + i;
//            // 清空this中待设定的的部分，
//            // 一般要操作两个相邻的 this.words
//            // 先
//            this.words[j] = ((~(WORD_MASK << a) & this.words[a]) | (s.words[0] << a));
//            this.words[j + 1] = ((WORD_MASK << a) & this.words[j + 1]) | (s.words[0] >> BITS_PER_WORD - a);
//
////            this.words[j] = ~((s.words[i] << startBitIndex) ^ (WORD_MASK << startBitIndex));
////            this.words[j + 1] &=
////                    pre = s.words[i] << startBitIndex;
////            next = s.words[i] & (WORD_MASK >> startBitIndex);
//
//        }
    }

    void naiveAnd(int s, NaiveBitSet nbs) {
        int a =  (s);
        int b = wordOffset(s);
        // 先确定怎么偏移
        if (nbs.longSize == 1) {
            // 即 nbs.longSize =1
            if ((b + nbs.bitSize) < BITS_PER_WORD) {
                // 如果偏移量和整个bitset长度相加之后仍小于64，直接做
                // 取反码
                // long rmask = (WORD_MASK << (nbs.bitSize - b)) | (WORD_MASK >>> (BITS_PER_WORD - b));
                // 取掩码, 先左移取反再左移，移之后是：111...10..01...11这样的形式
//                long rMask = (~(~(WORD_MASK << nbs.bitSize) << b));
                this.words[a] = this.words[a] & (~(~(WORD_MASK << nbs.bitSize) << b)) | (nbs.words[0] << b);
            } else {
                int offset1 = nbs.bitSize + b - BITS_PER_WORD;
//                long rMask =
//                int offset2 = BITS_PER_WORD - offset1;
                // 如果偏移量和整个bitset长度相加之后仍大于等于64，则需要用到this.words的下一个
                this.words[a] = ((~(WORD_MASK << b)) & this.words[a]) | (nbs.words[0] << b);
                this.words[a + 1] = (this.words[a + 1] & (WORD_MASK << offset1)) | (nbs.words[0] >> offset1);
            }
        } else {
            int endIndex = s + nbs.bitSize;
            int c = wordIndex(endIndex);
            int d = wordOffset(endIndex);
            int offset1 = nbs.bitSize + s - BITS_PER_WORD;
            // 先不计算最后一个影响位
            for (int i = 0, len = c - 1; i < len; ++i) {
                int j = a + i;
                this.words[j] = ((~(WORD_MASK << s)) & this.words[j]) | (nbs.words[i] << s);
                this.words[j + 1] = (this.words[j + 1] & (WORD_MASK << offset1)) | (nbs.words[i] >> offset1);
            }
        }
    }

    void reset(int bitIndex) {
        this.words[wordIndex(bitIndex)] &= ~(1L << bitIndex);
    }

    boolean isEmpty() {
        for (int i = 0; i < longSize; ++i) {
            if (this.words[i] != 0L) {
                return false;
            }
        }
        return true;
    }

    boolean check(int bitIndex) {
        int wordIndex = wordIndex(bitIndex);
        return wordIndex < longSize && (this.words[wordIndex] & 1L << bitIndex) != 0L;

    }

    void and(NaiveBitSet s) {
        for (int i = 0; i < longSize; ++i) {
            this.words[i] &= s.words[i];
        }
    }

    void or(NaiveBitSet s) {
        for (int i = 0; i < longSize; ++i) {
            this.words[i] |= s.words[i];
        }
    }

    int nextOneBit(int fromIndex) {
        if (fromIndex < 0) {
            throw new IndexOutOfBoundsException("fromIndex < 0: " + fromIndex);
        } else {
            int u = wordIndex(fromIndex);
            if (u >= this.longSize) {
                return -1;
            } else {
                long word;
                for (word = this.words[u] & -1L << fromIndex; word == 0L; word = this.words[u]) {
                    ++u;
                    if (u == this.longSize) {
                        return -1;
                    }
                }

                return u * 64 + Long.numberOfTrailingZeros(word);
            }
        }
    }

    // 从某个启始位置与并检查计算结果
    public static boolean naiveAndEmpty(BitSet d, BitSet s, int startBitIndex) {
        return false;
    }

    public void and(NaiveBitSet a, NaiveBitSet b) {
        for (int i = 0; i < this.longSize; ++i) {
            this.words[i] &= (a.words[i] & b.words[i]);
        }
    }

    public final static void and(NaiveBitSet res, NaiveBitSet a, NaiveBitSet b) {
        for (int i = 0, len = res.longSize; i < len; ++i) {
            res.words[i] = a.words[i] & b.words[i];
        }
    }

    public final static void and(NaiveBitSet res, NaiveBitSet a, NaiveBitSet b, NaiveBitSet c, NaiveBitSet d) {
        for (int i = 0, len = res.longSize; i < len; ++i) {
            res.words[i] = a.words[i] & b.words[i] & c.words[i] & d.words[i];
        }
    }

    public final static boolean EmptyAnd(NaiveBitSet a, NaiveBitSet b) {
        for (int i = 0; i < a.longSize; ++i) {
            if ((a.words[i] & b.words[i]) != 0L) {
                return false;
            }
        }
        return true;
    }

    public final static boolean EmptyAnd(NaiveBitSet a, NaiveBitSet b, NaiveBitSet c, NaiveBitSet d) {
        for (int i = 0; i < a.longSize; ++i) {
            if ((a.words[i] & b.words[i] & c.words[i] & d.words[i]) != 0L) {
                return false;
            }
        }
        return true;
    }
}
