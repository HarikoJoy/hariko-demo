package com.hariko.alg.dyn;

/**
 * 题目描述： 一个台阶总共有n级，如果一次可以跳1级，也可以跳2级。求总共有多少总跳法，并分析算法的时间复杂度。
 * 通过题目的描述，可以很清晰地看到，这就是一个Fibonacci数列。
 */
public class SkipStepAlg {

    //递归方式
    public static int alg1(int stageNum) {

        if (stageNum <= 0)
            return 0;
        else if (1 == stageNum)
            return 1;
        else if (2 == stageNum)
            return 2;

        return alg1(stageNum - 1) + alg1(stageNum - 2);
    }

    //采用自顶向下的动态规划。
    public static long alg2(int stageNum) {
        long[] counter = new long[101];
        if (0 != counter[stageNum])
            return counter[stageNum];

        //定义递归出口
        if (stageNum <= 0)
            return 0;
        else if (1 == stageNum)
            return counter[1] = 1;
        else if (2 == stageNum)
            return counter[2] = 2;

        counter[stageNum] = alg2(stageNum - 1) + alg2(stageNum - 2);
        return counter[stageNum];
    }

    //自底向上动态规划
    public static long alg3(int number) {
        //题目保证 number 最大为100
        long[] counter = new long[101];
        counter[1] = 1;
        counter[2] = 2;
        int calculatedIndex = 2;

        if (number <= calculatedIndex)
            return counter[number];

        //防止下标越界
        if (number > 100)
            number = 100;

        for (int i = calculatedIndex + 1; i <= number; i++) {
            counter[i] = counter[i - 1] + counter[i - 2];
        }
        calculatedIndex = number;
        return counter[number];

    }

    /**
     * 二、变态跳台阶问题
     * <p>
     * 题目描述： 一个台阶总共有n级，如果一次可以跳1级，也可以跳2级……也可以跳n级。求总共有多少总跳法，并分析算法的时间复杂度。
     * <p>
     * 分析：用Fib(n)表示跳上n阶台阶的跳法数。如果按照定义，Fib(0)肯定需要为0，否则没有意义。但是我们设定Fib(0) = 1；n = 0是特殊情况，通过下面的分析就会知道，强制令Fib(0) = 1很有好处。ps. Fib(0)等于几都不影响我们解题，但是会影响我们下面的分析理解。
     * <p>
     * 当n = 1 时， 只有一种跳法，即1阶跳：Fib(1) = 1;
     * <p>
     * 当n = 2 时， 有两种跳的方式，一阶跳和二阶跳：Fib(2)  = 2;
     * <p>
     * 到这里为止，和普通跳台阶是一样的。
     * <p>
     * 当n = 3 时，有三种跳的方式，第一次跳出一阶后，对应Fib(3-1)种跳法； 第一次跳出二阶后，对应Fib(3-2)种跳法；第一次跳出三阶后，只有这一种跳法。Fib(3) = Fib(2) + Fib(1)+ 1 = Fib(2) + Fib(1) + Fib(0) = 4;
     * <p>
     * 当n = 4时，有四种方式：第一次跳出一阶，对应Fib(4-1)种跳法；第一次跳出二阶，对应Fib(4-2)种跳法；第一次跳出三阶，对应Fib(4-3)种跳法；第一次跳出四阶，只有这一种跳法。所以，Fib(4) = Fib(4-1) + Fib(4-2) + Fib(4-3) + 1 = Fib(4-1) + Fib(4-2) + Fib(4-3) + Fib(4-4) 种跳法。
     * <p>
     * 当n = n 时，共有n种跳的方式，第一次跳出一阶后，后面还有Fib(n-1)中跳法； 第一次跳出二阶后，后面还有Fib(n-2)中跳法..........................第一次跳出n阶后，后面还有 Fib(n-n)中跳法。Fib(n) = Fib(n-1)+Fib(n-2)+Fib(n-3)+..........+Fib(n-n) = Fib(0)+Fib(1)+Fib(2)+.......+Fib(n-1)。
     * <p>
     * 通过上述分析，我们就得到了通项公式：
     * <p>
     * Fib(n) =  Fib(0)+Fib(1)+Fib(2)+.......+ Fib(n-2) + Fib(n-1)
     * <p>
     * 因此，有 Fib(n-1)=Fib(0)+Fib(1)+Fib(2)+.......+Fib(n-2)
     * <p>
     * 两式相减得：Fib(n)-Fib(n-1) = Fib(n-1)         =====》  Fib(n) = 2*Fib(n-1)     n >= 3
     * <p>
     * 这就是我们需要的递推公式：Fib(n) = 2*Fib(n-1)     n >= 3
     *
     * @param number
     * @return
     */
    public static long alg4(int number) {
        //题目保证 number 最大为100
        long[] counter = {0};
        counter[0] = 1;
        counter[1] = 1;
        counter[2] = 2;
        int calculatedIndex = 2;

        if (number <= calculatedIndex)
            return counter[number];

        //防止下标越界
        if (number > 100)
            number = 100;

        for (int i = calculatedIndex + 1; i <= number; i++) {
            counter[i] = 2 * counter[i - 1];
        }
        calculatedIndex = number;
        return counter[number];
    }
}
