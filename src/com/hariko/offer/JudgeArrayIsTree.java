package com.hariko.offer;

public class JudgeArrayIsTree {

    public static void main(String[] args) {

    }

    //问题:判断一个数组是不是某二叉树的后续便利结果
    public static void alg1(int[] array) {
        if (null == array || array.length == 0) {
            return;
        }

        int root = array[array.length - 1]; //因为是后续遍历,所以数组最后一个是树的根结点

        //第一步,先找到第一个大于root结点的位置,就找到了左右子树的分界点
        int index = 0;
        while (index < array.length) {
            if (array[index] > root) {
                break;
            }
            index++;
        }

        //右子树的下标
        int rightIndex = index;
        //判断分界点后面的是否全部大于root

        boolean right = true;
        while (rightIndex < array.length - 1) {//因为最后一个数是root小于它
            if (array[rightIndex] < root) {
                right = false;
                break;
            }
        }


    }
}
