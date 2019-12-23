package com.hariko.alg.find;

public class BinarySearchAlg {

    public static void main(String[] args) {
        int[] array = {1, 3, 8, 11, 13, 19};
        System.out.println(binarySearch(array, 1));
    }

    public static int binarySearch(int[] array, int value){
        int left = 0;
        int right = array.length - 1;

        while (left <= right){
            int mid = (left + right) >> 1;

            if(array[mid] == value){
                return mid;
            }else if(array[mid] > value){
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }

        return -1;
    }
}
