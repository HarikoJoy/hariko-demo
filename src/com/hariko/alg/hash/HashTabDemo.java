package com.hariko.alg.hash;

public class HashTabDemo {


}

class HashTab{
    EmpLinkedList[] empLinkedListArray = new EmpLinkedList[10];
    private int size;

    public HashTab(int size){
        this.size = size;
        this.empLinkedListArray = new EmpLinkedList[size];
    }

    public void add(Emp emp){
        int index = hashFun(emp.id);
        empLinkedListArray[index].add(emp);
    }

    public Emp findById(int id) {
        int index = hashFun(id);

        return empLinkedListArray[index].findById(id);
    }


    public int hashFun(int id){
        return id % size;
    }

    public void list(){
        for(int i = 0; i < size; i++){
            empLinkedListArray[i].list();
        }
    }
}

class Emp{
    public int id;
    public String name;
    public Emp next;

    public Emp(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

class EmpLinkedList{
    //头指针,指向第一个emp
    private Emp head = null;

    public void add(Emp emp){
        if(head == null){
            head = emp;
        }else{
            Emp curEmp = head;
            while(null != curEmp.next){
                curEmp = curEmp.next;
            }

            curEmp.next = emp;
        }
    }

    public Emp findById(int id){
        if(null == head){
            return null;
        }

        Emp curEmp = head;
        while(null != curEmp){
            if(curEmp.id == id){
                return curEmp;
            }
            curEmp = curEmp.next;
        }

        return null;
    }

    public void list(){
        if(null == head){
            System.out.println("当前链表为空");
            return;
        }
        Emp curEmp = head;
        while(null != curEmp){
            System.out.println("=>id = " + curEmp.id + ",  name = " + curEmp.name);
            curEmp = curEmp.next;
        }
    }
}