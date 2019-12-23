package com.hariko.alg;

/**
 * 迷宫问题,后续需要找出最短路径
 */
public class MazeAlg {

    public static void main(String[] args) {
        int[][] map = new int[8][7];

        for(int i = 0; i < 7; i++){
            map[0][i] = 1;
            map[7][i] = 1;
        }

        for(int i = 0; i < 7; i++){
            map[i][0] = 1;
            map[i][6] = 1;
        }

        map[3][1] = 1;
        map[3][2] = 1;
        System.out.println("小球走路之前:");
        printFinalMap(map);
        //使用递归函数给小球找路
        setWay(map, 1, 1);
        System.out.println("小球走路之后:");
        printFinalMap(map);
    }

    public static void printFinalMap(int[][] map){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 7; j++){
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * 当map[i][j]为0则代表该点没有走过 1表示为墙 2表示可以走 3表示已经走过但是走不通
     * 走迷宫时,需要确定一个走的策略 下->右->上->左
     *
     * @param map 表示地图
     * @param i   从地图的哪个横坐标开始找
     * @param j   从地图的哪个纵坐标开始找
     * @return
     */
    public static boolean setWay(int[][] map, int i, int j){
        if(map[6][5] == 2){
            return true;
        }else{
            if(map[i][j] == 0){//当前这个点还没有走过
                map[i][j] = 2;
                if(setWay(map, i + 1, j)){//向下走
                    return true;
                }else if(setWay(map, i, j + 1)){//向右走
                    return true;
                }else if(setWay(map, i - 1, j)){//向上走
                    return true;
                }else if(setWay(map, j, j - 1)){//向左走
                    return true;
                }else{
                    map[i][j] = 3;
                    return false;
                }
            }else{
                //如果map[i][j] != 0 可能是1,2,3
                return false;
            }
        }

    }
}
