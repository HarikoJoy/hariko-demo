package com.hariko.alg.classics;


import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

//1.创建棋盘,是一个二维数组
//2.将当前位置设置为已经访问,然后根据当前位置,计算马儿还能走哪些位置,并放入一个list中,最多有8个位置,每走一步,step += 1
//3.遍历list中放的所有位置,看哪些可以走通,走不通,回溯
//4.判断马儿是否完成任务,使用step和应该走的步数比较,如果没有完成,将整个棋盘置于0
public class HouseCheeseAlg {

    public static void main(String[] args) {
        ChessBoard chessBoard = new ChessBoard(8, 8);
        House house = new House(chessBoard);

        house.travle(0, 0, 1);
        house.printFinalResult();
    }
}

class ChessBoard{
    private int x;
    private int y;
    //表示棋盘上的点是否被访问过,横向排,x * X + y
    private boolean[] visited;

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public ChessBoard(int x, int y) {
        this.x = x;
        this.y = y;
        this.visited = new boolean[x * y];
        this.board = new int[x][y];
    }



    //表示所有位置是否都被访问过
    private boolean finished;
    private int[][] board;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * 走过棋盘的哪个位置,第几步走的
     * @param x
     * @param y
     * @param step
     */
    public void pass(int x, int y, int step){
        board[x][y] = step;
    }

    public void resetBoard(int x, int y){
        board[x][y] = 0;
    }

    public void visited(int x, int y, boolean flag){
        visited[x * this.x + y] = flag;
    }

    public boolean isVisited(int index){
        return visited[index];
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isFinished() {
        return finished;
    }
}

class House{
    private ChessBoard chessBoard;
    private int step = 1;

    public House(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    public void travel(int x, int y){
        travle(x, y, this.step);
    }

    /**
     * 马儿走棋盘
     * @param x    马儿当前位置的行
     * @param y    马儿当前位置的列
     * @param step 第几步,初始位置是第1步
     */
    public void travle(int x, int y, int step){
        this.chessBoard.pass(x, y, step);
        //x = 4 X = 8 y = 4 36
        this.chessBoard.visited(x, y, true);

        //获取当前位置的下一个位置
        List<Point> pointList = getAllPointList(new Point(y, x));
        greedySort(pointList);

        while (!pointList.isEmpty()){
            Point p = pointList.remove(0); //取出下一个可以走的位置

            //判断这个点是否已经访问过
            //棋盘的列 × 所在位置的纵坐标  +  所在位置的横坐标
            if(!this.chessBoard.isVisited(p.y * this.chessBoard.getX() + p.x)){
                travle(p.y, p.x, step + 1);
            }
        }

        //判断马儿是否完成任务,使用step和应该走的步数比较,如果没有完成,将该点置于0
        //说明:setp < X * Y 有两种情况
        //1.棋盘到目前位置,仍然没有走完
        //2.棋盘处于一个回溯的过程
        if(step < this.chessBoard.getX() * this.chessBoard.getY() && !this.chessBoard.isFinished()){
            this.chessBoard.resetBoard(x, y);
            this.chessBoard.visited(x, y, false);
        }else{
            this.step = step;
            this.chessBoard.setFinished(true);
        }
    }

    public void printFinalResult(){
        int[][] board = this.chessBoard.getBoard();
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                System.out.printf("%3d\t", board[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * 根据当前位置,计算马儿还能走哪些位置,并把结果放入到list中,最多有8个位置
     * @param curPoint
     * @return
     */
    public List<Point> getAllPointList(Point curPoint){
        List<Point> pointList = new ArrayList<>();

        Point point = new Point();
        //      × × 6 × 7 × × ×
        //      × 5 × × × 0 × ×
        //      × × × @ × × × ×
        //      × 4 × × × 1 × ×
        //      × × 3 × 2 × × ×
        //走日字,x坐标为2,y坐标为1,以下判断代表没有走出棋盘
        //表示马儿可以走  5  这个位置
        if((point.x = curPoint.x - 2) >= 0 && (point.y = curPoint.y - 1) >= 0){
            pointList.add(new Point(point));
        }
        //表示马儿可以走  6  这个位置
        if((point.x = curPoint.x - 1) >= 0 && (point.y = curPoint.y - 2) >= 0){
            pointList.add(new Point(point));
        }
        //表示马儿可以走  7  这个位置
        if((point.x = curPoint.x + 1) < chessBoard.getX() && (point.y = curPoint.y - 2) >= 0){
            pointList.add(new Point(point));
        }
        //表示马儿可以走  0  这个位置
        if((point.x = curPoint.x + 2) < chessBoard.getX() && (point.y = curPoint.y - 1) >= 0){
            pointList.add(new Point(point));
        }
        //表示马儿可以走  1  这个位置
        if((point.x = curPoint.x + 2) < chessBoard.getX() && (point.y = curPoint.y + 1) < chessBoard.getY()){
            pointList.add(new Point(point));
        }
        //表示马儿可以走  2  这个位置
        if((point.x = curPoint.x + 1) < chessBoard.getX() && (point.y = curPoint.y + 2) < chessBoard.getY()){
            pointList.add(new Point(point));
        }
        //表示马儿可以走  3  这个位置
        if((point.x = curPoint.x - 1) >= 0 && (point.y = curPoint.y + 2) < chessBoard.getY()){
            pointList.add(new Point(point));
        }
        //表示马儿可以走  4  这个位置
        if((point.x = curPoint.x - 2) >= 0 && (point.y = curPoint.y + 1) < chessBoard.getY()){
            pointList.add(new Point(point));
        }

        return pointList;
    }

    //根据当前这一步所有的下一步的选择位置,进行非递减排序
    public void greedySort(List<Point> pointList){
        pointList.sort(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                //先获取到o1的下一步的所有位置个数

                List<Point> pointList1 = getAllPointList(o1);
                int count1 = null != pointList1 ? pointList1.size() : 0;
                List<Point> pointList2 = getAllPointList(o2);
                int count2 = null != pointList2 ? pointList2.size() : 0;
                return count1 < count2 ? -1 : 1;
            }
        });
    }
}
