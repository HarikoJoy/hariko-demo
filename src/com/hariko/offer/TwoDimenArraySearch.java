package com.hariko.offer;

public class TwoDimenArraySearch {

    public static void main(String[] args) {

    }

    //问题:二维数组查找的问题
    //1.二维数组中包含要查找的数字(例如: 最大值、最小值、中间值)
    //2.二维数组中不包含要查找的数字,会有如下三种情况:
    //2.1.小于数组中的最小值
    //2.2.大于数组中的最大值
    //2.3.在数组中不存在
    //3.特殊测试,空指针
    public static void alg1(int[][] orgArray, int orgNum) {
        if (orgArray.length == 0) {
            return;
        }

        //从右上角开始找
        int row = 0;
        int col = orgArray[0].length - 1;

        while (row <= orgArray.length - 1 && col >= 0) {
            if (orgArray[row][col] > orgNum) {
                col--;
            } else if (orgArray[row][col] < orgNum) {
                row++;
            } else {

            }
        }
    }
}
