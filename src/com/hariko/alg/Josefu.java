package com.hariko.alg;

public class Josefu {

    public static void main(String[] args) {
        CirculeSingleLinkedList circuleSingleLinkedList = new CirculeSingleLinkedList();
        circuleSingleLinkedList.addBoy(5);

        circuleSingleLinkedList.print();
    }
}

class CirculeSingleLinkedList{
    private Boy first = new Boy(-1);

    public void addBoy(int boyNum){
        if(boyNum < 1){
            return;
        }

        Boy curBoy = null;
        for (int i = 1; i <= boyNum; i++){
            Boy boy = new Boy(i);

            if(i == 1){
                first = boy;
                first.setNext(boy);
                curBoy = first;
            }else{
                curBoy.setNext(boy);
                boy.setNext(first);
                curBoy = boy;
            }
        }
    }

    public void print(){
        if(first == null){
            return;
        }
        Boy curBoy = first;


        while (first.getNext() != null){
            System.out.printf("小孩的编号为: %d \t", curBoy.getNo());

            if(curBoy.getNext() == first){
                break;
            }

            curBoy = curBoy.getNext();
        }
    }
}

class Boy{
    private int no;
    private Boy next;
    public Boy(int no){
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Boy getNext() {
        return next;
    }

    public void setNext(Boy next) {
        this.next = next;
    }
}
