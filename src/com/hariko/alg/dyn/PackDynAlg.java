package com.hariko.alg.dyn;

//动态规划求解背包问题
public class PackDynAlg {
    public static void main(String[] args) {
        int[] weightArray = {2, 2, 6, 5, 4};
        int[] valueArray = {6, 3, 5, 4, 6};

        int[][] packArray = pack(weightArray, valueArray, 10);
        printFinalResult(packArray, weightArray, 10);
    }

    public static int[][] pack(int[] weightArray, int[] valueArray, int capacity){
        int[][] packArray = new int[valueArray.length + 1][capacity + 1];
        int goodNumber = valueArray.length;

        for(int i = 0; i <= goodNumber; i++){
            packArray[i][0] = 0;
        }
        for(int j = 0; j <= capacity; j++){
            packArray[0][j] = 0;
        }

        for(int i = 1; i <= goodNumber; i++){
            for(int j = 1; j <= capacity; j++){
                //第i件物品大于背包的承重
                if(weightArray[i - 1] > j){
                    packArray[i][j] = packArray[i - 1][j];
                    continue;
                }

                int remainValue = valueArray[i - 1] + packArray[i - 1][j - weightArray[i - 1]];
                if(packArray[i - 1][j] < remainValue){
                    packArray[i][j] = remainValue;
                }else{
                    packArray[i][j] = packArray[i - 1][j];
                }
            }
        }

        return packArray;
    }

    public static void printFinalResult(int[][] packArray, int[] weightArray, int capacity){
        int goodNumber = weightArray.length;
        int j = capacity;
        for(int i = goodNumber; i > 0; i--){
            if(packArray[i][j] > packArray[i - 1][j]){
                System.out.println(i);
                j = j - weightArray[i - 1];
                if(j < 0){
                    break;
                }
            }
        }
    }


}
