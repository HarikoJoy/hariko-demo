package com.hariko.jdk8.fork;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 一.ForkJoinPool: ExecutorService的实现类,负责工作线程的管理、任务队列的维护,以及控制整个任务调度流程
 * 1.1: 通过invoke方法提交的任务,调用线程直到任务执行完成才会返回,也就是一个同步方法,有返回结果
 * 1.2: 通过execute方法提交的任务,调用线程立即返回,是一个异步方法,没有返回结果
 * 1.3: 通过submit方法提交的任务,调用线程立即返回,是一个异步方法,有返回结果,返回Future的实现类,可以通过get获取结果
 * 二.ForkJoinTask: Future接口的实现类,fork是其核心方法,用于分解其任务并异步执行,而join方法在任务结果计算完毕才会运行，用来合并计算结果
 * 2.1: 支持任务的分解,支持任务的合并
 * 2.2： 它的两个子类
 * 2.2.1: RecursiveAction: 表示具有返回结果的ForkJoin任务
 * 2.2.2: RecursiveTask: 表示没有返回结果的ForkJoin任务
 * 三.ForkJoinWorkerThread: Thread的子类,作为线程池中的工作线程(Worker)执行任务
 * 3.1: 每个Worker都有一个自己的任务队列(WorkQueue),所以要对一般的Thread做一些特性化处理
 * 四.WorkQueue: 任务队列,用于保存任务
 * 4.1: 在ForkJoinPool中维护一个WorkQueue[]数组,它会在外部首次提交任务时进行初始化,当通过线程池的外部方法(submit、invoke、execute)提交任务时,如果没有初始化,
 *      然后根据数组大小和线程随机数(ThreadLocalRandom.probe)等信息,计算出任务队列在的数组索引(这个索引一定是偶数),如果索引处没有任务队列,则初始化一个,在将任务入队,
 *      也就是说,通过外部方法提交的任务一定是在偶数队列,没有绑定工作线程
 * 4.2: WorkQueue是一个双端队列,当工作线程从自己的任务队列中取任务时,默认LIFO方式从栈顶取任务,当从不是它的任务队列取任务时,则是FIFO的方式
 * 4.3: 有工作线程的任务队列,数组下标永远是奇数,叫做task queue,该队列中的任务均由工作线程调用产生(调用Fork方法)
 * 4.4: 没有工作线程的任务队列,数组下标永远是偶数,叫做submissions queue,该队列中的任务全部由其它线程提交,也就是由非工作线程调用execute、invoke、submit
 *      或者是FutureTask.fork方法
 */
public class ForJoinDemo {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ArraySumTask arraySumTask = new ArraySumTask(new int[1000], 0, 999);

        ForkJoinTask forkJoinTask = forkJoinPool.submit(arraySumTask);

        if(forkJoinTask.isCompletedAbnormally()){
            System.out.println(forkJoinTask.getException());
        }

        try {
            System.out.println(forkJoinTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class ArraySumTask extends RecursiveTask<Long> {
    private final int[] array;
    private final int begin;
    private final int end;

    private static final int THRESHOLD = 100;

    ArraySumTask(int[] array, int begin, int end) {
        this.array = array;
        this.begin = begin;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long sum = 0;
        if(end - begin + 1 < THRESHOLD){
            for(int i = begin; i < end; i++){
                sum += array[i];
            }
        }else{
            int middle = (end + begin) / 2;
            ArraySumTask sumTaskLeft = new ArraySumTask(this.array, begin, middle);
            ArraySumTask sumTaskRight = new ArraySumTask(this.array, middle + 1, end);

            sumTaskLeft.fork();
            sumTaskRight.fork();

            long sumLeft = sumTaskLeft.join();
            long sumRight = sumTaskRight.join();
            sum = sumLeft + sumRight;
        }
        return sum;
    }
}