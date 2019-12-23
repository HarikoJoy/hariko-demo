package com.hariko.alg;

/**
 * 八皇后问题
 */
public class EightQueuesAlg {
    //皇后的个数
    private static final int max = 8;
    //定义一个数组,存储皇后的位置
    private static int[] array = new int[max];

    public static void main(String[] args) {

    }

    private void check(int n){
        if(n == max){
            printEightQueues();
            return;
        }

        for(int i = 0; i < max; i++){
            //先把这个皇后放在该行的第一列上
            array[n] = i;
            //判断是否冲突
            if(judge(n)){
                check(n + 1);
            }
            //如果冲突,就继续执行array[n]=i,放在本行的后一个位置上
        }
    }

    /**
     * 查看当我们放置第n个皇后,就去检测该皇后和前面的是否冲突
     * array[n] == array[i] 两个皇后在同一列上
     * Math.abs(n - i) == Math.abs(array[n] - array[i]) 两个皇后在对角线上
     * @param n
     * @return
     */
    private boolean judge(int n){
        for(int i = 0; i < n; i++){
            if(array[n] == array[i] || Math.abs(n - i) == Math.abs(array[n] - array[i])){
                return false;
            }
        }
        return true;
    }

    public static void printEightQueues(){
        for(int i = 0; i < array.length; i ++){
            System.out.println(array[i] + " ");
        }
        System.out.println();
    }
}
