package com.hariko.alg.tree.sort;

public class BinarySortTreeDemo {
    public static void main(String[] args) {
        int[] array = new int[]{7, 3, 10, 12, 5, 1, 9, 2};
        BinarySortTree binarySortTree = new BinarySortTree();

        for(int i = 0; i < array.length; i++){
            binarySortTree.add(new BinarySortNode(array[i]));
        }
        System.out.println("删除前:");
        binarySortTree.infixOrder();
        binarySortTree.del(7);
        System.out.println("删除后:");
        binarySortTree.infixOrder();
    }
}

class BinarySortTree{
    private BinarySortNode root;

    private void setRoot(BinarySortNode root){
        this.root = root;
    }

    public void add(BinarySortNode binarySortNode){
        if(null == root){
            root = binarySortNode;
        }else{
            root.add(binarySortNode);
        }
    }


    //第一种情况:删除子结点
    //1.1需要先找到要册删除结点 target
    //1.2找到要删除的结点的父结点 parent
    //1.3确定target是parent的左子树还是右子树
    //1.4左子结点 parent.left = null 右子结点 parnet.right = null
    //第二种情况:删除只有一个子树的结点
    //2.1需要先找到要册删除结点 target
    //2.2找到要删除的结点的父结点 parent
    //2.3确定target的子结点是左子结点还是右子结点
    //2.4确定target是parent的左子结点还是右子结点
    //2.4.1 target是parent的左结点,target有左结点  parent.left = target.left
    //2.4.2 parent.left = target.right 2.4.3 2.4.4
    //第三种情况:删除有两棵子树的结点
    //3.1需要先找到要册删除结点 target
    //3.2找到要删除的结点的父结点 parent
    //3.3从target的右子树找到最小结点 保存为temp 然后删除最小结点 target.value = temp.value
    public BinarySortNode search(int value){
        if(root == null){
            return null;
        }

        return this.root.search(value);
    }

    public BinarySortNode searchParentNode(int value){
        if(root == null){
            return null;
        }
        return this.root.searchParentNode(value);
    }

    public int delRightTreeMin(BinarySortNode binarySortNode){
        BinarySortNode target = binarySortNode;

        while (target.left != null){
            target = target.left;
        }
        //这时target就指向了最小值
        del(target.value);
        return target.value;
    }

    public void del(int value){
        if(root == null){
            return;
        }

        BinarySortNode target = this.root.search(value);
        if(target == null){
            return;
        }

        //这种情况下只有父结点满足情况
        if(root.left == null && root.right == null){
            //如果当前二叉排序树只有一个结点,既当前结点没有父结点
            target = null;
            return;
        }

        BinarySortNode parent = this.root.searchParentNode(value);
        //是叶子结点
        if(target.left == null && target.right == null){
            if(target == parent.left){//是左结点
                parent.left = null;
            }

            if(target == parent.right){//是右结点
                parent.right = null;
            }
        }else if(target.left != null && target.right != null){
            int delVal = delRightTreeMin(target.right);
            target.value = delVal;
        }else{
            //要删除的结点只有左子树
            if(target.left != null){

                //树只有两个结点,一个根结点,一个左子结点
                if(root == target){
                    root = target.left;
                    return;
                }

                //要删除的结点是父结点的左子树
                if(parent.left == target){
                    parent.left = target.left;
                }
                if(parent.right == target){
                    parent.right = target.left;
                }
            }

            if(target.right != null){

                //树只有两个结点,一个根结点,一个右子结点
                if(root == target){
                    root = target.right;
                    return;
                }

                if(parent.left == target){
                    parent.left = target.right;
                }
                if(parent.right == target){
                    parent.right = target.right;
                }
            }
        }
    }

    public void infixOrder(){
        if(null == root){
            return;
        }

        root.infixOrder();
    }
}

class BinarySortNode{
    int value;
    BinarySortNode left;
    BinarySortNode right;

    public BinarySortNode(int value){
        this.value = value;
    }

    @Override
    public String toString() {
        return "BinarySortNode{" +
                "value=" + value +
                '}';
    }

    public BinarySortNode search(int value){
        if(this.value == value){
            return this;
        }

        if(this.value > value){
            if(this.left == null){
                return null;
            }
            return this.left.search(value);
        }else{
            if(this.right == null){
                return null;
            }
            return this.right.search(value);
        }

    }

    public BinarySortNode searchParentNode(int value){
        //如果当前结点就是当前要删除结点的父结点
        if((this.left != null && this.left.value == value) || (this.right != null && this.right.value == value)){
            return this;
        }else {
            //如果查找的值小于当前结点的值且当前结点的左子结点不为空
            if(value < this.value && this.left != null){
                return this.left.searchParentNode(value);
            }else if(value >= this.value && this.right != null){
                return this.right.searchParentNode(value);
            }else {
                return null;
            }
        }
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

    public void add(BinarySortNode binarySortNode){
        if(null == binarySortNode){
            return;
        }

        //判断传入结点的值和当前子树的根结点的值的关系
        if(binarySortNode.value < this.value){
            if(this.left == null){
                this.left = binarySortNode;
            }else{
                this.left.add(binarySortNode);
            }
        }else{
            if(this.right == null){
                this.right = binarySortNode;
            }else{
                this.right.add(binarySortNode);
            }
        }
    }
}
