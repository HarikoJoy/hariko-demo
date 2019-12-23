package com.hariko.alg.classics;

/**
 * 问题: 某城市新增7个站点,现要修路把7个站点连通
 * 思路: 按照权值从小到大的顺序选择n-1条边,并保证不构成回路
 *      首先构造一个只有n个顶点的森林,然后依据权值从小到大从连通网中选择边加入森林,并保证不会有回路,直到森林变成一棵树
 *
 * 1.需要对边按从小到大排序
 * 2.不产生回路
 */
public class KruskalAlg {

    public static void main(String[] args) {
        char[] vertexs = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int[][] matrix = new int[][]{
                {0,            12,          Kruskal.INF,     Kruskal.INF,    Kruskal.INF,         16,           14},
                {12,           0,           10,              Kruskal.INF,    Kruskal.INF,         7,            Kruskal.INF},
                {Kruskal.INF,  10,          0,               3,              5,                   6,            Kruskal.INF},
                {Kruskal.INF,  Kruskal.INF, 3,               0,              4,                   Kruskal.INF,  Kruskal.INF},
                {Kruskal.INF,  Kruskal.INF, 5,               4,              0,                   2,            8},
                {16,           7,           6,               Kruskal.INF,    2,                   0,            9},
                {14,           Kruskal.INF, Kruskal.INF,     Kruskal.INF,    8,                   9,            0}
        };

        Kruskal kruskal = new Kruskal(vertexs, matrix);
        kruskal.show();
        System.out.println("返回边(已经排序)的集合结果为:");
        kruskal.printKruskalEdgeArray();

        KruskalEdge[]  kruskalEdgeArray = kruskal.kruskalMain();
        System.out.println("最小生成树为:");
        for(int i = 0; i < kruskalEdgeArray.length; i++){
            System.out.println(kruskalEdgeArray[i]);
        }
    }
}

class KruskalEdge{
    char start; //边的起点
    char end; //边的终点
    int weight; //边的权值

    public KruskalEdge(char start, char end, int weight){
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "KruskalEdge{" +
                "start=" + start +
                ", end=" + end +
                ", weight=" + weight +
                '}';
    }
}


class Kruskal{
    private int edgeNum;
    private char[] vertexs;
    private int[][] matrix;
    //表示两个结点不能连通
    public static final int INF = Integer.MAX_VALUE;
    private KruskalEdge[] kruskalEdgeArray;

    public Kruskal(char[] vertexs, int[][] matrix){
        int vlen = vertexs.length;
        //初始化顶点
        this.vertexs = new char[vlen];
        for(int i = 0; i < vertexs.length; i++){
            this.vertexs[i] = vertexs[i];
        }
        //初始化邻接矩阵
        this.matrix = new int[vlen][vlen];
        for(int i = 0; i < vlen; i++){
            for(int j = 0; j < vlen; j++){
                this.matrix[i][j] = matrix[i][j];
            }
        }

        //统计边
        for(int i = 0; i < vlen; i++){
            for(int j = i + 1; j < vlen; j++){
                if(this.matrix[i][j] != INF){
                    edgeNum++;
                }
            }
        }

        this.kruskalEdgeArray = initKruskalEdge();
        System.out.println("返回边(没有排序)的集合结果为:");
        this.printKruskalEdgeArray();
        this.sortEdgeArray(this.kruskalEdgeArray);
    }

    public KruskalEdge[] kruskalMain(){
        int index = 0; //表示最后结果数组的索引
        int[] endArray = new int[this.edgeNum]; //用于保存已有最小生成树的顶点在最小生成树的终点

        //最终结果的边的集合
        KruskalEdge[] rets = new KruskalEdge[this.edgeNum];
        //获取图中的所有边的集合,一共有12条边
        System.out.println("图的边的集合一共有: " + this.kruskalEdgeArray.length);

        //遍历从小到大排序的边的集合
        //将边加入到最小生成树中,判断是否形成了回路,如果没有加入rets
        for(int i = 0; i < this.kruskalEdgeArray.length; i++){
            //获取第i条边的起点
            int p1 = getPosition(this.kruskalEdgeArray[i].start); //p1=4
            //获取第i条边的终点
            int p2 = getPosition(this.kruskalEdgeArray[i].end); //p2=5

            //获取这个p1顶点在最小生成树中的终点
            int m = getEnd(endArray, p1); //m=4
            //获取这个p2顶点在最小生成树中的终点
            int n = getEnd(endArray, p2);//n=5

            //是否构成回路
            if(m != n){
                endArray[m] = n; // 设置m在已有最小生成树的终点
                //{0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0}
                rets[index++] = kruskalEdgeArray[i];
            }
        }

        return rets;
    }

    public void show(){
        System.out.println("打印邻接矩阵为:");
        for(int i = 0; i < this.matrix.length; i++){
            for(int j = 0; j < this.matrix[i].length; j++)
                System.out.printf("%20d", this.matrix[i][j]);
            System.out.println();
        }
    }

    //对边进行排序
    private void sortEdgeArray(KruskalEdge[] edgeArray){
        for(int i = 0; i < edgeArray.length; i++){
            for(int j = 0; j < edgeArray.length - i - 1; j++){
                if(edgeArray[j].weight > edgeArray[j + 1].weight){
                    KruskalEdge temp = edgeArray[j];
                    edgeArray[j] = edgeArray[j + 1];
                    edgeArray[j + 1] = temp;
                }
            }
        }
    }

    /**
     *
     * @param ch 顶点的值
     * @return   返回ch顶点对应的下标,如果找不到,-1
     */
    private int getPosition(char ch){
        for(int i = 0; i < vertexs.length; i++){
            if(vertexs[i] == ch){
                return i;
            }
        }
        return -1;
    }

    //获取图中的边,放到
    private KruskalEdge[] initKruskalEdge(){
        int index = 0;
        KruskalEdge[] kruskalEdgeArray = new KruskalEdge[this.edgeNum];

        for(int i = 0; i < this.matrix.length; i++){
            for(int j = i + 1; j < this.matrix[i].length; j++){
                if(this.matrix[i][j] != INF){
                    kruskalEdgeArray[index++] = new KruskalEdge(vertexs[i], vertexs[j], matrix[i][j]);
                }
            }
        }


        return kruskalEdgeArray;
    }

    public KruskalEdge[] getKruskalEdgeArray(){
        return this.kruskalEdgeArray;
    }

    public void printKruskalEdgeArray(){
        for(int i = 0; i < kruskalEdgeArray.length; i++){
            System.out.println(kruskalEdgeArray[i]);
        }
    }

    /**
     * 获取下标为i的顶点的终点,用于后面判断两个顶点的终点是否相同
     * @param endArray 各个顶点的终点是哪个,是在遍历过程中逐步形成的
     * @param i        传入顶点的下标
     * @return         返回的是下标为i的顶点对应的终点的下标
     */
    private int getEnd(int[] endArray, int i){
        while (endArray[i] != 0){
            i = endArray[i];
        }

        return i;
    }
}
