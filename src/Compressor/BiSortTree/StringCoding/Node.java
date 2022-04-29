package Compressor.BiSortTree.StringCoding;

import java.io.Serializable;


public class Node implements Serializable {
    char character;   //节点包含的字符
    int val;
    Node left;
    Node right;
    String coding = "";  //节点的编码初始值为空


    //只有值得节点非叶子节点
    public Node(int val){
        this.val = val;
    }

    //既有值又有字符的节点为叶子节点
    public Node(char character, int val){
        this.character = character;
        this.val = val;
    }
}
