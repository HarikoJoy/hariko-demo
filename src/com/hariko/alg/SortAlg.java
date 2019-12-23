package com.hariko.alg;


import java.util.Arrays;

public class SortAlg {

    public static void main(String[] args) {
        int[] array = {3, 9, -1, -1, 10, -20, 11, 5, 44, 12, -222};

        System.out.println("排序前:");
        printArray(array);
        //bubbleSort(array);
        //selectSort(array);
        //insertSort(array);
        //quickSort(array, 0, array.length - 1);
        //shellSortByMove(array);
        //shellSortBySwap(array);
        mergeSort(array, 0, array.length - 1);
        System.out.println("排序后:");
        printArray(array);
    }

    public static void printArray(int[] array){
        for(int i = 0; i < array.length; i++){
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public static void radixSort(int[] array){
        //第1轮(针对每个元素的个位进行排序处理)
        //第2轮(针对每个元素的十位进行排序处理)
        //.........

        //根据前面的推导过程,我们可以找出算数规则,找出数组中的最大的一个数,计算出循环规律
        int max = Arrays.stream(array).max().getAsInt();
        //得到最大的数的位数
        int maxRadix = (max + "").length();

        //1.二维数组包含10个一维数组
        //2.空间换时间
        int[][] bucket = new int[10][array.length];

        //为了记录每个桶中的数据,定义一个一维数组记录各个桶每次放入的数据个数
        int[] bucketCapacity = new int[10];

        //根据数组中最大的数求出循环次数,后面每次循环处理
        for(int radix = 0, n = 1; radix < maxRadix; radix++, n *= 10) {
            for (int j = 0; j < array.length; j++) {
                //取出每个元素的个位数,十位数,百位数
                int digitfElement = array[j] / n % 10;
                bucket[digitfElement][bucketCapacity[digitfElement]] = array[j];
                bucketCapacity[digitfElement] += 1;
            }

            //按照上面排好的桶的顺序,依次取出放入原有的数组中
            int index = 0;
            for (int k = 0; k < 10; k++) {
                //如果桶中有数据,我们才放入原有数组中
                if (bucketCapacity[k] != 0) {
                    //遍历第k个一维数组
                    for (int l = 0; l < bucketCapacity[k]; l++) {
                        array[index++] = bucket[k][l];
                    }
                }
                //清空一维数组保存的个数,以便进行下一次遍历
                bucketCapacity[k] = 0;
            }
        }
    }

    public static void mergeSort(int[] array, int start, int end){
        if(start >= end){
            return;
        }

        int mid = (start + end) / 2;
        mergeSort(array, start, mid);
        mergeSort(array, mid + 1, end);
        merge(array, start, mid, end);
    }

    private static void merge(int[] array, int start, int mid, int end){
        System.out.println("start = " + start + ", mid = " + mid + ", end = " + end);
        int[] supportArray = new int[array.length];
        int indexLeft = start;
        int indexRight = mid + 1;
        int indexMerge = start;

        while(indexLeft <= mid && indexRight <= end){
            boolean isRightMove = false;
            boolean isLeftMove = false;

            if(array[indexLeft] > array[indexRight]){
                supportArray[indexMerge++] = array[indexRight];
                isRightMove = true;
            }
            if(array[indexLeft] < array[indexRight]){
                supportArray[indexMerge++] = array[indexLeft];
                isLeftMove = true;
            }
            if(array[indexLeft] == array[indexRight]){
                supportArray[indexMerge++] = array[indexLeft++];
                supportArray[indexMerge++] = array[indexRight++];
            }

            if(isRightMove){
                indexRight += 1;
            }

            if(isLeftMove){
                indexLeft += 1;
            }
        }

        while(indexLeft <= mid){
            supportArray[indexMerge++] = array[indexLeft++];
        }
        while (indexRight <= end){
            supportArray[indexMerge++] = array[indexRight++];
        }

        for(int i = start; i <= end; i++){
            array[i] = supportArray[i];
        }
        printArray(array);
    }

    public static void quickSort(int[] array, int start, int end){
        int partion = doQuickSort(array, start, end);

        if(partion == 0 || partion == array.length - 1 || partion == -1){
            return;
        }
        quickSort(array, start, partion - 1);
        quickSort(array, partion + 1, end);
    }

    private static int doQuickSort(int[] array, int low, int high){
        System.out.println("low is:" + low + ",high is:" + high);
        if(low == high || low > high){
            return -1;
        }
        int base = array[low];

        while(high > low){
            while (high > low && array[high] > base){
                high--;
            }

            while(low < high && array[low] < base){
                low ++;
            }

            int temp = array[high];
            array[high] = array[low];
            array[low] = temp;
        }

        array[low] = base;

        printArray(array);

        return low;
    }

    public static void insertSort(int[] array){
        for(int i = 0; i < array.length; i++) {
            for (int j = 0; j < i; j++) {
                if(array[j] <= array[i]){
                    int temp = array[j];
                    array[j] = array[i];
                    array[i] = temp;
                }
            }
        }
    }

    public static void selectSort(int[] array){
        for(int i = 0; i < array.length; i++) {
            int min = array[i];
            for (int j = i; j < array.length; j++) {
                if(array[j] <= min){
                    int temp = array[j];
                    array[j] = array[i];
                    array[i] = temp;
                }
            }
        }
    }

    public static void bubbleSort(int[] array){
        for(int i = 0; i < array.length; i++){
            int swapCount = 0;
            for(int j = 0; j < array.length - i - 1; j++){
                if(array[j] >= array[j + 1]){
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swapCount ++;
                }
            }
            if(swapCount == 0){
                //后续已经没有交换了,不需要在排序了
                break;
            }
        }
    }

    public static void shellSortBySwap(int[] array){
        for(int gap = array.length / 2; gap > 0; gap /= 2) {
            //第1轮排序,把10个数据分成5组
            for (int i = gap; i < array.length; i++) {
                //遍历各组中的各个元素,步长为5
                for (int j = i - gap; j >= 0; j -= gap) {
                    //如果当前元素大于加上步长为5的那个元素,说明交换
                    if (array[j] > array[j + gap]) {
                        int temp = array[j];
                        array[j] = array[j + gap];
                        array[j + gap] = temp;
                    }
                }
            }
        }
    }

    public static void shellSortByMove(int[] array){
        for(int gap = array.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < array.length; i++) {
                int j = i;
                int temp = array[j];
                if(array[j] < array[j - gap]){
                    while (j - gap >= 0 && temp < array[j - gap]){
                        array[j] = array[j - gap];
                        j -= gap;
                    }
                    array[j] = temp;
                }
            }
        }
    }
}
