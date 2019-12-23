package com.hariko.alg.dyn;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 问题: 如果coinArray的长度为N,生成行数为N,默认第一位为无效位,列数为money + 1的矩阵dp,dp[i][j]代表的就是使用coinArray[1..i]的货币的情况下,组成印数j有多少种方法
 * <p>
 * 1.如果完全不用coinArray[i],只使用coinArray[1..i-1]时,方法数为dp[i-1][j]
 * 2.如果用1张coinArray[i],剩下的钱用coinArray[1..i-1]时,方法数为dp[i-1][j - 1 * coinArray[i]]
 * 3.如果用2张coinArray[i],剩下的钱用coinArray[1..i-1]时,方法数为dp[i-1][j - 2 * coinArray[i]]
 */
public class CoinAlg {
    public static void main(String[] args) {
        int[] coinArray = {1, 2, 4, 5, 6};
        int money = 6;

        System.out.println("第一种方法: ");
        System.out.println(coinAlgOf1(coinArray, money));

        System.out.println("第二种方法: ");
        System.out.println(coinAlgOf2(coinArray, money));

        System.out.println("第三种方法: ");
        System.out.println(coinAlgOf3(coinArray, money));

        System.out.println("第四种方法: ");
        System.out.println(coinAlgOf4(coinArray, money));
    }

    //从小问题开始向大问题求解
    //retMap数组的最后一个值就是组合的最大解法
    public static int coinAlgOf1(int[] coinArray, int money) {
        int[] retMap = new int[money + 1];
        retMap[0] = 1;

        for (int i = 0; i < coinArray.length; i++) {
            for (int j = coinArray[i]; j <= money; j++) {
                //只要retMap[j - coinArray[i]] 这个值减为0就是有一种组合方法
                //非常重要
                retMap[j] += retMap[j - coinArray[i]];

                int orgNum = retMap[j - coinArray[i]];
                int newNum = retMap[j];


            }
            System.out.println(Arrays.toString(retMap));
        }

        //System.out.println(Arrays.toString(retMap));
        return retMap[money];
    }

    //先解决小问题,然后解决大问题
    public static int coinAlgOf2(int[] coinArray, int money) {
        int[][] retMap = initRetMap(coinArray, money);
        List<CoinPoint> coinPointList = new ArrayList<>();

        //因为要遍历所有的组合结果,所以要从每个硬币开始走一次
        for (int i = 1; i <= coinArray.length; i++) {
            List<StringBuffer> innerStringBufferList = new ArrayList<>();

            for (int j = 1; j <= money; j++) {
                CoinPoint coinPoint = new CoinPoint(i, j);

                //k为coinArray[i]有几个
                for (int k = 0; k <= j / coinArray[i - 1]; k++) {
                    //retMap[i][j]
                    //因为是多轮情况,所以需要多次的遍历结果相加
                    retMap[i][j] += retMap[i - 1][j - k * coinArray[i - 1]];

                    int orgNum = retMap[i - 1][j - k * coinArray[i - 1]];
                    int newNum = retMap[i][j];

                    if (k >= 0 && orgNum == newNum && k == j / coinArray[i - 1]) {
                        CoinSchema coinSchema = new CoinSchema(i - 1, k);
                        coinSchema.setPrevCoinPoint(coinPointList, i - 1, j - k * coinArray[i - 1]);
                        coinPoint.getSchemaList().add(coinSchema);
                    }

                    if (k >= 1 && orgNum != newNum) {
                        CoinSchema coinSchema = new CoinSchema(i - 1, k);
                        coinSchema.setPrevCoinPoint(coinPointList, i - 1, j - k * coinArray[i - 1]);
                        coinPoint.getSchemaList().add(coinSchema);
                    }
                }
                coinPointList.add(coinPoint);
            }
        }

        print(retMap);
        printCoinPointList(coinPointList, coinArray, money);
        return retMap[coinArray.length][money];
    }


    public static void printCoinPointList(List<CoinPoint> coinPointList, int[] coinArray, int money) {
        System.out.println("金额(" + money + ")的组合方案为:");

        for (CoinPoint coinPoint : coinPointList) {
            if (coinPoint.getY() == money) {

                coinPoint.getSchemaList().stream().forEach(coinSchema ->
                {
                    System.out.print("其中一种: ");
                    System.out.printf("硬币(%2d)需要(%2d)个", coinArray[coinSchema.getCoinIndex()], coinSchema.getCoinNum());
                    recrusCoinSchemaList(coinSchema.getPrev(), coinArray);
                });

                System.out.println();
            }
        }
    }

    private static void recrusCoinSchemaList(CoinPoint curCoin, int[] coinArray) {
        if (null != curCoin && curCoin.getSchemaList() != null) {
            curCoin.getSchemaList().stream().forEach(coinSchema ->
            {
                System.out.printf("硬币(%2d)需要(%2d)个", coinArray[coinSchema.getCoinIndex()], coinSchema.getCoinNum());
                recrusCoinSchemaList(coinSchema.getPrev(), coinArray);
            });
        }
    }


