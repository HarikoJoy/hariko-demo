package com.hariko.alg.classics;

import java.util.Arrays;

//每一个顶点到所有顶点的最短路径
public class FloydAlg {
    private static int INF = 65535;
    public static void main(String[] args) {
        char[] pointArray = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};

        int[][] matrix = new int[][]{
                {0,    5,    7,    INF,    INF,    INF,    2},
                {5,    INF,  INF,  9,      INF,    INF,    3},
                {7,    INF,  INF,  INF,    8,      INF,    INF},
                {INF,  9,    INF,  INF,    INF,    4,      INF},
                {INF,  INF,  8,    INF,    INF,    5,      4},
                {INF,  INF,  INF,  4,      5,      INF,    6},
                {2,    3,    INF,  INF,    4,      6,      INF}
        };

        FloydGraph floydGraph = new FloydGraph(pointArray, matrix);
        floydGraph.show();

        floydGraph.floyd();

        floydGraph.show();
    }
}

class FloydGraph{
    public FloydGraph(char[] pointArray, int[][] matrixArray) {
        this.pointArray = pointArray;
        this.matrixArray = matrixArray;
        this.preArray = new int[pointArray.length][pointArray.length];

        //存放的是前驱顶点的下标,刚开始时每个顶点的前驱就是自己
        for (int i = 0; i < pointArray.length; i++) {
            Arrays.fill(this.preArray[i], i);
        }
    }

    private char[] pointArray; //顶点数组
    private int[][] matrixArray; //从各个顶点到其它顶点的距离
    private int[][] preArray; //保存要到达顶点的前驱结点


    public void show(){
        for(int k = 0; k < matrixArray.length; k++){
            for(int i = 0; i < preArray.length; i++){
                System.out.printf("%20d", preArray[k][i]);
            }
            System.out.println();
            for(int i = 0; i < matrixArray.length; i++){
                System.out.printf("%20d", matrixArray[k][i]);
            }

            System.out.println();

            for(int i = 0; i < matrixArray.length; i++){
                System.out.print("(" + pointArray[k] + "到" + pointArray[i] + "的最短路径是: " + matrixArray[k][i] + ")");
            }

            System.out.println();
            System.out.println();
        }
    }

    public void floyd(){
        int length = 0;//变量保存距离
        //对中间顶点进行遍历,k就是中间顶点的下标 [A, B, C, D, E, F, G]
        for(int k = 0; k < matrixArray.length; k++){
            //从 i 顶点出发到 [A, B, C, D, E, F, G]
            for (int i = 0; i < matrixArray.length; i++){
                //到达j顶点  [A, B, C, D, E, F, G]
                for(int j = 0; j < matrixArray[i].length; j++){
                    //求出从i顶点出发经过k中间顶点,到达j的距离
                    length = matrixArray[i][k] + matrixArray[k][j];

                    if(length < matrixArray[i][j]){
                        matrixArray[i][j] = length;
                        preArray[i][j] = preArray[k][j];
                    }
                }
            }
        }
    }

}