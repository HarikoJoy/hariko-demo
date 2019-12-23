package com.hariko.alg.dyn;

import java.util.Arrays;

//钢管切割问题
public class SteelCutAlg {
    public static void main(String[] args) {
        int steelLength = 8;
        //钢管切割对应每段的价值
        //切割0长度默认为0
        int[] priceArray = {0, 1, 5, 8, 9, 10, 17, 17, 20, 24, 30};

        int totalMaxPrice = cut(steelLength, priceArray);
        System.out.println("第一种:切割的最大总价值为: " + totalMaxPrice);

        int[] steelArray = new int[steelLength + 1];
        int[][] schemaArray = new int[steelLength + 1][steelLength+1];
        totalMaxPrice = cut(steelLength, priceArray, steelArray, schemaArray);
        System.out.println("第二种:切割的最大总价值为: " + totalMaxPrice);
        System.out.println("第二种:切割的所有集合为: " + Arrays.toString(steelArray));
        System.out.println("第二种:切割的所有方案为: ");
        printSchema(schemaArray);

    }

    /**
     * 循环方式求得子问题
     * @param steelLength  需要切割的钢管的长度
     * @param priceArray   价格对照数组
     * @param steelArray   每个长度对应的切割结果,方便回溯查找
     * @param schemaArray  保存切割方案结果
     * @return             最大收益
     */
    private static int cut(int steelLength, int[] priceArray, int[] steelArray, int[][] schemaArray) {
        if(steelLength == 0){
            return 0;
        }

        //优化成每个子问题的求解,并把每个问题的解保存
        int maxTotalPrice = Integer.MIN_VALUE;
        for(int i = 1; i <= steelLength; i++){
            int totalPrice = Integer.MIN_VALUE;
            for(int j = 1; j <= i; j++) {
                if(priceArray[j] + steelArray[i - j] > totalPrice){
                    totalPrice = priceArray[j] + steelArray[i - j];
                    //分割方案也是要复制的
                    //先把上一个方案复制一份,因为数组是引用的,所以要新建一个数组
                    //这里是保存切割方案最重要的地方
                    schemaArray[i] = Arrays.copyOf(schemaArray[i - j], steelLength + 1);
                    //然后在新选择的切割段上加上1,代表又在这个长度上切割了一次
                    schemaArray[i][j] += 1;
                }
            }
            steelArray[i] = totalPrice;
            maxTotalPrice = maxTotalPrice > totalPrice ? maxTotalPrice : totalPrice;
        }

        return maxTotalPrice;
    }

    public static void printSchema(int[][] schemaArray){
        for(int i = 1; i < schemaArray.length; i++){
            for(int j = 1; j < schemaArray[i].length; j++){
                System.out.printf("%3d\t", schemaArray[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * 递归方式求得结果
     * @param steelLength
     * @param priceArray
     * @return
     */
    public static int cut(int steelLength, int[] priceArray){
        if(steelLength == 0){
            return 0;
        }

        int totalPrice = Integer.MIN_VALUE;
        for(int i = 1; i <= steelLength; i++){
            totalPrice = Math.max(totalPrice, priceArray[i] + cut(steelLength - i, priceArray));
        }

        return totalPrice;
    }

}
