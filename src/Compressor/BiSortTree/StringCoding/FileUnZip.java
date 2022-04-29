package Compressor.BiSortTree.StringCoding;


import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FileUnZip {
    /*解压
    1.首先读取压缩的文件（字节表示）
    2.将字节数组在字符和字符编码哈希表中查找
     */
    String fileaddress;  //文件的绝对地址
    String fileunzipaddress;  //文件解压的地址
    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        FileUnZip fileUnZip = new FileUnZip();
        fileUnZip.unZip();
        long time2 = System.currentTimeMillis();
        long time = time2 - time1;
        System.out.println("解压时间"+time/1000+"s");
    }


    public void setFileaddress(String fileaddress){
        this.fileaddress = fileaddress;
    }
    public void setFileunzipaddress(String fileunzipaddress){
        this.fileunzipaddress = fileunzipaddress;
    }


    public void unZip(){
        long time1 = System.currentTimeMillis();
        //获取构建的huffman树的根节点
        HashMap<Character, String> codingHashMap = null;  //码表
        //1.读取压缩的文件（字节表示），先读码表，再读原始文件
        File file = new File(fileaddress);
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            codingHashMap = (HashMap) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }



        try {
            int length = fileInputStream.available();
            byte[] bytes = new byte[length];
            fileInputStream.read(bytes);
            String string = new String(bytes);
//            System.out.println("string的值" + string);
            //将字节数组转化为01整数数组
            int[] intarr = byteToInt(bytes);
//            System.out.println("数组的值");
//            for(int i=0; i<intarr.length; i++){
//                System.out.print(intarr[i]);
//            }
//            System.out.println();


            //将01整数数组转化为字符串数组
            String charstring = intToString(intarr);
//            System.out.println("charstring"+charstring);


         StringBuilder resultString = new StringBuilder();           //解压的结果String
         String curstring = "";

         HashMap<String, Character> codinfHashMap_ = reverseHashMap(codingHashMap);
         //2.将字节数组在字符和字符编码哈希表中查找
            for(int i=0; i<charstring.length(); i++){
                curstring = curstring + charstring.charAt(i);
                if(codinfHashMap_.containsKey(curstring)){
                    resultString.append(codinfHashMap_.get(curstring));
                    curstring = "";
                }

//                if(codingHashMap.containsValue(curstring)){
//                    System.out.println("有了111");
//                    for(Object character : codingHashMap.keySet()){
//                        if(codingHashMap.get(character).equals(curstring)){
//                            resultString.append(character);
//                            curstring = "";
//
//                        }
//                    }
//                    for(Map.Entry<Character, String> entry : codingHashMap.entrySet()){
//                        if(entry.getValue().equals(curstring)){
//                            resultString.append(entry.getKey());
//                            curstring = null;
//                            //找到了value值后，跳出循环
//                            break;
//                        }
//                    }
//                }
            }








            byte[] resultbyte = resultString.toString().getBytes();;
//            System.out.println("解压的结果"+resultString);
            File resultfile = new File(fileunzipaddress);
            FileOutputStream fileOutputStream = new FileOutputStream(resultfile);
            fileOutputStream.write(resultbyte);
        }catch (IOException e){
            e.printStackTrace();
        }


        long time2 = System.currentTimeMillis();
        long time = time2 - time1;
        System.out.println("解压时间"+time/1000+"s");
    }

    //将byte[]数组转化为01整数数组
    public int[] byteToInt(byte[] bytesarr){
        int lessnum = bytesarr[0];         //01二进制编码补齐的长度
        int length = bytesarr.length-1;
        int intarrlength = length*8;

        int[] intarr = new int[intarrlength];
        int k = 0;
        for(int i=1; i<length; i++) {
            //每个字节有八位，将字节变为8位01表示
            byte curbyte = bytesarr[i];
                intarr[k++] = (curbyte >> 7) & 1;
                intarr[k++] = (curbyte >> 6) & 1;
                intarr[k++] = (curbyte >> 5) & 1;
                intarr[k++] = (curbyte >> 4) & 1;
                intarr[k++] = (curbyte >> 3) & 1;
                intarr[k++] = (curbyte >> 2) & 1;
                intarr[k++] = (curbyte >> 1) & 1;
                intarr[k] = (curbyte >> 0) & 1;
                k++;
            }


        int[] intarr_ = new int[intarrlength-lessnum];
        for(int i=0; i<intarr_.length; i++){
            intarr_[i] = intarr[i];
        }
        return intarr_;
        }

    //将01整数数组转化为字符串数组
    public String intToString(int[] intarr){
        StringBuffer string = new StringBuffer();
        int length = intarr.length;
        for(int i=0; i<length; i++){
            string.append(intarr[i]);
        }
        return string.toString();

    }



    //将编码01字符串转为十进制01
    //将字符1-9转为十进制1-9，
    public int[] stringToInt(String string) {
        int length = string.length();
        int[] codingIntArr = new int[length];
        for (int i = 0; i < length; i++) {
            //字符输出的是ASCII码，字符1-9输出的ASCII码是48-57（十进制），转十进制1-9要-48
            int num = string.charAt(i) - 48;
            codingIntArr[i] = num;
        }
        return codingIntArr;
    }



    //先序递归遍历所有的叶子结点
//    public void PreOrder2(Node root) {
//        if (root == null) {
//            return;
//        }
//        if (root.left == null && root.right == null) {
//            codingHashmap.put(root.character, root.coding);
//            System.out.println("节点值：" + root.val + "字符" + root.character + "编码：" + root.coding);
//        }
//        PreOrder2(root.left);
//        PreOrder2(root.right);
//    }



    public HashMap<String, Character> reverseHashMap(HashMap<Character, String> codingHashmap){
        HashMap<String, Character> codingfHashMap_ = new HashMap<>();
        for(Map.Entry<Character, String> entry : codingHashmap.entrySet()){
                codingfHashMap_.put(entry.getValue(), entry.getKey());
        }
        return codingfHashMap_;
    }

}
