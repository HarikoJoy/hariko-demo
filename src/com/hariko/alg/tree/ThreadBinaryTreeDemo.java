package com.hariko.alg.tree;

public class ThreadBinaryTreeDemo {
    public static void main(String[] args) {
        ThreadNode threadNode_1 = new ThreadNode(1, "AAA");
        ThreadNode threadNode_2 = new ThreadNode(3, "BBB");
        ThreadNode threadNode_3 = new ThreadNode(6, "CCC");
        ThreadNode threadNode_4 = new ThreadNode(8, "DDD");
        ThreadNode threadNode_5 = new ThreadNode(10, "EEE");
        ThreadNode threadNode_6 = new ThreadNode(14, "FFF");

        threadNode_1.setLeft(threadNode_2);
        threadNode_1.setRight(threadNode_3);

        threadNode_2.setLeft(threadNode_4);
        threadNode_2.setRight(threadNode_5);

        threadNode_3.setLeft(threadNode_6);

        ThreadBinaryTree threadBinaryTree = new ThreadBinaryTree();
        threadBinaryTree.setRoot(threadNode_1);

        threadBinaryTree.threadNode(threadNode_1);

        //测试10号结点
        ThreadNode threadNode = threadNode_5.getLeft();
        System.out.println("10号结点的前驱是:" + threadNode);

        threadBinaryTree.threadList();
    }
}

class ThreadBinaryTree{
    private ThreadNode root;
    //为了实现线索化,需要创建一个指向当前结点前驱结点的指针
    private ThreadNode prev;

    public void setRoot(ThreadNode root){
        this.root = root;
    }

    public void threadList(){
        ThreadNode node = root;
        while (node != null){
            //找到第一个leftType == 2的结点
            //当leftType == 2的时候,说明该结点是线索化的有效结点
            while (node.getLeftType() == 0){
                node = node.getLeft();
            }

            System.out.println(node);
            //如果当前结点的右结点是后继结点,就一直输出
            while (node.getRightType() == 1){
                node = node.getRight();
                System.out.println(node);
            }

            node = node.getRight();
        }
    }

    //对当前结点线索化
    public void threadNode(ThreadNode node){
        if(null == node){
            return;
        }

        //1.先线索化左子树
        threadNode(node.getLeft());
        //2.然后线索化当前结点
        //2.1如果当前结点的左子树为空
        if(node.getLeft() == null){
            //因为左子树为叶子结点,所以为前驱结点
            node.setLeft(prev);
            node.setLeftType(1);
        }

        //处理后继结点
        if(prev != null && prev.getRight() == null){
            prev.setRight(node);
            prev.setRightType(1);
        }
        //每次处理一个结点后,指针向后移
        prev = node;
        //3.最后线索化右子树
        threadNode(node.getRight());
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

    public ThreadNode postOrderSearch(int no){
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

class ThreadNode{
    private int no;
    private String name;
    private ThreadNode left;
    private ThreadNode right;

    //1.leftType == 1代表的是左子树,如果为2代表的是前驱结点
    //2.rightType == 1代表的是右子树,如果为2代表的是前驱结点
    private int leftType;
    private int rightType;

    public int getLeftType() {
        return leftType;
    }

    public void setLeftType(int leftType) {
        this.leftType = leftType;
    }

    public int getRightType() {
        return rightType;
    }

    public void setRightType(int rightType) {
        this.rightType = rightType;
    }

    public ThreadNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public String toString(){
        return "ThreadNode[no = " + no + ", name = " + name + "]";
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

    public ThreadNode preOrderSearch(int no){
        if(this.no == no){
            return this;
        }

        ThreadNode left = null != this.left ? this.left.preOrderSearch(no) : null;
        if(null != left){
            return left;
        }

        ThreadNode right = null != this.right ? this.right.preOrderSearch(no) : null;
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

    public ThreadNode infixOrderSearch(int no){
        ThreadNode left = null != this.left ? this.left.preOrderSearch(no) : null;
        if(null != left){
            return left;
        }

        if(this.no == no){
            return this;
        }

        ThreadNode right = null != this.right ? this.right.preOrderSearch(no) : null;
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

    public ThreadNode postOrderSearch(int no){
        ThreadNode left = null != this.left ? this.left.preOrderSearch(no) : null;
        if(null != left){
            return left;
        }

        ThreadNode right = null != this.right ? this.right.preOrderSearch(no) : null;
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

    public ThreadNode getLeft() {
        return left;
    }

    public void setLeft(ThreadNode left) {
        this.left = left;
    }

    public ThreadNode getRight() {
        return right;
    }

    public void setRight(ThreadNode right) {
        this.right = right;
    }
}