    private static int[][] initRetMap(int[] coinArray, int money) {
        int x = coinArray.length + 1;
        int y = money + 1;
        int[][] retMap = new int[x][y];

        //用i种硬币组合成0金额的组合种数
        for (int i = 0; i < x; i++) {
            retMap[i][0] = 1;
        }

        for (int j = 1; j < y; j++) {
            retMap[0][j] = 0;
        }

        return retMap;
    }

    public static void printSelectCoin(int[][] retMap, int[] coinArray) {
        for (int i = 1; i < retMap.length; i++) {
            for (int j = 1; j < retMap[i].length; j++) {
                if (retMap[i][j] != 0) {
                    System.out.printf("%2d \t", coinArray[i - 1]);
                }
            }
            System.out.println();
        }
    }

    public static void print(int[][] retMap) {
        for (int i = 1; i < retMap.length; i++) {
            for (int j = 1; j < retMap[i].length; j++) {
                System.out.printf("%2d\t", retMap[i][j]);
            }
            System.out.println();
        }
    }

    public static int coinAlgOf3(int[] coinArray, int money) {
        if (null == coinArray || coinArray.length == 0 || money < 0) {
            return 0;
        }

        return doCoinAlgOf3(coinArray, 0, money);
    }

    public static int doCoinAlgOf3(int[] coinArray, int index, int money) {
        int res = 0;
        if (index == coinArray.length) {
            res = money == 0 ? 1 : 0;
        } else {
            for (int i = 0; coinArray[index] * i <= money; i++) {
                res += doCoinAlgOf3(coinArray, index + 1, money - coinArray[index] * i);
            }
        }
        return res;
    }

    public static int coinAlgOf4(int[] coinArray, int money) {
        if (null == coinArray || coinArray.length == 0 || money < 0) {
            return 0;
        }

        int[][] retMap = new int[coinArray.length + 1][money + 1];
        int count = doCoinAlgOf4(coinArray, 0, money, retMap);
        print(retMap);
        return count;
    }

    public static int doCoinAlgOf4(int[] coinArray, int index, int money, int[][] retMap) {
        int res = 0;
        if (index == coinArray.length) {
            res = money == 0 ? 1 : 0;
        } else {
            int mapValue = 0;
            for (int i = 0; coinArray[index] * i <= money; i++) {
                mapValue = retMap[index + 1][money - coinArray[index] * i];
                if (mapValue != 0) {
                    res += mapValue;
                } else {
                    res += doCoinAlgOf4(coinArray, index + 1, money - coinArray[index] * i, retMap);
                }
            }
        }

        retMap[index][money] = res;
        return res;
    }

}

class CoinPoint {
    private int x;
    private int y;

    private List<CoinSchema> schemaList;

    public CoinPoint(int x, int y) {
        this.x = x;
        this.y = y;
        this.schemaList = new ArrayList<>();
    }

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

    public List<CoinSchema> getSchemaList() {
        return schemaList;
    }

    public void setSchemaList(List<CoinSchema> schemaList) {
        this.schemaList = schemaList;
    }

    @Override
    public boolean equals(Object o) {

        return this.x == ((CoinPoint) o).getX() && this.y == ((CoinPoint) o).getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, schemaList);
    }
}

class CoinSchema {
    private int coinIndex;

    private int coinNum;

    private CoinPoint prev;

    public CoinPoint setPrevCoinPoint(List<CoinPoint> coinPointList, int x, int y) {
        CoinPoint coinPoint = new CoinPoint(x, y);
        int index = coinPointList.indexOf(coinPoint);
        CoinPoint tempPoint = -1 == index ? null : coinPointList.get(index);
        this.setPrev(tempPoint);
        return tempPoint;
    }

    public CoinSchema(int coinIndex, int coinNum) {
        this(coinIndex, coinNum, null);
    }

    public CoinSchema(int coinIndex, int coinNum, CoinPoint prev) {
        this.coinIndex = coinIndex;
        this.coinNum = coinNum;
        this.prev = prev;
    }

    public int getCoinNum() {
        return coinNum;
    }

    public void setCoinNum(int coinNum) {
        this.coinNum = coinNum;
    }

    public int getCoinIndex() {
        return coinIndex;
    }

    public void setCoinIndex(int coinIndex) {
        this.coinIndex = coinIndex;
    }

    public CoinPoint getPrev() {
        return prev;
    }

    public void setPrev(CoinPoint prev) {
        this.prev = prev;
    }

    @Override
    public boolean equals(Object o) {
        return this.getCoinIndex() == ((CoinSchema) o).getCoinIndex();
    }

    @Override
    public int hashCode() {
        return Objects.hash(coinIndex, prev);
    }
}
