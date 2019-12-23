package com.hariko.alg.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HuffmanTree {

    public static void main(String[] args) {
        int[] array = {13, 7, 8, 3, 29, 6, 1};

        createHuffmanTree(array);

    }


    public static HuffmanNode createHuffmanTree(int[] array){
        //1.遍历array把每个元素构造成Node
        List<HuffmanNode> list = new ArrayList<>();

        for(int val : array){
            list.add(new HuffmanNode(val));
        }

        while (list.size() > 1){
            Collections.sort(list);
            handleList(list);
        }

        //list中只有一个元素了,就是根结点
        return list.get(0);
    }

    public static List<HuffmanNode> handleList(List<HuffmanNode> list){
        //1.取出权值最小的两棵树
        HuffmanNode left = list.get(0);
        HuffmanNode right = list.get(1);

        HuffmanNode parent = new HuffmanNode(left.value + right.value);
        parent.left = left;
        parent.right = right;

        list.remove(left);
        list.remove(right);
        list.add(parent);

        return list;
    }
}

class HuffmanNode implements Comparable{
    int value;
    HuffmanNode left;
    HuffmanNode right;

    public HuffmanNode(int value){
        this.value = value;
    }

    @Override
    public String toString() {
        return "HuffmanNode{" +
                "value=" + value +
                '}';
    }

    @Override
    public int compareTo(Object o) {

        return this.value - ((HuffmanNode) o).value;
    }
}
