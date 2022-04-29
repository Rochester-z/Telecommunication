package Compressor.BiSortTree.StringCoding;



import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class HuffmanTree {
    /*创建哈夫曼树
    1.根据权值初始化节点，保存到列表中
    2.取出一组节点中值最小的两个节点，合并加入节点组
     */
    List<Node> list = new LinkedList<>();    //保存哈夫曼节点的列表
    Node root;  //根节点

    public HuffmanTree(HashMap hashmap) {
        //初始化哈夫曼树，底层数据结构为列表
        //foreach遍历哈希表
        for (Object key : hashmap.keySet()) {
            Object value = hashmap.get(key);
            Node node = new Node((char) key, (int) value);
            list.add(node);
        }
        createHuffman();
        //创建好哈夫曼树后再进行编码
        this.coding(this.root);
//        this.PreOrder2(this.root);


//        for (Object key : hashmap.keySet()) {
//            System.out.println("key值" + key);
//            System.out.println("value值" + hashmap.get(key));
//        }
    }


    //对列表中节点进行排序，冒泡排序时间复杂度O(n^2)，快速排序事件复杂度O(nlog2n)，比较快
    public void listsort() {
        for (int i = 0; i < list.size(); i++) {
            boolean flag = false;
            for (int j = list.size()-1; j > i; j--) {
                if (list.get(j).val <= list.get(j - 1).val) {  //如果列表中前一节点的值大于后一节点的值，则交换两节点的位置，不可交换值
                    Node node = list.get(j);
                    list.set(j, list.get(j - 1));
                    list.set(j - 1, node);
                    flag = true;
                }
            }
            //一次冒泡后如果没有交换元素，表示序列已经有序
                if(flag == false){
                    return;
                }
        }
    }



    public void listsort2(){

    }


    //快速排序
    public void quickSort(List<Node> list, int low, int high) {
        if(low < high) {     //递归跳出条件
            int pivot = partition(list, low, high);  //对array进行划分，返回枢纽下标，进行下次划分
            quickSort(list, low, pivot-1);
            quickSort(list,pivot+1,high);

        }
    }

    public int partition(List<Node> list, int low, int high){
        //一趟划分过程
        Node pivot = list.get(low);      //将当前表中的第一个节点元素设为枢纽值，对表进行划分
        while(low < high){   //循环跳出条件
            while(low < high && list.get(high).val >= pivot.val){
                high--;
            }
            Node node1 = list.get(low);  //将比枢纽值小的元素移动到左端
            list.set(low, list.get(high));
            list.set(high, node1);

            while(low < high && list.get(low).val <= pivot.val) {
                low++;
            }
            Node node2 = list.get(high);  //将比枢纽值小的元素移动到右端
            list.set(high, list.get(low));
            list.set(low, node2);
        }
        list.set(low,pivot);     //枢纽元素存放到最终位置
        return low;     //返回存放枢纽的最终位置
    }





    //取出列表中值最小的两个节点合并加入列表
    public void createHuffman() {
        quickSort(list, 0, list.size()-1);
        //当哈夫曼树只有一个根节点时结束循环
        while (list.size() > 1) {
            Node node1 = list.remove(0);
            Node node2 = list.remove(0);
            int val = node1.val + node2.val;
            Node node = new Node(val);
            list.add(node);
            //合并的新节点左右节点连接起来
            node.left = node1;
            node.right = node2;
            quickSort(list,0,list.size()-1);
        }
        root = list.get(0);
    }


    //先序遍历，根左右
    public void PreOrder(Node root) {
        if (root == null) {
            return;
        }

        System.out.println("节点值：" + root.val);

        PreOrder(root.left);
        PreOrder(root.right);
    }


    //先序递归遍历所有的叶子结点
    public void PreOrder2(Node root) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            System.out.println("节点值：" + root.val + "字符" + root.character + "编码：" + root.coding);
        }
        PreOrder2(root.left);
        PreOrder2(root.right);
    }


    //给哈夫曼树编码，遍历左孩子的时候，左孩子编码+0，遍历右孩子的时候，右孩子编码+1
    public void coding(Node root) {
        if (root == null) {
            return;
        }
        if (root.left != null) {
            root.left.coding = root.coding + "0";
            coding(root.left);
        }
        if (root.right != null) {
            root.right.coding = root.coding + "1";
            coding(root.right);
        }
    }

//
//    public static void main(String[] args) {
//        //压缩字符串
//        String str = "adjsjasss";
//        HashMap hashmap = StringCoding.chartime(str);
//        int length = hashmap.size();
//        //创建数组保存所有字符的个数
//
//        //构造哈夫曼树
//        HuffmanTree huffmanTree = new HuffmanTree(hashmap);
//
//
//        for (Object key : hashmap.keySet()) {
//            System.out.println("key值" + key);
//            System.out.println("value值" + hashmap.get(key));
//        }
//    }
}

