package Compressor.BiSortTree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BiSortTree {
    /**
     *当两个引用地址相等时一变皆变，当其中一个引用又被赋值时，就没关系了
     */
    Node root;     //定义二叉排序树的根节点

    //二叉排序树插入节点
    public void insertNode(Node node) {
        //传入一个节点，从二叉排序树根节点开始判断，如果大于节点值，访问其右孩子，否则访问其左孩子
        //直到访问到最底层的节点，插入节点

        //如果根节点为空，设根节点为当前插入节点，并跳出循环
        if(root == null){
            root = node;
            return;
        }

        if(node == null){
            return;
        }

        //遍历指针初始为根节点
        Node curNode = root;     //引用赋值curNode和root同时变化了
        while (true) {
            //当要插入节点大于遍历节点时，判断遍历节点是否有右孩子，无直接插入，有右孩子继续遍历
            if (node.val > curNode.val) {
                if(curNode.right == null){
                    curNode.right = node;
                    break;   //插入节点后跳出循环，跳出循环无返回值
                }
                else {
                curNode = curNode.right;

                }
            }
            //当要插入的节点小于遍历节点时，判断遍历节点是否有左孩子，无直接插入，有左孩子继续遍历
            else {
                if(curNode.left == null){
                    curNode.left = node;
                    break;   //插入节点后跳出循环
                }
                else {
                    curNode = curNode.left;
                }
            }
        }
    }



    //先序递归遍历，根左右
    public void PreOrder(Node root){
        if(root == null){
            return;
        }

        System.out.println(root.val);
        PreOrder(root.left);
        PreOrder(root.right);
    }



    //中序遍历，左根右
    public void InOrder(Node root){
        if(root == null){
            return;
        }

        InOrder(root.left);
        System.out.println(root.val);
        InOrder(root.right);
    }

    //后序遍历，左右根
    public void PostOrder(Node root){
        if(root == null){
            return;
        }

        PostOrder(root.left);
        PostOrder(root.right);
        System.out.println(root.val);

    }



    //非递归先序遍历，根左右
    public void PreOrder2(Node root){
        if(root == null){
            return;
        }

        Stack<Node> stack = new Stack<>();
        //访问节点（出栈）的顺序根左右，入栈的顺序右左
        Node curNode = root;      //初始化遍历节点为根节点
        stack.push(curNode);      //根节点先入栈
        while (!stack.isEmpty()) {  //当栈不空时循环
            curNode = stack.pop(); //出栈的节点设为遍历节点
            System.out.println("节点值"+curNode.val);
            //当遍历节点右孩子不为空时，右孩子入栈
            if(curNode.right != null) {
                stack.push(curNode.right);
            }
            //当遍历节点左孩子不为空时，左孩子入栈
            if (curNode.left != null){
                stack.push(curNode.left);
            }
        }
    }



    //非递归中序遍历，左根右，利用栈，找到最左节点
    public void InOrder2(Node root){
        if(root == null){
            return;
        }

        Stack<Node> stack = new Stack<>();
        Node curNode = root;        //初始化遍历指针为根节点
        while(curNode != null || !stack.isEmpty()){ //当遍历指针不为空或栈不空时循环
            //当遍历指针不为空时节点继续入栈，一直访问其左孩子，直到最底层
            if(curNode != null){
                stack.push(curNode);
                curNode = curNode.left;
            }else{      //当遍历指针空时，出栈，访问，并判断其是否有右孩子
                curNode = stack.pop();
                System.out.println(curNode.val);
                curNode = curNode.right;
            }
        }
    }



    //非递归后序遍历，左右根
    public void PostOrder2(Node root){
        if(root == null){
            return;
        }

        Node curNode = root;
        Node newVisited = null;   //后序遍历需设最近访问过的节点
        Stack<Node> stack = new Stack<>();
        while(curNode != null || !stack.isEmpty()){
            //先走到最左边
            if(curNode != null){
                stack.push(curNode);
                curNode = curNode.left;
            }else{
                //当走到最走，判断其栈顶节点
                curNode = stack.peek();
                //判断栈顶节点是否有右孩子，无直接访问，有，且右孩子未被访问，右孩子入栈，再访问右孩子的左孩子
                if(curNode.right != null && curNode.right != newVisited){
                    curNode = curNode.right;
                    stack.push(curNode);
                    curNode = curNode.left;
                }else {//出栈节点无右孩子，直接访问
                    curNode = stack.pop();
                    newVisited = curNode;
                    curNode = null;         //出站后当前节点设为空
                }
            }
        }
    }

    //层次遍历，借助队列
    public void levelOrder(Node root) {
        if (root == null) {
            return;
        }

        Node curNode = root;
        //Stack为类，Queue为接口
        Queue<Node> queue = new LinkedList<>();
        queue.add(curNode);
        while (!queue.isEmpty()) {   //当队列不为空时，出队列
            curNode = queue.poll();
            System.out.println(curNode.val);
            //出队列后，其左右孩子如队列
            if (curNode.left != null) {
                queue.add(curNode.left);
            }
            if (curNode.right != null) {
                queue.add(curNode.right);
            }
        }


    }


    //查找某一节点

    public static void main(String[] args) {
        int[] arr = {2,5,7,3,8,4,1};
        BiSortTree biSortTree = new BiSortTree();
        for(int i : arr){
            biSortTree.insertNode(new Node(i));
        }
//        biSortTree.PreOrder(biSortTree.root);
//        biSortTree.PreOrder2(biSortTree.root);
//        biSortTree.InOrder(biSortTree.root);
//        biSortTree.InOrder2(biSortTree.root);
//        biSortTree.PostOrder(biSortTree.root);
//        biSortTree.PostOrder2(biSortTree.root);
          biSortTree.levelOrder(biSortTree.root);
    }


}
