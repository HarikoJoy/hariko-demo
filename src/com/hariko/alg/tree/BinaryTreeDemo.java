package com.hariko.alg.tree;

public class BinaryTreeDemo {
    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();
        BinaryNode node1 = new BinaryNode(1, "songjiang");
    }

}

class BinaryTree{
    private BinaryNode root;

    public void setRoot(BinaryNode root){
        this.root = root;
    }

    public void preOrder(){
        if(null != this.root){
            this.root.preOrder();
        }
    }

    public void infixOrder(){
        if(null != this.root){
            this.root.infixOrder();
        }
    }

    public void postOrder(){
        if(null != this.root){
            this.root.postOrder();
        }
    }

    public BinaryNode postOrderSearch(int no){
        if(null != this.root){
            this.root.postOrderSearch(no);
        }

        return null;
    }

    public void delNode(int no){
        if(null == this.root){
            return;
        }

        if(null != root){
            if(root.getNo() == no){
                root = null;
                return;
            }else{
                root.delNode(no);
            }
        }
    }
}

class BinaryNode{
    private int no;
    private String name;
    private BinaryNode left;
    private BinaryNode right;

    public BinaryNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public String toString(){
        return "BinaryNode[no = " + no + ", name = " + name + "]";
    }

    public void preOrder(){
        System.out.println(this);
        if(this.left != null){
            this.left.preOrder();
        }

        if(this.left.right != null){
            this.right.preOrder();
        }
    }

    public BinaryNode preOrderSearch(int no){
        if(this.no == no){
            return this;
        }

        BinaryNode left = null != this.left ? this.left.preOrderSearch(no) : null;
        if(null != left){
            return left;
        }

        BinaryNode right = null != this.right ? this.right.preOrderSearch(no) : null;
        if(null != right){
            return right;
        }
        return null;
    }

    public void infixOrder(){
        if(this.left != null){
            this.left.infixOrder();
        }
        System.out.println(this);
        if(this.right != null){
            this.right.infixOrder();
        }
    }

    public BinaryNode infixOrderSearch(int no){
        BinaryNode left = null != this.left ? this.left.preOrderSearch(no) : null;
        if(null != left){
            return left;
        }

        if(this.no == no){
            return this;
        }

        BinaryNode right = null != this.right ? this.right.preOrderSearch(no) : null;
        if(null != right){
            return right;
        }
        return null;
    }

    public void postOrder(){
        if(this.left != null){
            this.left.postOrder();
        }
        if(this.right != null){
            this.right.postOrder();
        }
        System.out.println(this);
    }

    public BinaryNode postOrderSearch(int no){
        BinaryNode left = null != this.left ? this.left.preOrderSearch(no) : null;
        if(null != left){
            return left;
        }

        BinaryNode right = null != this.right ? this.right.preOrderSearch(no) : null;
        if(null != right){
            return right;
        }

        if(this.no == no){
            return this;
        }
        return null;
    }

    public void delNode(int no){
        if(null != this.getLeft()){
            if(this.getLeft().getNo() == no){
                this.setLeft(null);
                return;
            }

            this.getLeft().delNode(no);
        }

        if(null != this.getRight()){
            if(this.getRight().getNo() == no){
                this.setRight(null);
                return;
            }

            this.getRight().delNode(no);
        }
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

    public BinaryNode getLeft() {
        return left;
    }

    public void setLeft(BinaryNode left) {
        this.left = left;
    }

    public BinaryNode getRight() {
        return right;
    }

    public void setRight(BinaryNode right) {
        this.right = right;
    }
}
