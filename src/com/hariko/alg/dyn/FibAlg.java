package com.hariko.alg.dyn;

public class FibAlg {

    public static void main(String[] args) {
        System.out.println(fib(4));
    }

    public static int fib(int n){
        if(n == 0){
            return 0;
        }

        if(n == 1 || n == 2){
            return 1;
        }

        int[] table = new int[n];
        table[0] = 1;
        table[1] = 1;

        for(int i = 2; i < n; i++){
            table[i] = table[i - 1] + table[i - 2];
        }

        return table[n - 1];
    }
}
