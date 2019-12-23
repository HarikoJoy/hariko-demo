package com.hariko.alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GraphDemo {

    public static void main(String[] args) {
        int n = 5; //顶点的个数
        String vertexValue[] = {"A", "B", "C", "D", "E"};
        Graph graph = new Graph(n);

        for(String val : vertexValue){
            graph.insertVertext(val);
        }

        //添加边
        //A->B  A->C B->C B->D B->E
        graph.insertEdge(0, 1, 1);
        graph.insertEdge(0, 2, 1);
        graph.insertEdge(1, 2 ,1);
        graph.insertEdge(1, 3, 1);
        graph.insertEdge(1, 4 ,1);

        graph.showGraph();


        graph.dfs();
    }
}

class Graph {

    private List<String> vertexList; // 存储顶点集合
    private int[][] edges; // 邻接矩阵
    private int numOfEdges; //边的个数
    private boolean[] isVisited; //标记顶点是否被访问过

    public Graph(int n){
        edges = new int[n][n];
        vertexList = new ArrayList<>(n);
        numOfEdges = 0;
        isVisited = new boolean[n];
    }

    //深度优先遍历
    public void dfs(boolean[] isVisited, int i){
        //首先访问该结点
        System.out.print(getValueByIndex(i) + "->");
        isVisited[i] = true;

        //查找i的第一个邻接结点
        int w = getFirstNeighbor(i);

        while(w != -1){
            //如果没有访问过
            if(!isVisited[w]) {
                dfs(isVisited, w);
            }
            //查找下一个邻接结点
            w = getNextNeighbor(i, w);
        }
    }

    //
    public void dfs(){
        //遍历所有结点
        for(int i = 0; i < vertexList.size(); i++){
            if(!isVisited[i]){
                dfs(isVisited, i);
            }
        }
    }

    public void bfs(){
        //遍历所有结点
        for(int i = 0; i < vertexList.size(); i++){
            if(!isVisited[i]){
                bfs(isVisited, i);
            }
        }
    }

    public void bfs(boolean[] isVisited, int i){
        int u; //表示队列的头结点的对应下标
        int w; //邻接结点 w

        LinkedList<Integer> queue = new LinkedList<>();

        System.out.print(getValueByIndex(i) + "=>");

        isVisited[i] = true;
        //加入队列
        queue.add(i);

        while (!queue.isEmpty()){
            //取出队列的头
            u = queue.removeFirst();
            //获取下一个邻接结点
            w = getFirstNeighbor(u);

            while (w != -1) {
                if (!isVisited[w]){
                    System.out.println(getValueByIndex(w) + "=>");
                    isVisited[w] = true;
                    //入队
                    queue.addLast(w);
                }

                //以u为邻接结点去找w后面的下一个邻接结点
                w = getNextNeighbor(u, w);
            }
        }

    }

    //获取第一个邻接结点下标
    public int getFirstNeighbor(int index){
        for(int j = 0; j < vertexList.size(); j++){
            if(edges[index][j] > 0){
                return j;
            }
        }
        return -1;
    }

    public int getNextNeighbor(int v1, int v2){
        for(int j = v2 + 1; j < vertexList.size(); j++){
            if(edges[v1][j] > 0){
                return j;
            }
        }

        return -1;
    }

    //添加顶点
    public void insertVertext(String vertex){
        vertexList.add(vertex);
    }

    public int getNumOfEdges(){
        return numOfEdges;
    }

    public int getNumOfVertex(){
        return vertexList.size();
    }

    //返回结点下标对应的数据 0->A  1->B
    public String getValueByIndex(int i){
        return vertexList.get(i);
    }

    public int getWeight(int v1, int v2) {
        return edges[v1][v2];
    }

    public void showGraph(){
        for(int[] link : edges){
            System.out.println(Arrays.toString(link));
        }
    }

    /**
     *
     * @param v1    第一个顶点代表的下标 A->B  A->D
     * @param v2    第二个顶点代表的下标
     * @param weight
     */
    public void insertEdge(int v1, int v2, int weight){
        edges[v1][v2] = weight;
        edges[v2][v1] = weight;
        numOfEdges++;
    }

}
