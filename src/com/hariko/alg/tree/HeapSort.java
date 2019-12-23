package com.hariko.alg.tree;

public class HeapSort {

    public static void main(String[] args) {
        int[] array = {4, 6, 8, 5, 9};
    }

    public void heapSort(int[] array){
        for(int i = array.length / 2 - 1; i >= 0; i--){
            adjust(array, i, array.length);
        }

        for(int j = array.length - 1; j > 0; j--){
            int temp = array[j];
            array[j] = array[0];
            array[0] = temp;
            adjust(array, 0, j);
        }
    }

    //将一个数组(二叉树)调整成一个大顶堆

    /**
     * 完成以i对应的非叶子结点的数调整成大顶堆
     * i = 1 -> {4, 9, 8, 5, 6}
     *
     *
     * @param array
     * @param i    表示非叶子结点在数组中的位置
     * @param length 表示对多少个元素继续调整
     */
    public void adjust(int[] array, int i, int length){
        int temp = array[i]; // 先取出当前元素的值,保有在临时变量
        //i这个非叶子结点的左结点
        for(int k = i * 2 + 1; k < length; k = k * 2 + 1){
            //说明左子结点小于右子结点的值
            if(k + 1 < length && array[k] < array[k + 1]){
                k++;
            }
            //如果子结点大于父结点
            if(array[k] > temp){
                array[i] = array[k];
                i = k;
            }else {
                //从左到右,从下向上
                break;
            }
        }

        array[i] = temp;
    }
}
