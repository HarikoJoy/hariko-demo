package com.hariko.alg.greedy;

public class CoinAlg {

    public static void main(String[] args) {
        //忽略第一位
        int[] coinArray = {0, 1, 3, 4};
        int count = coin(6, coinArray);
        System.out.println(count);
    }

    public static int coin(int money, int[] coinArray){
        int[][] coinResult = new int[coinArray.length][money + 1];


        //针对money=0的情况下默认初始化0
        for(int i = 0; i < coinArray.length; i++){
            coinResult[i][0] = 0;
        }
        //针对每种零钱都不选择的情况
        for(int i = 0; i <= money; i++){
            coinResult[0][i] = Integer.MAX_VALUE;
        }

        for(int i = 1; i <= money; i++){
            for(int j = 1; j < coinArray.length; j++){
                if(i < coinArray[j]){
                    coinResult[j][i] = coinResult[j][i];

                    continue;
                }

                if(coinResult[j][i] < (coinResult[j - 1][i - coinArray[j]]) + 1){
                    coinResult[j][i] = coinResult[j - 1][i];
                }else{
                    coinResult[j][i] = coinResult[j][i - coinArray[j - 1]] + 1;
                }

            }
        }

        return coinResult[coinArray.length - 1][money];
    }
}
