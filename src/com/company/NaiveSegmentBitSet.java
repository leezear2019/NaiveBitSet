//package com.company;
//
//public class NaiveSegmentBitSet extends NaiveSparseBitSet {
//    int[] index;
//    int startIndex;
//    int endIndex;
//
//    // 高位和低位的掩码
//    long endMask;
//    long startMask;
//
//    NaiveSegmentBitSet(int s, int nbits) {
//        super(s, nbits);
//        this.startIndex = s;
//        this.endIndex = s + nbits;
//        this.endMask = WORD_MASK >>> endIndex;
//        this.startMask = WORD_MASK << startIndex;
//        this.andMask[0] = ~startIndex;
//        this.andMask[longSize - 1] = ~endIndex;
//    }
//}
