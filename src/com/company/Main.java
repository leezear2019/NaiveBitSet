package com.company;

import java.util.BitSet;

public class Main {

    public static void main(String[] args) {
        // write your code here
//        BitSet b = new BitSet(10);
//        var a = 1 ^ 0;
//        b.isEmpty(1, false);
//        b.nextSetBit(0);
//        System.out.println(a);
        int a = 0xa3e32b39;
        int b = 28;
        int start = 3;
        int len = 5;
        int kAllOne = 0xffffffff;
        int INT_SIZE = 32;
        int os1 = start + len;
        int os2 = INT_SIZE - start;

        int u = (kAllOne << os1) | (kAllOne >>> os2);
        int u2 = ~(~(kAllOne << len) << start);
        int r = a & u | (b << start);
        a = a & u | (b << start);
        System.out.println(Integer.toBinaryString(a));
        System.out.println(Integer.toBinaryString(b));
//        System.out.println(os1 + ", " + os2);
//        System.out.println(Integer.toBinaryString(0xffffffff >>> 29));
//        System.out.println(Integer.toBinaryString(u1));
//        System.out.println(Integer.toBinaryString(u2));
        System.out.println(Integer.toBinaryString(u));
        System.out.println(Integer.toBinaryString(u2));
        System.out.println(Integer.toBinaryString(r));
        System.out.println(Integer.toBinaryString(a));
        System.out.println(Integer.toBinaryString(kAllOne));
    }
}
