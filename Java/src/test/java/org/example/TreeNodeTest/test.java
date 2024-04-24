package org.example.TreeNodeTest;

import org.example.test.TreeNode;
import java.util.*;

public class test {
    public static void main(String[] args) {
        TreeNode node = new TreeNode(1);
        node.left = new TreeNode(2);
        node.left.left = new TreeNode(3);
        node.left.right = new TreeNode(4);
        node.right = new TreeNode(2);
        node.right.right = new TreeNode(3);
        node.right.left = new TreeNode(4);
        levelOrderTraversal(node).forEach(System.out::print);
        System.out.println();
        System.out.println(maxDeep(node));
        System.out.println(maxDeepByLevel(node));


        //判断一颗二叉树是否是平衡二叉树
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.left.left.left = new TreeNode(8);
        root.right = new TreeNode(3);
        root.right.left = new TreeNode(6);
        boolean balanced = isBalanced(root);
        System.out.println(balanced);

    }
    /**
     * 什么是DP Dynamic Programming
     * 一种优化问题求解方法，通常用于解决具有 重叠子问题 和 最优子结构 性质的问题
     * 基本思想是将原问题分解成更小的子问题，通过求解和保存这些子问题的解，避免重复计算，从而提高算法的效率
     */

    /**
     * 实现二叉树的前序遍历。
     *
     * @param root 二叉树的根节点
     * @return
     */
    public static List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        //递归
        preorderDP(root, res);
        //迭代
        preorder(root);
        return res;
    }

    public static void preorderDP(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }
        res.add(root.val);
        preorderDP(root.left, res);
        preorderDP(root.right, res);
    }

    /**
     * 前序遍历 中 左 右
     * 入栈顺序 中 右 左
     * 出栈顺序 中 左 右
     *
     * @param root
     * @return
     */
    public static List<Integer> preorder(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            res.add(node.val);
            //根据栈的特性，先进后出；所以先将右节点压栈，再将左节点压栈
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        return res;
    }


    /**
     * 实现二叉树的中序遍历。
     *
     * @param root 二叉树的根节点
     * @return
     */
    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        inorderDP(root, res);
        return res;
    }

    public static void inorderDP(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }
        inorderDP(root.left, res);
        res.add(root.val);
        inorderDP(root.right, res);
    }

    /**
     * 中序遍历 左 中 右
     * 入栈顺序 左 右
     *
     * @param root
     * @return
     */
    public static List<Integer> inorder(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                cur = stack.pop();
                res.add(cur.val);
                cur = cur.right;
            }
        }

        return res;
    }

    /**
     * 实现二叉树的后序遍历。
     *
     * @param root 二叉树的根节点
     * @return
     */
    public static List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        postorderDP(root, res);
        return res;
    }

    public static void postorderDP(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }
        postorderDP(root.left, res);
        postorderDP(root.right, res);
        res.add(root.val);
    }


    /**
     * 后序遍历 左 右 中
     * 入栈顺序 中 左 右
     * 出栈顺序 中 右 左
     * 集合反转 左 右 中
     *
     * @param root
     * @return
     */
    public static List<Integer> postorder(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            res.add(node.val);
            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }
        Collections.reverse(res);
        return res;
    }


    /**
     * 实现二叉树的层序遍历。
     *
     * @param root 二叉树的根节点
     * @return
     */

    public static List<Integer> levelOrderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                TreeNode node = queue.poll();
                res.add(node.val);
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }
        return res;
    }


    /**
     * @param root 二叉树的根节点
     * @return 计算二叉树的最大深度
     */
    public static int maxDeep(TreeNode root) {
        //后序遍历
        if (root == null) {
            return 0;
        }
        int leftDeep = maxDeep(root.left);//左
        int rightDeep = maxDeep(root.right);//右
        return Math.max(leftDeep, rightDeep) + 1;//中
    }

    public static int deep = 0;

    public static int maxDeepByLevel(TreeNode root) {
        if (root == null) {
            return 0;
        }
        //层序遍历
        level(root, 0);
        return deep;
    }

    public static void level(TreeNode root, int index) {
        //递归的遍历左右子树的最大深度
        //到达叶子节点后，更新深度deep
        if (root == null) {
            deep = Math.max(deep, index);
            return;
        }
        index++;
        level(root.left, index);
        level(root.right, index);
    }

    /**
     * @param root 二叉树的根节点
     * @return 计算二叉树的最小深度，注意如果根节点的左右子树任意为null,则返回的是1
     */
    public static int minDeep(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftDeep = maxDeep(root.left);
        int rightDeep = maxDeep(root.right);
        return Math.min(leftDeep, rightDeep) + 1;
    }


    /**
     * @param root 二叉树的根节点
     * @return 计算二叉树的最小深度，排除如果根节点的左右子树任意为null,则返回的是1的情况
     * 即从根节点到最浅叶子节点的最小深度
     */
    public static int minDeepLogin(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftDeep = maxDeep(root.left);
        int rightDeep = maxDeep(root.right);
        if (root.left == null && root.right != null) {
            return rightDeep + 1;
        }
        if (root.right == null && root.left != null) {
            return leftDeep + 1;
        }
        return 1 + Math.min(leftDeep, rightDeep);
    }




    public static boolean isBalanced(TreeNode root) {
        if(root == null){
            return true;
        }
        return Math.abs(height(root.left) - height(root.right)) <= 1 && isBalanced(root.left) && isBalanced(root.right);
    }
    public static int height(TreeNode root){
        if(root == null){
            return 0;
        }
        return Math.max(height(root.left) ,height(root.right)) + 1;

    }


}
