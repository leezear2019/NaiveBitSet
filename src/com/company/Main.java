package com.company;

import gnu.trove.list.array.TIntArrayList;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;

import javax.swing.*;
import java.util.BitSet;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        // write your code here
        BitSet bb = new BitSet(10);
//        var a = 1 ^ 0;
//        b.isEmpty(1, false);
//        b.nextSetBit(0);
//        System.out.println(a);
//        int a = 0xa3e32b39;
//        int b = 28;
//        int start = 3;
//        int len = 5;
//        int kAllOne = 0xffffffff;
//        int INT_SIZE = 32;
//        int os1 = start + len;
//        int os2 = INT_SIZE - start;
//
//        int u = (kAllOne << os1) | (kAllOne >>> os2);
//        int u2 = ~(~(kAllOne << len) << start);
//        int r = a & u | (b << start);
////        a = a & u | (b << start);
//        System.out.println(Integer.toBinaryString(a));
//        System.out.println(Integer.toBinaryString(b));
//        System.out.println(Integer.toBinaryString(u));
//        System.out.println(Integer.toBinaryString(u2));
//        System.out.println(Integer.toBinaryString(r));
//        System.out.println(Integer.toBinaryString(a));
//        System.out.println(Integer.toBinaryString(kAllOne));
//
//        System.out.println("naiveAnd测试");
//        // 掩码，先让开b的长度，装入b，再移动start的长度，装入填充
//        int c = ((kAllOne << len) | b) << start | (~(kAllOne << start));
//        System.out.println(Integer.toBinaryString(a));
//        a &= ((kAllOne << len) | b) << start | (~(kAllOne << start));
//        System.out.println(Integer.toBinaryString(a));
//        int d = b >> 66;
//        System.out.println(Integer.toBinaryString(d));
        NaiveBitSet b;
        int numBit = 100;
        b = new NaiveBitSet(numBit);
        TIntArrayList n = new TIntArrayList();

        for (int i = 0; i < numBit; i += 3) {
            System.out.println(i);
            b.set(i);
            n.add(i);
        }


        System.out.println(b);
        System.out.println(n);
        n.shuffle(new Random(25));
        System.out.println(n);

        TIntSet s = new TIntHashSet(n);

        n.sort();
        System.out.println(n);

        System.out.println(s);

    }
}
