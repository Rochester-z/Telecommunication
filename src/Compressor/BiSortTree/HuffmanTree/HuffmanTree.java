package Compressor.BiSortTree.HuffmanTree;

import java.util.LinkedList;
import java.util.List;

public class HuffmanTree {
    /*创建哈夫曼树
    1.根据权值初始化节点，保存到列表中
    2.取出一组节点中值最小的两个节点，合并加入节点组
     */
    List<Node> list = new LinkedList<>();    //保存哈夫曼节点的列表
    Node root;  //根节点

    public HuffmanTree(){
        //初始化哈夫曼树，底层数据结构为列表
        int[] arr = {4,2,3,2};
        for(int i : arr){
            Node node = new Node(i);
            list.add(node);
        }
        list.get(0);

        createHuffman();

    }


    //对列表中节点进行排序
    public void listsort(){
        for(int i=0; i<list.size(); i++){
            for(int j=list.size()-1; j>i; j--){
                if(list.get(j).val > list.get(j-1).val){  // 如果列表中前一节点的值大于等于后一节点的值，则交换两节点的位置，不可交换值
                    Node node = list.get(j);
                    list.set(j,list.get(j-1));
                    list.set(j-1,node);
                }
            }
        }
    }

    //取出列表中值最小的两个节点合并加入列表
    public void createHuffman(){
        listsort();
        //当哈夫曼树只有一个根节点时结束循环
        while(list.size() > 1) {
            Node node1 = list.remove(0);
            Node node2 = list.remove(0);
            int val = node1.val + node2.val;
            Node node = new Node(val);
            list.add(node);
            //合并的新节点左右节点连接起来
            node.left = node1;
            node.right = node2;
            listsort();
            root = list.get(0);
        }
    }


    //先序遍历，根左右
    public void PreOrder(Node root){
        if(root == null){
            return;
        }

        System.out.println("节点值："+root.val);

        PreOrder(root.left);
        PreOrder(root.right);
    }


    //先序递归遍历所有的叶子结点
    public void PreOrder2(Node root){
        if(root == null){
            return;
        }
        if(root.left == null && root.right == null){
            System.out.println("节点值："+root.val+"编码："+root.coding);
        }
        PreOrder2(root.left);
        PreOrder2(root.right);
    }




    //给哈夫曼树编码，遍历左孩子的时候，左孩子编码+0，遍历右孩子的时候，右孩子编码+1
    public void coding(Node root){
        if(root == null){
            return;
        }
        if(root.left != null) {
            root.left.coding = root.coding + "0";
            coding(root.left);
        }
        if(root.right != null) {
            root.right.coding = root.coding + "1";
            coding(root.right);
        }
    }







    public static void main(String[] args) {

        //压缩字符串
        String str = "aadshdsahas";
        //构造哈夫曼树
        HuffmanTree huffmanTree = new HuffmanTree();
        //创建好哈夫曼树后再进行编码
        huffmanTree.coding(huffmanTree.root);
        huffmanTree.PreOrder(huffmanTree.root);
//        huffmanTree.PreOrder2(huffmanTree.root);
    }
}
