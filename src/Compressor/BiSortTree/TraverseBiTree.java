package Compressor.BiSortTree;

import java.util.*;

public class TraverseBiTree {
    /*二叉树的遍历方法有前序，中序，后序的递归遍历
    非递归遍历
    层次遍历
     */


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
                //判断栈顶节点是否有右孩子，无直接访问，有，且最近右孩子未被访问，右孩子入栈，再访问右孩子的左孩子
                if(curNode.right != null && curNode.right != newVisited){
                    curNode = curNode.right;
                    stack.push(curNode);
                    //右孩子如战后再访问其左孩子
                    curNode = curNode.left;
                }else {//出栈节点无右孩子，直接访问
                    curNode = stack.pop();
                    System.out.println(curNode.val);
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


}
