package com.hariko.alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BinarySearchAlg {
    //数组中同样的数有多个,用个list保存
    public static final List<Integer> location = new ArrayList<>();
    public static int maxSize = 20;
    public static void main(String[] args) {
        //int[] array = {-111, -22, -22, -22, -22, 0, 1, 2, 3, 3, 44, 333, 33333};
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};

        System.out.println(Arrays.toString(array));
        //binarySearch(array, -22, 0, array.length - 1);
        int ret = insertSearch(array, 6, 0, array.length -1);
        System.out.println(ret);
        //System.out.println(Arrays.toString(location.toArray()));
    }

    public static int fibSearch(int[] array, int value, int start, int end){
        //mid = low + f(k - 1) -1
        //f(k) = f(k - 1) + f(k - 2)  ->  f(k) - 1 = (f(k - 1) - 1) + (f(k - 2) - 1) + 1
        //但是顺序表的长度n不一定刚好等于f(k) - 1,所以需要将原来的顺序表的长度n增加到f(k) - 1,这里的k值只要使
        //f(k) - 1恰好大于等于n即可

        //因为要使用到fibonacci数列,先要构造一个数列
        int k = 0;//fib数列分割数值的下标,公式中的k
        int mid = 0;
        int[] fib = generateFib(maxSize);
        //获取fib数列的下标的k
        while(end > fib[k] - 1){
            k++;
        }
        //因为f(k)这个值可能大于,可能小于,不足部分用0填充
        int[] temp = Arrays.copyOf(array, fib[k]);
        //temp = {1, 8, 10, 89, 1000, 1234, 0, 0, 0}
        //temp = {1, 8, 10, 89, 1000, 1234, 1234, 1234, 1234}
        for(int i = end + 1; i < temp.length; i++){
            temp[i] = array[end];
        }

        while(start <= end){
            mid = start + fib[k - 1] - 1;
            if(value < temp[mid]){
                end = mid;
                //1.全部元素 = 前面元素 + 后面元素
                //2.f[k] = f[k - 1] + f[k - 2]
                //因为前面有f[k - 1]个元素,可以继续拆分成f[k - 1] = f[k - 2] + f[k - 3]
                k--;
            }else if(value > temp[mid]){
                start = mid + 1;
                k -= 2;
            }else{
                if(mid <= end){
                    return mid;
                }else{
                    return end;
                }
            }
        }

        return -1;
    }

    public static int[] generateFib(int size){
        int[] fib = new int[size];
        fib[0] = 1;
        fib[1] = 1;
        for(int i = 2; i < size; i++){
            fib[i] = fib[i - 1] + fib[i - 2];
        }
        return fib;
    }

    //插值查找只是和二分查找的公式不一样
    public static int insertSearch(int[] array, int value, int start, int end){
        //数组 arr = [1, 2, 3, ..., 100]
        //int mid = left + (right - left) * (findVal -arr[left]) / (arr[right] - arr[left])
        //int mid = 0 + (99 - 0) * (1 - 1) = 0 + 99 * 0 / 99 = 0

        if(start > end || value < array[0] || value > array[array.length - 1]){
            return -1;
        }

        int mid = start + (end - start) * (value - array[start]) / (array[end] - array[start]);

        if(array[mid] == value){
            return mid;
        }

        if(array[mid] > value){
            return insertSearch(array, value, start, mid);
        }

        if(array[mid] < value){
            return insertSearch(array, value, mid + 1, end);
        }

        return -1;
    }

    public static void binarySearch(int[] array, int value, int start, int end){
        int mid = (start + end) >> 1;

        if(array[mid] > value){
            binarySearch(array, value, start, mid);
            return;
        }

        if(array[mid] < value){
            binarySearch(array, value, mid + 1, end);
            return;
        }

        if(array[mid] == value){
            location.add(mid);
        }

        int left = mid - 1;
        while (array[left] == value){
            location.add(left--);
        }

        int right = mid + 1;
        while (array[right] == value){
            location.add(right++);
        }
    }
}
