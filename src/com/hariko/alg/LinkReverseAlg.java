package com.hariko.alg;

public class LinkReverseAlg {
    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList();
        for(int i = 1; i <= 10; i++){
            linkedList.add(i);
        }

        System.out.println("反转前的链表输出结果为: ");
        linkedList.print();

        System.out.println("反转后的链表输出结果为: ");
        linkedList.reverse();
        linkedList.print();
    }
}

class LinkedList{
    LinkedNode head;

    public void add(int value){
        if(null == head){
            head = new LinkedNode(value);
            return;
        }

        LinkedNode cur = head;

        while (cur.next != null){
            cur = cur.next;
        }

        LinkedNode linkedNode = new LinkedNode(value);
        cur.next = linkedNode;
    }

    public void reverse(){
        if(null == head){
            return;
        }

        LinkedNode cur = head;
        LinkedNode temp = null;
        head = head.next;
        cur.next = null;

        while (null != head){
            temp = head.next;

            head.next = cur;

            cur = head;

            head = temp;
        }

        head = cur;
    }

    public void print(LinkedNode node){
        if(null == node){
            return;
        }

        LinkedNode cur = node;
        while (null != cur){
            System.out.println(cur);
            cur = cur.next;
        }
    }

    public void print(){
        this.print(this.head);
    }
}

class LinkedNode{
    int value;
    LinkedNode next;

    public LinkedNode(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "LinkedNode{" +
                "value=" + value +
                '}';
    }
}

