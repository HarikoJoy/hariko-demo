package com.hariko.alg.tree.sort;

public class AvlTreeDemo {

    public static void main(String[] args) {
        AvlTree<Integer> tree = new AvlTree<Integer>();
        AvlNode root = null;
        int[] array = {20, 8, 2, 4, 5, 6, 28, 18, 19, 33, 25, 26};
        for(int i = 0; i < array.length; i++){
            root = tree.insert(root, array[i]);
        }

        tree.infixOrder(root);
    }
}


class AvlTree<T extends Comparable>{
    private AvlNode<T> root;

    public AvlNode<T> getRoot() {
        return root;
    }

    public void setRoot(AvlNode<T> root) {
        this.root = root;
    }

    public AvlTree() {
    }

    public AvlTree(AvlNode<T> root) {
        this.root = root;
    }

    //计算指定结点的高度
    public int height(AvlNode avlNode){
        return avlNode == null ? 0 : avlNode.getHeight();
    }
    //计算树的高度
    public int height(){
        return height(root);
    }

    private int getMax(int h1, int h2){
        return h1 > h2 ? h1 : h2;
    }

    public void infixOrder(AvlNode avlNode){
        if(null == avlNode){
            return;
        }

        infixOrder(avlNode.getLeft());

        System.out.println(avlNode);

        infixOrder(avlNode.getRight());
    }

    public AvlNode insert(AvlNode node, T data){
        if(node == null){
            node = new AvlNode(data);
        }else{
            int compared = data.compareTo(node.getData());

            if(compared > 0){
                //把添加结点添加到右子树上
                AvlNode resNode = insert(node.getRight(), data);
                node.setRight(resNode);

                if(height(node.getRight()) - height(node.getLeft()) > 1){
                    if(data.compareTo(node.getRight().getData()) > 0){
                        //右右插入  左旋转
                        node = this.rr(node);
                    }else{
                        //右左插入  左右旋转
                        node = this.rl(node);
                    }
                }
            }else if(compared < 0){
                AvlNode resNode = insert(node.getLeft(), data);
                node.setLeft(resNode);

                if(height(node.getRight()) - height(node.getLeft()) > 1){
                    if(data.compareTo(node.getLeft().getData()) > 0){
                        //左右插入  右左旋转
                        node = this.lr(node);
                    }else{
                        //左左插入  右旋转
                        node = this.ll(node);
                    }
                }
            }else{

            }
        }

        //重新调整结点的高度
        node.setHeight(this.getMax(height(node.getLeft()), height(node.getRight())) + 1);
        return node;
    }

    public AvlNode remove(AvlNode node, T data){
        if(node == null){
            return null;
        }

        int compared = data.compareTo(node.getData());
        if(compared < 0){ //从左子树上删除
            node.setLeft(remove(node.getLeft(), data));
            //从左子树上删除,右子树一定不小于左子树高度
            if(height(node.getRight()) - height(node.getLeft()) > 1){
                if(height(node.getRight().getLeft()) > height(node.getRight().getRight())){
                    node = this.rl(node);
                }else{
                    node = this.rr(node);
                }
            }
        }else if(compared > 0){
            node.setRight(remove(node.getRight(), data));

            if(height(node.getLeft()) - height(node.getRight()) > 1){
                if(height(node.getLeft().getRight()) > height(node.getLeft().getLeft())){
                    node = this.lr(node);
                }else{
                    node = this.ll(node);
                }
            }
        }else if(compared == 0){
            if(node.getLeft() != null && node.getRight() != null){
                //将失衡结点的data更改为直接后继结点的值
                node.setData(findNextNode(node).getData());
                //将问题修改为删除直接后继结点
                node.setRight(remove(node.getRight(), data));
            }else{
                //只有左子树或者只有右子树或者叶子结点的情况
                node = (node.getLeft() == null) ? node.getRight() : node.getLeft();
            }
        }

        if(node != null){
            node.setHeight(getMax(height(node.getLeft()), height(node.getRight())) + 1);
        }

        return node;
    }

