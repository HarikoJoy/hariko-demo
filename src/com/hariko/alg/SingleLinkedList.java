package com.hariko.alg;

public class SingleLinkedList {
    private SingleLinkedNode head = new SingleLinkedNode(0, "", "");

    public void addIndex(int index, SingleLinkedNode singleLinkedNode){
        if(index < 1){
            return;
        }

        SingleLinkedNode curNode = head;
        while (true){
            if(curNode.getNext().getNo() < index){
                curNode = curNode.getNext();
            }else{
                break;
            }
        }

        SingleLinkedNode temp = curNode.getNext();
        curNode.setNext(singleLinkedNode);
        singleLinkedNode.setNext(temp);
    }

    public void delete(int no){
        SingleLinkedNode curNode = head;

        while (true){
            if(curNode.getNext() == null){
                return;
            }

            if(curNode.getNext().getNo() == no){
                break;
            }

            curNode = curNode.getNext();
        }

        SingleLinkedNode deleteNode = curNode.getNext();
        SingleLinkedNode temp = curNode.getNext().getNext();
        curNode.setNext(temp);
        deleteNode.setNext(null);
    }

    public void addLast(SingleLinkedNode singleLinkedNode){
        SingleLinkedNode curNode = head;

        while (true){
            if(curNode.getNext() == null){
                break;
            }
            curNode = curNode.getNext();
        }

        curNode.setNext(singleLinkedNode);
    }

    public void list(){
        if(head.getNext() == null){
            return;
        }

        SingleLinkedNode curNode = head.getNext();
        while (true){
            System.out.println(curNode);

            if(null == curNode.getNext()){
                break;
            }

            curNode = curNode.getNext();
        }
    }

    public static void main(String[] args) {
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        singleLinkedList.addLast(new SingleLinkedNode(1, "zhangsan", "A"));
        singleLinkedList.addLast(new SingleLinkedNode(2, "lisi", "B"));
        singleLinkedList.addLast(new SingleLinkedNode(3, "wangwu", "C"));
        singleLinkedList.addLast(new SingleLinkedNode(4, "zhaoliu", "D"));
        singleLinkedList.addLast(new SingleLinkedNode(7, "sunqi", "G"));

        singleLinkedList.addIndex(5, new SingleLinkedNode(5, "songwu", "E"));
        singleLinkedList.addIndex(3, new SingleLinkedNode(3, "liangsan", "F"));

        singleLinkedList.delete(3);
        singleLinkedList.list();
    }
}

class SingleLinkedNode{
    private int no;
    private String name;
    private String nickName;

    private SingleLinkedNode next;

    public SingleLinkedNode(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "SingleLinkedNode{" +
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

    public SingleLinkedNode getNext() {
        return next;
    }

    public void setNext(SingleLinkedNode next) {
        this.next = next;
    }
}
