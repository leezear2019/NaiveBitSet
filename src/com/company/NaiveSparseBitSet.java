package com.company;

//import gnu.trove.iterator.TIntIterator;
//import gnu.trove.list.array.TIntArrayList;
//import gnu.trove.set.TIntSet;

import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TIntLongHashMap;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;

import java.util.*;

public class NaiveSparseBitSet {
    // 用于and运算的mask外侧环以1
//    long[] andMask;
    // 用于or运算的mask外侧环以0
//    long[] orMask;
    protected int[] index;
    protected long[] words;
    int startIndex;
    int endIndex;
    int minIndex = Integer.MAX_VALUE;
    int maxIndex = Integer.MIN_VALUE;
    //    private ArrayList<Integer> tmpList = new ArrayList<>();
    private Map<Integer, Long> tmp = new TreeMap<>();

    //    int bitSize;
    protected int longSize;

    protected final static int ADDRESS_BITS_PER_WORD = 6;
    protected final static int BITS_PER_WORD = 1 << ADDRESS_BITS_PER_WORD;
    protected final static int BIT_INDEX_MASK = BITS_PER_WORD - 1;
    /* Used to shift left or right for a partial word mask */
    protected static final long WORD_MASK = 0xffffffffffffffffL;
    protected static final long MOD_MASK = 0x3fL;
    protected static final int MOD_MASK_INT = 0x3f;

    NaiveSparseBitSet(int s) {
        startIndex = s;
    }

    public void add(int a) {
//        tmpList.add(a);
        // 获取最大最小值
        minIndex = Integer.min(a, minIndex);
        maxIndex = Integer.max(a, maxIndex);
        // 拿到新地址
        int pos = startIndex + a;
        int k = wordIndex(pos);
        int v = wordOffset(pos);
        if (!tmp.containsKey(k)) {
            // 没有这个k就加一个新Key进去，并初始化数据
            tmp.put(k, 1L << v);
        } else {
            // 有就在原位置上修改这个值
            tmp.put(k, tmp.get(k) | (1L << v));
        }

    }

    // 集合类转数组
    public void complete() {
        longSize = tmp.size();
        index = new int[longSize];
        words = new long[longSize];
        int i = 0;

        for (Map.Entry<Integer, Long> entry : tmp.entrySet()) {
            index[i] = entry.getKey();
            words[i] = entry.getValue();
            ++i;
        }
        // 清除临时数据结构
        tmp.clear();
    }

    protected static int wordIndex(int bitIndex) {
        return bitIndex >> ADDRESS_BITS_PER_WORD;
    }

    protected static int wordOffset(int bitIndex) {
        return bitIndex & MOD_MASK_INT;
    }
}
