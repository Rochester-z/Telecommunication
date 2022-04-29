package Compressor.BiSortTree;

public class Test {
    public static void main(String[] args) {
        Node root = new Node(1);

        Node node1 = root;
        if(node1 == root){
            System.out.println("true");
        }
        else{
            System.out.println("false");
        }

        Node node2 = new Node(3);

        node1.val = 2;
        node1.left = node2;

        node1 = node1.left;   //当两个引用地址相等时一变皆变，当其中一个引用又被赋值时，就没关系了
        System.out.println(root.val);
        System.out.println(root.left.val);
        System.out.println(node1.val);

        if(node1 == root){
            System.out.println("true");
        }
        else{
            System.out.println("false");
        }
    }
}
