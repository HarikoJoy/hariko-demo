package com.hariko.alg;

public class HanoTower {
    public static void main(String[] args) {
        hanoTower(3, 'A', 'B', 'C');
    }

    public static void hanoTower(int num, char a, char b, char c){
        //如果只有一个盘子
        if(num == 1){
            System.out.println("第1个盘从" + a + " --> " + c);
        }else{

            //如果我们有n>=2个盘子,我们总是可以看做两个盘子,最下面的一个盘子,上面的n-1个盘子
            //1.先把上面的n-1的盘子 A --> B  移动过程中会用到 C
            hanoTower(num - 1, a, c, b);
            //2.把最下面的盘子  A --> C
            System.out.println("第" + num + "盘从" + a + " --> " + c);
            //3.把B塔上的盘子  B --> C 移动过程中使用到 A
            hanoTower(num - 1, b, a, c);
        }
    }

}
