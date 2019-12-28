package com.hariko.offer;

public class ReplaceBlank {

    public static void main(String[] args) {

    }

    //问题:请实现一个算法,把字符串中所有的空格转换为%20
    //例如: we are happy 转换为 we%20are%20happy
    public static void alg1(char[] orgCharArray) {
        if (null == orgCharArray || 0 == orgCharArray.length) {
            return;
        }

        int orgLen = orgCharArray.length; //用来记录原字符串的长度
        int blankLen = 0; //记录出现的空格的个数
        int validLen = 0; //记录有效占用的长度
        int index = 0;

        while (index != orgLen && orgCharArray[index] != '-') {
            validLen++;
            if (orgCharArray[index] == ' ') {
                blankLen++;
            }
            index++;
        }

        int detLen = validLen + 3 * blankLen;
        if (detLen > orgLen) {
            return;
        }

        int validIndex = validLen;
        int blankIndex = orgLen;
        while (validIndex >= 0 && validIndex <= blankIndex) {
            if (orgCharArray[validIndex] != '-') {//当不是-时,要完成复制任务
                orgCharArray[blankIndex] = orgCharArray[validIndex];
                blankIndex--;
                validIndex--;
            } else {
                orgCharArray[blankIndex--] = '0';
                orgCharArray[blankIndex--] = '2';
                orgCharArray[blankIndex--] = '%';
                validIndex--;
            }
        }
    }
}
