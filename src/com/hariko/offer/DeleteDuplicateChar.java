package com.hariko.offer;

import java.util.Arrays;

public class DeleteDuplicateChar {

    public static void main(String[] args) {
        String orgStr = "abcadfbjkl";

        System.out.println("原始字符串为: " + orgStr);
        String destStrAlg2 = alg2(orgStr.toCharArray());
        System.out.println("第二种算法处理结果为: " + destStrAlg2);

    }

    //设计算法移除字符串中重复的字符,不能使用额外的空间,可以用临时变量,不能额外开辟数组复制
    //以选择排序的方式去处理所有的字符
    public static void alg1(String orgStr) {

    }

    //要求尽可能的快,对空间没有要求
    public static String alg2(char[] charArray) {
        int[] isExistArray = new int[26]; //定义一个长度为26的数组,初始时都为0,表示字母都没有出现过

        for (int i = charArray.length - 1; i >= 0; i--) {
            if (0 == isExistArray[charArray[i] - 'a']) {
                isExistArray[charArray[i] - 'a']++;
            } else {
                charArray[i] = '-';
            }
            System.out.println(Arrays.toString(isExistArray));
        }

        return String.valueOf(charArray);
    }
}
