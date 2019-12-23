package com.hariko.alg.greedy;

import java.util.Arrays;

public class PackGreedyAlg {

    public static void main(String[] args) {
        int[] weightArray = {2, 2, 6, 5, 4};
        int[] valueArray = {6, 3, 5, 4, 6};

        greedy(150, weightArray, valueArray);

    }

    public static void greedy(int capacity, int[] weightArray, int[] valueArray){
        int goodNumber = weightArray.length;

        double[] perfArray = new double[goodNumber];
        //初始化性价比数组
        for(int i = 0; i < goodNumber; i++){
            perfArray[i] = (double)valueArray[i] / weightArray[i];
        }

        for(int i = 0; i < goodNumber - 1; i ++){
            double temp = perfArray[i];
            for(int j = i + 1; j < goodNumber; j++){
                if(perfArray[j] > temp){
                    temp = perfArray[i];
                    perfArray[i] = perfArray[j];
                    perfArray[j] = temp;
                }
            }
        }

        System.out.println("性价比数组: " + Arrays.toString(perfArray));

    }
}
