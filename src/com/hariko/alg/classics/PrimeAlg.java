package com.hariko.alg.classics;


import java.util.Arrays;

/**
 * 问题: 7个村庄,每个村庄之间的距离用两个点之间的权值来表示,如何修路保证各个村庄都能连通,并且修路的总里程数最小
 * 思路: 如何选择一棵最小生成树,且边上的权值总和最小
 *
 * 最小生成树的算法 Prime(普里姆) Kruskal(克鲁斯卡尔)
 *
 * 1.求最小生成树,也是在包含n个顶点的连通图中,找出只有n-1条边包含所有n个顶点的连通子图,也是最小连通子图
 * 2.设G=(V,E)是连通网,T=(U,D)是最小生成树,V,U是顶点集合,E,D是边的集合
 * 3.若从顶点u开始构建最小生成树,则从集合V中取出顶点u放入集合U中,标记顶点u的发V[u]=1
 * 3.若集合中顶点ui与集合V-U的顶点vj之间存在边,则寻找这些边中权值最小的边,但不能构成回路,将顶点vj加入集合U中,将边(ui,vj)加入集合D中,标记V[vj]=1
 * 4.重复步骤2,直到u与v相等
 */
public class PrimeAlg {

    public static void main(String[] args) {
        char[] data = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int vertx = data.length;
        //10000代表不连通
        int[][] weight = new int[][]{
                {10000,   5,      7,     10000,    10000,  10000,   2},
                {5,       10000,  10000, 9,        10000,  10000,   3},
                {7,       10000,  10000, 10000,    8,      10000,   10000},
                {10000,   9,      10000, 10000,    10000,  4,       10000},
                {10000,   10000,  8,     10000,    10000,  5,       4},
                {10000,   10000,  10000, 4,        5,      10000,   6},
                {2,       3,      10000, 10000,    4,      6,       10000}
        };


        PrimeGraph primeGraph = new PrimeGraph(vertx);
        PrimeTree primeTree = new PrimeTree();
        primeTree.createPrimeGraph(primeGraph, vertx, data, weight);
        primeTree.show(primeGraph);

        primeTree.prime(primeGraph, 0);
    }

}

class PrimeTree{
    /**
     * 创建图的邻接矩阵
     * @param primeGraph 图
     * @param vertx      顶点个数
     * @param data       各个顶点的值
     * @param weight     邻接矩阵
     */
    public void createPrimeGraph(PrimeGraph primeGraph, int vertx, char[] data, int[][] weight){
        int i, j;
        for(i = 0; i < vertx; i++){
            primeGraph.data[i] = data[i];
            for(j = 0; j < vertx; j++){
                primeGraph.weight[i][j] = weight[i][j];
            }
        }
    }

    public void show(PrimeGraph primeGraph){
        for(int i = 0; i < primeGraph.vertx; i++){
            System.out.println(Arrays.toString(primeGraph.weight[i]));
        }
    }

    /**
     *
     * @param primeGraph  所构建的图
     * @param v           从哪个顶点开始
     */
    public void prime(PrimeGraph primeGraph, int v){
        // 标记顶点是否访问过 默认都是0
        int[] visited = new int[primeGraph.vertx];

        //当前结点标记为已访问
        visited[v] = 1;
        //记录两个顶点的下标
        int h1 = -1;
        int h2 = -1;
        int minWeight = 10000;

        //因为有primeGraph.vertx个顶点,最终会有primeGraph.vertx-1条边
        //总共几条边需要循环几次
        //求出每个顶点的所有子树,计算出最小子树的最小边,把这个结点记录
        //每次循环找还没有终点的那个顶点的所有子树的最小子树,这些树最多只有两个结点,求出最小值的边,然后标记顶点为已访问
        for(int k = 1; k < primeGraph.vertx; k++){
            //这个是确定每一次生成的子图和哪个结点的距离最近
            for(int i = 0; i < primeGraph.vertx; i++) {
                for (int j = 0; j < primeGraph.vertx; j++) {
                    //从i结点开始,去获取这个结点的子树,找到最小的权值的边的结果
                    if (visited[i] == 1 && visited[j] == 0 && primeGraph.weight[i][j] < minWeight) {
                        //替换
                        minWeight = primeGraph.weight[i][j];
                        h1 = i;
                        h2 = j;
                    }
                }
            }

            System.out.println("边< " + primeGraph.data[h1] + "," + primeGraph.data[h2] + " > 权值为: " + minWeight);
            //将当前标记为已访问
            visited[h2] = 1;
            minWeight = 10000;
        }
    }
}


class PrimeGraph{
    int vertx; //图的结点个数
    char[] data; //存放结点数据
    int[][] weight; //存放边,就是我们的邻接矩阵

    public PrimeGraph(int vertx){
        this.vertx = vertx;
        data = new char[vertx];
        weight = new int[vertx][vertx];
    }
}
