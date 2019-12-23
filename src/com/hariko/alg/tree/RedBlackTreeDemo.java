package com.hariko.alg.tree;

/**
 * 红黑树的五个性质
 * 1.每个结点要么是红的,要么是黑的
 * 2.根结点是黑的
 * 3.每个叶子结点,就是空结点是黑的
 * 4.如果一个结点是红的,那么它的两个儿子是黑的
 * 5.对每个结点,从该结点到其子孙结点的所有路径上包含相同的黑结点
 */
public class RedBlackTreeDemo {
    public static void main(String[] args) {

    }
}

class RedBlackTree {
    private static final boolean RED = false;
    private static final boolean BLACK = true;

    private RedBlackNode root;

    private transient int size = 0;

    /**
     * 插入的几种情况
     * 1.新结点位于树的根上,没有父结点,这种情况直接等于根结点,颜色是黑色
     * 2.新结点的父结点p是黑色,在这个时候,树是平衡的,不需要管
     * 3.父结点和叔叔结点都是红色
     * 4.父结点是红色,叔叔结点是黑色
     *
     * @param value
     */
    public void insert(int value) {
        RedBlackNode t = root;
        if (t == null) {
            //1.对应第一种情况
            root = new RedBlackNode(value, null);
            size = 1;
            return;
        }

        RedBlackNode parent;
        int cmp = value - t.value;
        do {
            parent = t;

            if (cmp < 0)
                t = t.left;
            else if (cmp > 0)
                t = t.right;
            else
                t.count += 1;
        } while (t != null);

        RedBlackNode e = new RedBlackNode(value, parent);
        if (cmp < 0)
            parent.left = e;
        else
            parent.right = e;
        fixAfterInsertion(e);
        size++;
    }