    //得到给定结点的后继结点(中序遍历)实际上是给定结点的右子树上的最小结点
    public AvlNode findNextNode(AvlNode avlNode){
        if(null == avlNode){
            return null;
        }

        AvlNode temp = avlNode.getRight();
        while(temp != null && temp.getLeft() != null){
            temp = temp.getLeft();
        }

        return temp;
    }

    public AvlNode findPreviousNode(AvlNode avlNode){
        if(null == avlNode){
            return null;
        }

        AvlNode temp = avlNode.getLeft();
        while(null != temp && temp.getRight() != null){
            temp = temp.getRight();
        }

        return temp;
    }

    /**
     * LL类型:在失衡结点的左孩子的左子树插入结点 往右旋转
     * @param avlNode 失衡结点
     * @return 旋转后根结点
     */
    public AvlNode ll(AvlNode avlNode){
        //定义一个变量保存失衡结点的左子树
        AvlNode lChild = avlNode.getLeft();
        //将失衡结点的左结点的右子树作为失衡结点的左子树
        avlNode.setLeft(lChild.getRight());
        //将我们失衡结点的左子树作为右子树的左子树
        lChild.setRight(avlNode);

        //重新计算旋转后的高度
        avlNode.setHeight(getMax(height(avlNode.getLeft()), height(avlNode.getRight())) + 1);
        lChild.setHeight(getMax(height(lChild.getLeft()), height(lChild.getRight())) + 1);
        return lChild;
    }

    /**
     * RR类型:在失衡结点的右孩子的右子树插入结点 往左旋转
     * @param avlNode 失衡结点
     * @return 旋转后根结点
     */
    public AvlNode rr(AvlNode avlNode){
        //定义一个变量保存失衡结点的右子树
        AvlNode rChild = avlNode.getRight();
        //将失衡结点的右结点的左子树作为失衡结点的右子树
        avlNode.setRight(rChild.getLeft());
        //将我们失衡结点的左子树作为右子树的左子树
        rChild.setLeft(avlNode);

        //重新计算旋转后的高度
        avlNode.setHeight(getMax(height(avlNode.getLeft()), height(avlNode.getRight())) + 1);
        rChild.setHeight(getMax(height(rChild.getLeft()), height(rChild.getRight())) + 1);
        return rChild;
    }

    /**
     * RL类型:在失衡结点的右孩子的左子树插入结点 先左后右旋转
     * @param avlNode 失衡结点
     * @return 旋转后根结点
     */
    public AvlNode rl(AvlNode avlNode){
        avlNode.setLeft(ll(avlNode));

        return rr(avlNode);
    }

    /**
     * LR类型:在失衡结点的左孩子的右子树插入结点 先右后左旋转
     * @param avlNode 失衡结点
     * @return 旋转后根结点
     */
    public AvlNode lr(AvlNode avlNode){
        avlNode.setRight(rr(avlNode));

        return ll(avlNode);
    }
}


class AvlNode<T extends Comparable>{
    private T data;
    private AvlNode<T> left;
    private AvlNode<T> right;
    private int height;

    public AvlNode(T data){
        this(data, null, null);
    }

    public AvlNode(T data, AvlNode<T> left, AvlNode<T> right) {
        this(data, left, right, 0);
    }

    public AvlNode(T data, AvlNode<T> left, AvlNode<T> right, int height) {
        this.data = data;
        this.left = left;
        this.right = right;
        this.height = height;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public AvlNode<T> getLeft() {
        return left;
    }

    public void setLeft(AvlNode<T> left) {
        this.left = left;
    }

    public AvlNode<T> getRight() {
        return right;
    }

    public void setRight(AvlNode<T> right) {
        this.right = right;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "AvlNode{" +
                "data=" + data +
                ", height=" + height +
                '}';
    }
}
