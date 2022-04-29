package Compressor.BiSortTree.HuffmanTree2;

import java.util.LinkedList;
import java.util.List;

public class HuffmanTree {
    //现场编码能力还要提高啊
    /*
    1.对哈夫曼树的节点进行初始化
    2.取出哈夫曼树中最小的两个节点合并值并新建一个节点加入节点列表

     */
    List<Node> list = new LinkedList<>();  //哈夫曼树底层数据结构
    Node root;


    public HuffmanTree(){
        int[] array = {1,2,3,4};
        for(int i : array){
            Node node = new Node(i);
        }

    }


    public void bubbleSort(){

    }


    public void createHuffman(){
        //取出节点列表中值最小的两个节点
//        list.sort();
        while (list.size()>1) {
            Node node1 = list.remove(0);
            Node node2 = list.remove(0);
            int val = node1.val + node2.val;
            Node node = new Node(val);
            node.left = node1;
            node.right = node2;
            list.add(node);
//            list.sort();
        }
        root = list.get(0);  //根节点

    }


    public void InOrder(Node root){
        if(root == null){
            return;
        }

        root.left.coding = root.coding + "0";
        InOrder(root.left);
        root.right.coding = root.coding + "1";
        System.out.println(root.val);
        InOrder(root.right);
    }


    //非递归打印叶子节点

    public static void main(String[] args) {

    }

}