    private void fixAfterInsertion(RedBlackNode x) {
        //默认插入的结点都是红色的,红色的才有可能平衡
        x.color = RED;
        //2.如果不满足while里面的情况,也就是插入结点的父结点是黑色的,不需要做调整
        while (x != null && x != root && x.parent.color == RED) {
            //插入结点的父亲是其祖父结点的左子树
            if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
                RedBlackNode y = rightOf(parentOf(parentOf(x)));
                //3.父结点和叔叔结点都是红色
                if (colorOf(y) == RED) {
                    //调整父结点为黑色
                    setColor(parentOf(x), BLACK);
                    //调整叔叔为黑色
                    setColor(y, BLACK);
                    //调整父亲的父亲为红色
                    setColor(parentOf(parentOf(x)), RED);
                    //使当前结点为父亲的父亲,也就是祖父
                    x = parentOf(parentOf(x));
                } else {
                    //4.父结点是红色,叔叔结点是黑色
                    //插入结点x是它的父亲的右子树,向左旋转
                    if (x == rightOf(parentOf(x))) {
                        x = parentOf(x);
                        //以插入结点的父结点作为旋转点
                        //旋转完成后,插入的结点(红)变为父结点,原来的父结点(红)变为左孩子
                        rotateLeft(x);
                    }
                    setColor(parentOf(x), BLACK); //把父结点变为黑,叔叔也是黑
                    setColor(parentOf(parentOf(x)), RED); //把父亲的父亲变为红
                    rotateRight(parentOf(parentOf(x))); //以父亲的父亲为基准旋转
                }
            } else {
                RedBlackNode y = leftOf(parentOf(parentOf(x)));
                if (colorOf(y) == RED) {
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                } else {
                    if (x == leftOf(parentOf(x))) {
                        x = parentOf(x);
                        rotateRight(x);
                    }
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    rotateLeft(parentOf(parentOf(x)));
                }
            }
        }
        root.color = BLACK;
    }

    private static <K, V> boolean colorOf(RedBlackNode p) {
        return (p == null ? BLACK : p.color);
    }

    private static RedBlackNode parentOf(RedBlackNode p) {
        return (p == null ? null : p.parent);
    }

    private static <K, V> void setColor(RedBlackNode p, boolean c) {
        if (p != null)
            p.color = c;
    }

    private static RedBlackNode leftOf(RedBlackNode p) {
        return (p == null) ? null : p.left;
    }

    private static RedBlackNode rightOf(RedBlackNode p) {

        return (p == null) ? null : p.right;
    }

    /**
     * 这里分为几种情况
     * 1.这棵子树只有两个结点,也就是p只有一棵右子树
     *
     * @param p
     */
    private void rotateLeft(RedBlackNode p) {
        if (p != null) {
            RedBlackNode r = p.right;
            p.right = r.left;
            if (r.left != null)
                r.left.parent = p;
            r.parent = p.parent;
            if (p.parent == null)
                root = r;
            else if (p.parent.left == p)
                p.parent.left = r;
            else
                p.parent.right = r;
            r.left = p;
            p.parent = r;
        }
    }

    private void rotateRight(RedBlackNode p) {
        if (p != null) {
            RedBlackNode l = p.left;
            p.left = l.right;
            if (l.right != null) l.right.parent = p;
            l.parent = p.parent;
            if (p.parent == null)
                root = l;
            else if (p.parent.right == p)
                p.parent.right = l;
            else p.parent.left = l;
            l.right = p;
            p.parent = l;
        }
    }

    public void remove(int value) {
        RedBlackNode p = search(value);
        if (p == null)
            return;

        remove(p);
    }

    //后继
    static RedBlackNode successor(RedBlackNode t) {
        if (t == null)
            return null;
        else if (t.right != null) {
            //右子树不为空,获取右子树的最小结点,也就是右子树的最小的左子树
            RedBlackNode p = t.right;
            while (p.left != null)
                p = p.left;
            return p;
        } else {
            RedBlackNode p = t.parent;
            RedBlackNode ch = t;
            while (p != null && ch == p.right) {
                ch = p;
                p = p.parent;
            }
            return p;
        }
    }

    //前驱
    static RedBlackNode predecessor(RedBlackNode t) {
        if (t == null)
            return null;
        else if (t.left != null) {
            //左子树不为空,获取左子树的最大结点,也就是左子树的最大右子树
            RedBlackNode p = t.left;
            while (p.right != null)
                p = p.right;
            return p;
        } else {
            RedBlackNode p = t.parent;
            RedBlackNode ch = t;
            while (p != null && ch == p.left) {
                ch = p;
                p = p.parent;
            }
            return p;
        }
    }

    private void remove(RedBlackNode p) {
        size--;

        // If strictly internal, copy successor's element to p and then make p
        // point to successor.
        if (p.left != null && p.right != null) {
            RedBlackNode s = successor(p);
            p.value = s.value;
            p = s;
        } // p has 2 children

        // Start fixup at replacement node, if it exists.
        RedBlackNode replacement = (p.left != null ? p.left : p.right);

        if (replacement != null) {
            // Link replacement to parent
            replacement.parent = p.parent;
            if (p.parent == null)
                root = replacement;
            else if (p == p.parent.left)
                p.parent.left = replacement;
            else
                p.parent.right = replacement;

            // Null out links so they are OK to use by fixAfterDeletion.
            p.left = p.right = p.parent = null;

            // Fix replacement
            if (p.color == BLACK)
                fixAfterDeletion(replacement);
        } else if (p.parent == null) { // return if we are the only node.
            root = null;
        } else { //  No children. Use self as phantom replacement and unlink.
            if (p.color == BLACK)
                fixAfterDeletion(p);

            if (p.parent != null) {
                if (p == p.parent.left)
                    p.parent.left = null;
                else if (p == p.parent.right)
                    p.parent.right = null;
                p.parent = null;
            }
        }
    }

    private void fixAfterDeletion(RedBlackNode x) {
        while (x != root && colorOf(x) == BLACK) {
            if (x == leftOf(parentOf(x))) {
                RedBlackNode sib = rightOf(parentOf(x));

                if (colorOf(sib) == RED) {
                    setColor(sib, BLACK);
                    setColor(parentOf(x), RED);
                    rotateLeft(parentOf(x));
                    sib = rightOf(parentOf(x));
                }

                if (colorOf(leftOf(sib)) == BLACK &&
                        colorOf(rightOf(sib)) == BLACK) {
                    setColor(sib, RED);
                    x = parentOf(x);
                } else {
                    if (colorOf(rightOf(sib)) == BLACK) {
                        setColor(leftOf(sib), BLACK);
                        setColor(sib, RED);
                        rotateRight(sib);
                        sib = rightOf(parentOf(x));
                    }
                    setColor(sib, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(rightOf(sib), BLACK);
                    rotateLeft(parentOf(x));
                    x = root;
                }
            } else { // symmetric
                RedBlackNode sib = leftOf(parentOf(x));

                if (colorOf(sib) == RED) {
                    setColor(sib, BLACK);
                    setColor(parentOf(x), RED);
                    rotateRight(parentOf(x));
                    sib = leftOf(parentOf(x));
                }

                if (colorOf(rightOf(sib)) == BLACK &&
                        colorOf(leftOf(sib)) == BLACK) {
                    setColor(sib, RED);
                    x = parentOf(x);
                } else {
                    if (colorOf(leftOf(sib)) == BLACK) {
                        setColor(rightOf(sib), BLACK);
                        setColor(sib, RED);
                        rotateLeft(sib);
                        sib = leftOf(parentOf(x));
                    }
                    setColor(sib, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(leftOf(sib), BLACK);
                    rotateRight(parentOf(x));
                    x = root;
                }
            }
        }

        setColor(x, BLACK);
    }

    final RedBlackNode search(int value) {
        RedBlackNode p = root;
        while (p != null) {
            int cmp = value - p.value;
            if (cmp < 0)
                p = p.left;
            else if (cmp > 0)
                p = p.right;
            else
                return p;
        }
        return null;
    }

    static class RedBlackNode {
        int value;
        int count;
        RedBlackNode left;
        RedBlackNode right;
        RedBlackNode parent;
        boolean color = BLACK;

        RedBlackNode(int value, RedBlackNode parent) {
            this.value = value;
            this.parent = parent;
            this.count = 1;
        }

        @Override
        public String toString() {
            return "RedBlackNode{" +
                    "value=" + value +
                    ", color=" + (color ? "black" : "red") +
                    '}';
        }
    }
}




