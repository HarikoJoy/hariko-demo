package com.hariko.alg.classics;

import java.util.Arrays;

/**
 * 主要用来求解问题: 某一个顶点到时所有顶点的最短路径,最短路径问题
 *
 * 问题: 现在有7个村庄,有6个邮差,从G点出发,要分别把邮件送到其它6个村庄,求出从G点到6个村庄的最短距离
 *      G -> A  G -> B  G -> C  G -> D  G -> E  G -> F
 *
 * 思想: 图的广度优先算法
 */
public class DijkstraAlg {
    private static int INF = 65535;
    public static void main(String[] args) {
        char[] vertex = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};

        int[][] matrix = new int[][]{
                {INF,  5,    7,    INF,    INF,    INF,    2},
                {5,    INF,  INF,  9,      INF,    INF,    3},
                {7,    INF,  INF,  INF,    8,      INF,    INF},
                {INF,  9,    INF,  INF,    INF,    4,      INF},
                {INF,  INF,  8,    INF,    INF,    5,      4},
                {INF,  INF,  INF,  4,      5,      INF,    6},
                {2,    3,    INF,  INF,    4,      6,      INF}
        };

        DijkstraGraph dijkstraGraph = new DijkstraGraph(vertex, matrix);
        dijkstraGraph.show();

        dijkstraGraph.dijkstra(6);

        dijkstraGraph.printFinalResult();
    }

}

//已访问顶点集合
class VisitedVertex{
    //记录各个顶点是否访问过,1表示访问过,0表示未访问
    public int[] alreadyArray;
    //每个下标对应的值为前一个顶点下标
    public int[] preVisitedArray;
    //记录出发顶点到其它所有顶点的距离
    //比如:G为出发顶点,就会记录G到其它顶点的距离,最短路径就会保存在这里
    public int[] disArray;

    /**
     *
     * @param length 表示顶点的个数
     * @param index  出发顶点对应的下标,比如G顶点,下标是6
     */
    public VisitedVertex(int length, int index){
        this.alreadyArray = new int[length];

        //前驱结点都是0
        this.preVisitedArray = new int[length];
        this.disArray = new int[length];

        //初始化一个顶点到其它顶点的距离为 65535
        Arrays.fill(disArray, 65535);
        this.alreadyArray[index] = 1;//设置出发顶点为已访问过
        //设置到自己的访问距离为0
        this.disArray[index] = 0;//设置出发顶点的访问距离为0
    }

    /**
     * 判断index顶点是否访问过
     * @param index
     * @return
     */
    public boolean in(int index){
        return alreadyArray[index] == 1;
    }

    /**
     * 更新出发顶点到index顶点的距离
     * @param index
     * @param length
     */
    public void updateDis(int index, int length){
        disArray[index] = length;
    }

    /**
     * 更新pre顶点前驱为index顶点
     * @param pre
     * @param index
     */
    public void updatePre(int pre, int index){
        preVisitedArray[pre] = index;
    }

    /**
     * 返回出发顶点到index顶点的距离
     * @param index
     */
    public int getDis(int index){
        return disArray[index];
    }

    //继续选择并返回新的访问顶点,比如这里的G访问完后,就是A做为新的访问顶点
    //选取最小的距离的顶点
    public int updateArray(){
        int min = 65535, index = 0;

        for(int i = 0; i < alreadyArray.length; i++){
            if(alreadyArray[i] == 0 && disArray[i] < min){
                min = disArray[i];
                index = i;
            }
        }

        alreadyArray[index] = 1;
        return index;
    }

    public void printFinalResult(){
        System.out.println("输出已访问集合:");
        for(int i : this.alreadyArray)
            System.out.println(i + "");

        System.out.println("输出前驱集合:");
        for(int i : this.preVisitedArray)
            System.out.println(i + "");

        System.out.println("输出距离集合:");
        for(int i : this.disArray)
            System.out.println(i + "");
    }
}

class DijkstraGraph{
    private char[] vertex;
    private int[][] matrix;
    private VisitedVertex visitedVertex; //已经访问的顶点的集合

    public DijkstraGraph(char[] vertex, int[][] matrix){
        int vlen = vertex.length;
        //初始化顶点
        this.vertex = new char[vlen];
        for(int i = 0; i < vertex.length; i++){
            this.vertex[i] = vertex[i];
        }
        //初始化邻接矩阵
        this.matrix = new int[vlen][vlen];
        for(int i = 0; i < vlen; i++){
            for(int j = 0; j < vlen; j++){
                this.matrix[i][j] = matrix[i][j];
            }
        }
    }

    public void show(){
        System.out.println("打印邻接矩阵为:");
        for(int i = 0; i < this.matrix.length; i++){
            for(int j = 0; j < this.matrix[i].length; j++)
                System.out.printf("%20d", this.matrix[i][j]);
            System.out.println();
        }
    }

    public void dijkstra(int index){
        //设置出发顶点的各种数据的初始状态
        visitedVertex = new VisitedVertex(vertex.length, index);

        //更新出发顶点到其它顶点的相关信息
        //第一轮过后,G顶点走一次的结果
        //0  0  0  0  0  0  1
        //6  6  0  0  6  6  0
        //A2 B3 CN DN E4 F6 G0
        update(index);

        //继续遍历下一个顶点
        for(int j = 1; j < vertex.length; j++){
            index = visitedVertex.updateArray();
            update(index);
        }
    }

    //更新index下标到周围顶点的距离和周围顶点的前驱顶点
    private void update(int index){
        int length = 0;

        //遍历所有的顶点
        for(int j = 0; j < this.matrix[index].length; j++){
            //出发顶点到index顶点的距离 + 从index顶点到j顶点的距离的和
            //this.visitedVertex.getDis(index)   出发顶点到index顶点的距离
            length = this.visitedVertex.getDis(index) + matrix[index][j];

            //如果j顶点没有访问过,并且length小于  出发顶点到j顶点的距离,就埼更新
            if(!visitedVertex.in(j) && length < visitedVertex.getDis(j)){
                //更新j顶点的前驱为index
                //没什么用
                visitedVertex.updatePre(j, index);
                //更新出发顶点到j顶点的距离
                visitedVertex.updateDis(j, length);
            }
        }
    }

    public void printFinalResult(){
        this.visitedVertex.printFinalResult();
    }
}
