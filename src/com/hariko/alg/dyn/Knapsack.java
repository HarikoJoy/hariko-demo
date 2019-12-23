package com.hariko.alg.dyn;
/**
 * 算法主要思想:
 * 对于给定的n个物品,设w[i]和v[i]分别为第i个物品的重量和价值
 * C为背包的容量,在令v[i][j]表示前i个物品中能够装入容量为j的背包中的最大价值,则我们会有以下结果
 * 1.当v[i][0]=v[0][j]=0代表:  没有容量或者澧物品装入
 * 2.当w[i]>j时,v[i][j] = v[i-1][j]  当第i个物品的重量大于背包剩余的重量空间时,最优解就是上一个解
 * 3.当w[i]<=j时，v[i][j] = max(v[i-1][j-w[i]] + v[i], v[i-1][j])
 *   v[i-1][j]上一个单元存储的最大价值
 */
public class Knapsack {

    public static void main(String[] args) {
        //物品的重量
        int[] w = {0, 1, 4, 3}; //第一个物品虚拟
        //物品的价值
        int[] v = {0, 1500, 3000, 2000}; //第一个物品虚拟
        //背包的容量
        int capacity = 4;

        System.out.println("第一种算法结果为: ");
        System.out.println(alg1(capacity, w, v));

        System.out.println("第二种算法结果为: ");
        alg2(w, v, capacity);

        System.out.println("第三种算法结果为: ");
        System.out.println(alg3(capacity, w, v));
    }

    private static void alg2(int[] w, int[] v, int capacity) {
        //物品的个数
        int count = v.length;

        //为了记录放入商品的情况
        int[][] weight = new int[count][capacity + 1];

        //先创建一个二维数组
        //v[i][j]表示前i个物品在容量为j的背包的最大价值
        //初始化的数组多一行,多一列,为了方便操作
        int[][] value = new int[count][capacity + 1];

        //先初始化第一行第一列的值都为0
        for(int i = 0; i < value.length; i++){
            value[i][0] = 0; //第一列初始化为0
        }
        for(int j = 0; j < value[0].length; j++){
            value[0][j] = 0; //第一行初始化0
        }

        //根据前面公式写代码
        for(int i = 1; i < value.length; i++){//不处理第一列,因为都为0
            for(int j = 1; j < value[0].length; j++){//不处理第一行,因为都为0
                if(w[i] > j){
                    value[i][j] = value[i-1][j];
                }else{
                    //value[i][j] = Math.max(value[i-1][j], value[i-1][j-w[i]]+ v[i]);
                    //因为为了记录商品放入背包的情况,不能直接使用上面的公式
                    if(value[i-1][j] < value[i-1][j-w[i]]+ v[i]){
                        value[i][j] = value[i-1][j-w[i]]+ v[i];
                        weight[i][j] = 1;
                    }else{
                        value[i][j] = value[i-1][j];
                    }
                }
            }
        }


        System.out.println("输出存储物价值:");
        for(int i = 0; i < v.length; i++){
            for(int j = 0; j < value[0].length; j++){
                System.out.print(value[i][j] + "\t");
            }
            System.out.println();
        }

        System.out.println("输出存储的重量:");
        //这种写法会把所有放入的情况都输出,我们只需要最后的放入
        for(int i = 0; i < weight.length; i++){
            for(int j = 0; j < weight[0].length; j++){
                System.out.print(weight[i][j] + "\t");
            }
            System.out.println();
        }


        System.out.println("输出最终放入的商品:");
        int i = weight.length - 1;
        int j = weight[0].length - 1;
        while (i > 0){//从最后开始遍历
            if(weight[i][j] == 1){
                System.out.println("第 " + i + " 个商品放入到背包");
                j -= w[i];
            }
            i--;
        }
    }

    //每个物品可以重复放
    public static int alg1(int cap, int[] w, int[] v){
        int n = w.length;
        int[] dp = new int[cap + 1];

        /*for(int i = 1; i <= n; i++){
            for(int j = cap; j > w[i - 1]; j--){
                if(dp[j - w[i - 1]] + v[i - 1] > dp[j]){
                    dp[j] = dp[j - w[i - 1]] + v[i - 1];
                }
            }
        }*/

        for(int i = 1; i < n; i++){
            for(int j = w[i]; j <= cap; j++){
                if(dp[j - w[i]] + v[i] > dp[j]){
                    dp[j] = dp[j - w[i]] + v[i];
                }
            }
        }

        return dp[cap];
    }

    //每个物品放一次
    public static int alg3(int cap, int[] w, int[] v){
        int n = w.length;
        int[] dp = new int[cap + 1];

        for(int i = 1; i < n; i++){
            for(int j = cap; j >= w[i]; j--){
                if(dp[j - w[i]] + v[i] > dp[j]){
                    dp[j] = dp[j - w[i]] + v[i];
                }
            }
        }
/*

        for(int i = 1; i < n; i++){
            for(int j = w[i]; j <= cap; j++){
                if(dp[j - w[i]] + v[i] > dp[j]){
                    dp[j] = dp[j - w[i]] + v[i];
                }
            }
        }
*/

        return dp[cap];
    }
}
