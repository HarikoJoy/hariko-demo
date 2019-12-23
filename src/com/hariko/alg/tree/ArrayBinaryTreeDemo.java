package com.hariko.alg.tree;

public class ArrayBinaryTreeDemo {

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7};
        ArrayBinaryTree arrayBinaryTree = new ArrayBinaryTree(array);
        arrayBinaryTree.preOrder(0);
    }
}

/**
 * 1.顺序二叉树通常只考虑完全二叉树
 * 2.第n个元素的左子结点为 2 * n + 1
 * 3.第n个元素的右子结点为 2 * n + 2
 * 4.第n个元素的父结点为 (n - 1) / 2
 */
class ArrayBinaryTree{
    private int[] array;
    public ArrayBinaryTree(int[] array){
        this.array = array;
    }

    public void preOrder(int index){
        if(null == array || array.length == 0){
            return;
        }

        System.out.print(array[index] + "\t");

        if(index * 2 + 1 < array.length) {
            preOrder(2 * index + 1);
        }

        if(index * 2 + 2 < array.length){
            preOrder(2 * index + 2);
        }
    }

    public void infixOrder(int index){
        if(null == array || array.length == 0){
            return;
        }

        if(index * 2 + 1 < array.length) {
            infixOrder(2 * index + 1);
        }

        System.out.print(array[index] + "\t");

        if(index * 2 + 2 < array.length){
            infixOrder(2 * index + 2);
        }
    }

}

