package com.company;

import gnu.trove.list.array.TIntArrayList;
import gnu.trove.set.TIntSet;

public class NaiveSparseBitSet {
    // 用于and运算的mask外侧环以1
    long[] andMask;
    // 用于or运算的mask外侧环以0
    long[] orMask;
    long[] words;
    //    private ArrayList<Integer> tmpList = new ArrayList<>();
    //    private TIntSet
//    private TIntSet
    private TIntArrayList tmpIndex = new TIntArrayList();
//    private TIntArrayList  = new TIntArrayList();
    int bitSize;
    int longSize;


    protected final static int ADDRESS_BITS_PER_WORD = 6;
    protected final static int BITS_PER_WORD = 1 << ADDRESS_BITS_PER_WORD;
    protected final static int BIT_INDEX_MASK = BITS_PER_WORD - 1;
    /* Used to shift left or right for a partial word mask */
    protected static final long WORD_MASK = 0xffffffffffffffffL;
    protected static final long MOD_MASK = 0x3fL;
    protected static final int MOD_MASK_INT = 0x3f;

    NaiveSparseBitSet() {

    }

    NaiveSparseBitSet(int s, int nbits) {
        this.bitSize = nbits;
        this.longSize = wordIndex(nbits - 1) + 1;
        words = new long[longSize];

//        this.andMask = new long[longSize];
//        this.orMask = new long[longSize];
    }

    public void add(int a) {
        tmpIndex.add(a);
    }

    public void finallize() {
        tmpIndex.sort();
    }

    protected static int wordIndex(int bitIndex) {
        return bitIndex >> ADDRESS_BITS_PER_WORD;
    }

    protected static int wordOffset(int bitIndex) {
        return bitIndex & MOD_MASK_INT;
    }
}
