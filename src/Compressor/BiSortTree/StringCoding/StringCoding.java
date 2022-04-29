package Compressor.BiSortTree.StringCoding;

import java.util.HashMap;
import java.util.Map;

public class StringCoding {
    //求出字符串中各字符出现的次数
    public static HashMap chartime(String code) {
        HashMap<Character, Integer> hashmap = new HashMap<>();
        for (int i = 0; i < code.length(); i++) {
            //获取哈希表中键（字符）的值（个数）
            //获取code字符串中第i的字符
            char chararacter = code.charAt(i);

            //当哈希表中不包含当前字符时
            if (!hashmap.containsKey(chararacter)) {
                hashmap.put(chararacter, 1);
            } else {
                //获取哈希表中当前字符的值
                int num = hashmap.get(chararacter);
                hashmap.put(chararacter, num + 1);
            }
        }


        //字符串用""括起来，字符用''括起来
        System.out.println("输出" + hashmap.get('d'));
        System.out.println(hashmap.get('a'));
        System.out.println(hashmap.get('b'));

        //传入字符串，返回哈希表
        return hashmap;

    }
}
