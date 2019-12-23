package com.hariko.alg.dyn;

import java.util.Random;

//最长公共子序列
public class LCSAlg {
    private static final String BASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static Random random = new Random();

    public static void main(String[] args) {
        int size = 20;

        String x = generateRandomStr(size);
        String y = generateRandomStr(size);
        x = "ABCBDAB";
        y = "BDCABA";


        System.out.println("最长公共子序列为: ");
        printLCS(x, lcsAlg(x, y), x.length(), y.length());

        System.out.println("最长连续公共子序列为: ");
        lcsAlg1(x, y);
    }

    //不连续公共子序列
    private static int[][] lcsAlg(String x, String y){
        int m = x.length();
        int n = y.length();

        int[][] c = new int[m + 1][n + 1];

        for(int i = 0; i < m + 1; i++){
            c[i][0] = 0;
        }

        for(int j = 0; j < n + 1; j++){
            c[0][j] = 0;
        }

        int[][] path = new int[m + 1][n + 1];

        for(int i = 1; i < m + 1; i++){
            for(int j = 1; j < n + 1; j++){
                if(x.charAt(i - 1) == y.charAt(j - 1)){
                    c[i][j] = c[i - 1][j - 1] + 1;
                }else if(c[i - 1][j] >= c[i][j - 1]){
                    c[i][j] = c[i - 1][j];
                    path[i][j] = 1;
                }else{
                    c[i][j] = c[i][j - 1];
                    path[i][j] = -1;
                }
            }
        }

        System.out.println("输出c的结果集为: ");
        for(int i = 0; i < m + 1; i++){
            for (int j = 0; j < n + 1; j++){
                System.out.print(c[i][j] + "\t");
            }
            System.out.println();
        }

        System.out.println("输出path的结果集为: ");
        for(int i = 0; i < m + 1; i++){
            for (int j = 0; j < n + 1; j++){
                System.out.print(path[i][j] + "\t");
            }
            System.out.println();
        }

        return path;
    }

    private static void printLCS(String x, int[][] path, int m, int n){
        if(m == 0 || n == 0){
            return;
        }

        if(path[m][n] == 0){
            printLCS(x, path, m - 1, n - 1);
            System.out.printf("%c", x.charAt(m - 1));
        }else if(path[m][n] == 1){
            printLCS(x, path, m - 1, n);
        }else{
            printLCS(x, path, m, n - 1);
        }
    }

    private static String generateRandomStr(int size) {
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < size; i++){
            stringBuilder.append(BASE.charAt(random.nextInt(BASE.length())));
        }

        return stringBuilder.toString();
    }

    //连续公共子序列
    private static int[][] lcsAlg1(String x, String y){
        int m = x.length();
        int n = y.length();

        int[][] c = new int[m + 1][n + 1];

        for(int i = 0; i < m + 1; i++){
            c[i][0] = 0;
        }

        for(int j = 0; j < n + 1; j++){
            c[0][j] = 0;
        }

        int[][] path = new int[m + 1][n + 1];

        int max = 0;
        for(int i = 1; i < m + 1; i++){
            for(int j = 1; j < n + 1; j++){
                if(x.charAt(i - 1) == y.charAt(j - 1)){
                    c[i][j] = c[i - 1][j - 1] + 1;
                }else{
                    c[i][j] = 0;
                }

                if(max < c[i][j]){
                    max = c[i][j];
                }
            }
        }

        System.out.println("输出c的结果集为: ");
        for(int i = 0; i < m + 1; i++){
            for (int j = 0; j < n + 1; j++){
                System.out.print(c[i][j] + "\t");
            }
            System.out.println();
        }

        System.out.println("输出path的结果集为: ");
        for(int i = 0; i < m + 1; i++){
            for (int j = 0; j < n + 1; j++){
                System.out.print(path[i][j] + "\t");
            }
            System.out.println();
        }

        return path;
    }

}
