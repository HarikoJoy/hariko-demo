package com.hariko.alg;

public class DoubleLinkedList {
    private DoubleLinkedNode head = new DoubleLinkedNode(0, "", "");

    public void addIndex(int index, DoubleLinkedNode doubleLinkedNode){
        if(index < 1){
            return;
        }

        DoubleLinkedNode curNode = head;
        while (true){
            if(curNode.getNext().getNo() < index){
                curNode = curNode.getNext();
            }else{
                break;
            }
        }

        DoubleLinkedNode temp = curNode.getNext();
        curNode.setNext(doubleLinkedNode);
        doubleLinkedNode.setNext(temp);
    }

    public void delete(int no){
        DoubleLinkedNode curNode = head;

        while (true){
            if(curNode.getNext() == null){
                return;
            }

            if(curNode.getNext().getNo() == no){
                break;
            }

            curNode = curNode.getNext();
        }

        DoubleLinkedNode deleteNode = curNode.getNext();
        DoubleLinkedNode temp = curNode.getNext().getNext();
        curNode.setNext(temp);
        deleteNode.setNext(null);
    }

    public void addLast(DoubleLinkedNode doubleLinkedNode){
        DoubleLinkedNode curNode = head;

        while (true){
            if(curNode.getNext() == null){
                break;
            }
            curNode = curNode.getNext();
        }

        curNode.setNext(doubleLinkedNode);
        doubleLinkedNode.setPrev(curNode);
    }

    public void list(){
        if(head.getNext() == null){
            return;
        }

        DoubleLinkedNode curNode = head.getNext();
        while (true){
            System.out.println(curNode);

            if(null == curNode.getNext()){
                break;
            }

            curNode = curNode.getNext();
        }
    }


}


class DoubleLinkedNode{
    private int no;
    private String name;
    private String nickName;

    private DoubleLinkedNode next;
    private DoubleLinkedNode prev;

    public DoubleLinkedNode(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "DoubleLinkedNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public DoubleLinkedNode getNext() {
        return next;
    }

    public void setNext(DoubleLinkedNode next) {
        this.next = next;
    }

    public DoubleLinkedNode getPrev() {
        return prev;
    }

    public void setPrev(DoubleLinkedNode prev) {
        this.prev = prev;
    }
}
