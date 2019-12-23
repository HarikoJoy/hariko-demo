package com.hariko.alg;

public class CircleArrayQueueDemo {

    public static void main(String[] args) {

    }
}

class CircleArrayQueue{
    private int maxSize;
    private int head;
    private int tail;
    private int[] array;

    public CircleArrayQueue(int maxSize){
        this.maxSize = maxSize;
        this.array = new int[maxSize];
        this.head = 0;
        this.tail = 0;
    }

    public boolean isFull(){
        return (this.tail + 1) % maxSize == this.head;
    }

    public boolean isEmpty(){
        return this.head == this.tail;
    }

    public int size(){
        return (this.tail + maxSize - this.head) % maxSize;
    }

    public void addQueue(int value){
        if(isFull()){
            return;
        }

        array[this.tail] = value;
        this.tail = (this.tail + 1) % maxSize;
    }

    public int getQueue(){
        if(isEmpty()){
            return -1;
        }

        int value = array[this.head];
        this.head = (this.head + 1) % this.maxSize;
        return value;
    }

    public void print(){
        if(isEmpty()){
            return;
        }

        for(int i = this.head; i < this.head + this.size(); i++){
            System.out.printf("[%2d] = %d\t", i % maxSize, array[i % this.maxSize]);
        }
    }


}
