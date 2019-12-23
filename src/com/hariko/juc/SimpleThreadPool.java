package com.hariko.juc;

import java.util.LinkedList;

public class SimpleThreadPool {

    private static int size;
    private static int threadSize;

    private static int DEFAULT_TASK_SIZE = 10;
    private static int DEFAULT_THREAD_SIZE = 30;

    private static volatile int sequence = 0;

    private final static String THREAD_PREFIX = "SIMPLE_THREAD_POOL-";

    private static final ThreadGroup THREAD_GROUP = new ThreadGroup("POOL_GROUP");

    private static LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();
    private static LinkedList<Runnable> THREAD_QUEUE = new LinkedList<>();

    private final static int DEFAULT_SIZE = 10;

    private DiscardPolicy discardPolicy;

    private static final DiscardPolicy DEFAULT_DISCARD_POLICY = () -> {throw new DiscardException("discard exception");};

    public SimpleThreadPool() {
        this(DEFAULT_TASK_SIZE, DEFAULT_THREAD_SIZE, DEFAULT_DISCARD_POLICY);
    }

    public SimpleThreadPool(int size, int threadSize, DiscardPolicy discardPolicy) {
        this.size = size;
        this.threadSize = threadSize;
        this.discardPolicy = discardPolicy;
        init();
    }

    private void init() {
        for (int i = 0; i < size; i++) {
            createWorkTask();
        }
    }

    private void createWorkTask() {
        WorkerTask workerTask = new WorkerTask(THREAD_GROUP, THREAD_PREFIX + (sequence++));
        workerTask.start();
        THREAD_QUEUE.add(workerTask);
    }

    public void submit(Runnable runnable) {
        synchronized (TASK_QUEUE) {
            if(TASK_QUEUE.size() > size){
                discardPolicy.discard();
            }

            TASK_QUEUE.addLast(runnable);
            TASK_QUEUE.notifyAll();
        }
    }

    public interface DiscardPolicy {
        void discard() throws DiscardException;
    }

    public static class DiscardException extends RuntimeException {
        public DiscardException(String message) {
            super(message);
        }
    }

    private enum TaskState {
        FREE, RUNNING, BLOCKED, DEAD
    }

    private static class WorkerTask extends Thread {
        private volatile TaskState taskState = TaskState.FREE;

        public WorkerTask(ThreadGroup threadGroup, String name) {
            super(threadGroup, name);
        }

        public TaskState getTaskState() {
            return this.taskState;
        }

        @Override
        public void run() {
            OUTER:
            while (this.taskState != TaskState.DEAD) {
                Runnable runnable;
                synchronized (TASK_QUEUE) {
                    while (TASK_QUEUE.isEmpty()) {
                        try {
                            TASK_QUEUE.wait();
                        } catch (InterruptedException e) {
                            break OUTER;
                        }
                    }
                    runnable = TASK_QUEUE.removeFirst();
                    if (null != runnable) {
                        taskState = TaskState.RUNNING;
                        runnable.run();
                        taskState = TaskState.FREE;
                    }
                }
            }
        }

        public void close() {
            this.taskState = TaskState.DEAD;
        }
    }
}
