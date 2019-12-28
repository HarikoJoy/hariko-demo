package com.hariko.alg.kmp;

import java.util.Arrays;

public class StrMatchAlg {

    public static void main(String[] args) {
        String str1 = "BBC ABCDAB ABCDABCDABDE";
        String str2 = "ABCDAB";
        System.out.println("暴力搜索结果为: " + violenceMath(str1, str2));
        System.out.println("KMP匹配表结果为: " + Arrays.toString(getPartMatchTable(str2)));
        System.out.println("KMP搜索结果为: " + kmpMatch(str1, str2));
    }

    /**
     * 字符串: bread
     * 前缀:  b, br, bre, brea
     * 后缀:  read, ead, ad, d
     * 例如: a 的前缀和后缀都为空集,共有元素的长度为0
     *      ab 的前缀为a,后缀为b,共有元素的长度为0
     *      abc 的前缀为a,ab,后缀为bc,c共有元素的长度为0
     *      abcda 的前缀为a,ab,abc,abcd后缀为bcda,cda,da,a共有元素为a共有元素的长度为1
     * 因此得出:
     * 搜索词:      A B C D A B D
     * 部分匹配值:  0 0 0 0 1 2 0
     */
    public static int kmpMatch(String str1, String str2){
        int[] mathTable = getPartMatchTable(str2);

        //i指向str1,j指向str2
        for(int i = 0, j = 0; i < str1.length(); i++){
            //需要考虑str1.charAt(i) == str2.charAt(j)不相等的情况下
            while (j > 0 && str1.charAt(i) != str2.charAt(j)){
                //根据匹配表向前回溯
                j = mathTable[j - 1];
            }

            if(str1.charAt(i) == str2.charAt(j)){
                j++;
            }

            if(j == str2.length()){
                return i - j + 1;
            }
        }

        return -1;
    }

    //获取字符串的部分匹配表
    //AA --> [0, 1]
    //AAA --> [0, 1, 2]
    //AAAB --> [0, 1, 2, 0]
    //ABCDABD --> [0, 0, 0, 0, 1, 2, 0]
    public static int[] getPartMatchTable(String str){
        int[] matchTable = new int[str.length()];

        //如果str的长度为1,它的部分匹配就是0
        matchTable[0] = 0;
        for(int i = 1, j = 0; i < str.length(); i++){
            //当str.charAt(i) == str.charAt(j)这个条件不满足时,需要从matchTable获取新的值
            //只到我们发现有 str.charAt(i) == str.charAt(j) 这个条件成立时
            //kmp算法的核心
            while (j > 0 && str.charAt(i) != str.charAt(j)){
                j = matchTable[j - 1];
            }

            //当这个条件满足时,部分匹配值就加1
            if(str.charAt(i) == str.charAt(j)){
                j++;
            }
            matchTable[i] = j;
        }

        return matchTable;
    }

    //暴力匹配
    public static int violenceMath(String str1, String str2){
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();

        int s1len = s1.length;
        int s2len = s2.length;

        int i = 0; //指向s1
        int j = 0; //指向s2

        while (i < s1len && j < s2len){
            if(s1[i] == s2[j]){ //匹配OK
                i++;
                j++;
            }else{
                i = i - (j - 1);
                j = 0;
            }
        }

        if(j == s2len){
            i = i - j;
            return i;
        }

        return -1;
    }
}
