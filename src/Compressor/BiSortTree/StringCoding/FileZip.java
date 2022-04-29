package Compressor.BiSortTree.StringCoding;

import java.io.*;
import java.util.HashMap;

public class FileZip{
    /*文件压缩
      1.先读文件
      2.统计各字符出现的次数，找出个字符的权值，根据权值建立哈夫曼树，进行编码
      3.哈夫曼树编码为字符，将字符改为整数，化为2进制的01，补齐8位01，化为1个字节写出去
     */

    String fileaddress;  //文件的绝对地址
    String filezipaddress;  //文件的压缩地址
    String string;       //文件的字符串形式
    StringBuilder fileCoding = new StringBuilder();   //文件的总编码
    //用HashMap保存字符的编码和字符
    HashMap<Character, String> codingHashmap = new HashMap<>();
    int lessnum;        //文件的二进制编码补齐的长度

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        FileZip fileZip = new FileZip();
        fileZip.compression();
        long time2 = System.currentTimeMillis();
        long time = time2 - time1;
        System.out.println("压缩时间"+time+"ms");
    }



    public void setFileAddress(String fileaddress){
        this.fileaddress = fileaddress;
    }
    public void setFileZipAddress(String filezipaddress){
        this.filezipaddress = filezipaddress;
    }


    public void compression() {
        long time3 = System.currentTimeMillis();
        try {
            long time1 = System.currentTimeMillis();

            //1.读文件
            File file = new File(fileaddress);
            FileInputStream fileInputStream = null;
            fileInputStream = new FileInputStream(file);

            //统计文件的字节长度，当文件不可读时，停止计数
            int filelength = fileInputStream.available();
            byte[] fileBytes = new byte[filelength];
            //将文件读入字节数组
            fileInputStream.read(fileBytes);
            string = new String(fileBytes);
//            System.out.println("string值" + string);
            long time2 = System.currentTimeMillis();
            long time = time2 - time1;
            System.out.println("读文件时间"+time/1000+"s");

        } catch (IOException e) {
            e.printStackTrace();
        }


        //2.统计各字符出现的次数，找出个字符的权值，根据权值建立哈夫曼树，进行编码
        HashMap hashmap = StringCoding.chartime(string);
        long time1 = System.currentTimeMillis();
        HuffmanTree huffmanTree = new HuffmanTree(hashmap);
        long time2 = System.currentTimeMillis();
        long time = time2 - time1;
        System.out.println("建树时间为time"+time/1000+"s");



        //根据哈夫曼对字符的编码求出文件字符串的总编码，将总编码转化10进制01化为2进制的01，补齐8位01，化为1个字节写出去
        PreOrder2(huffmanTree.root);


        //将码表保存到和文件的二进制编码同一个文件中
        File file = new File(filezipaddress);
        FileOutputStream fileOutputStream = null;
        try {
            //将序列化的对象写入文件
            fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(codingHashmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < string.length(); i++) {
            fileCoding = fileCoding.append(codingHashmap.get(string.charAt(i)));
        }

//        System.out.println("文件的总编码的二进制");

        int[] codingIntArr = stringToInt(fileCoding.toString());

        //文件的字节表示
        byte[] byteArr = intArrToByte(codingIntArr);


        //将文件的二进制编码写到压缩文件中
        try {
            fileOutputStream.write(byteArr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long time4 = System.currentTimeMillis();
        long timee = time4 - time3;
        System.out.println("压缩时间"+timee/1000+"s");
    }

    //将编码01字符串转为十进制01
    //将字符1-9转为十进制1-9，
    public int[] stringToInt(String string) {
        int length = string.length();
        int[] codingIntArr = new int[length];
        for (int i = 0; i < length; i++) {
            //字符输出的是ASCII码，字符0-9输出的ASCII码是48-57（十进制），转十进制0-9要-48
            int num = string.charAt(i) - 48;
            codingIntArr[i] = num;
        }
        return codingIntArr;
    }

    //将01整数数组转为bit，8个bit合并为一个字节，用字节（0-255的整数）保存，字节数组的首字节保存文件的01二进制编码长度补齐的长度
    public byte[] intArrToByte(int[] bitArr) {
        int length = bitArr.length;
        //统计bit数组的长度，如果对8取余有余数，将余数补齐为8位
        int less = length % 8;
        //当余数为0时，不用补齐
        if (less != 0) {
            lessnum = 8 - less;
        }
        int[] bitArr_ = new int[length + lessnum];
        for (int i = 0; i < length; i++) {
            bitArr_[i] = bitArr[i];
        }

        //保存的整数数组的长度
        int intlength = bitArr_.length / 8;
        byte[] byteArr = new byte[intlength+1];
        byteArr[0] = (byte) lessnum;
        int k = 1;
        for (int i = 0; i < bitArr_.length; i = i + 8) {
            //先存高位
            int x = ((bitArr_[i] & 0xFF) << 7) |
                    ((bitArr_[i + 1] & 0xFF) << 6) |
                    ((bitArr_[i + 2] & 0xFF) << 5) |
                    ((bitArr_[i + 3] & 0xFF) << 4) |
                    ((bitArr_[i + 4] & 0xFF) << 3) |
                    ((bitArr_[i + 5] & 0xFF) << 2) |
                    ((bitArr_[i + 6] & 0xFF) << 1) |
                    ((bitArr_[i + 7] & 0xFF) << 0);
//            System.out.println("x的值" + x);
            //整数存的是低八位，记事本读的是字符，1个字符2个字节。
            //将8个bit合并为一个字节，用字节数组保存，输入输出流读取原码，sout输出补码
            byteArr[k] = (byte) (x & 0xFF);
//          System.out.println("byteArr的值" + byteArr[k]);
            k++;
        }
        return byteArr;
    }
    //整数转字节数组
    public byte[] IntToByte(int x) {
        //整数用一个长度为4的字节数组表示
        byte[] byteInt = new byte[4];
        //int10进制可以直接进行&运算转成32位2进制，结果依旧为10进制，计算机只是存储用2进制
        //0x表示16进制，0xff二进制为11111111,表示无符号位255，有符号位-1。
        byteInt[0] = (byte) ((x >> 24) & 0xFF);   //x1的32位2进制右移动24位，取最高的八位，高位自动补0，存储用二进制，结果依旧为10进制
        byteInt[1] = (byte) ((x >> 16) & 0xFF);   //x1的32位2进制右移16位，取最高的16位，和0xFF与运算，截取第二组二进制
        byteInt[2] = (byte) ((x >> 8) & 0xFF);    //x1的32位2进制右移8位，取最高的24位，与0xFF与运算，截取第三组二进制
        byteInt[3] = (byte) ((x >> 0) & 0xFF);    //x1的32位2进制右移0位，取全部32位，与0xFF与运算，截取最低的八位
        return byteInt;
    }

    //字节数组转整数
    public int ByteToInt(byte[] byteInt) {
        //从高位开始读
        int x = ((byteInt[0] & 0xFF) << 24) |
                ((byteInt[1] & 0xFF) << 16) |
                ((byteInt[2] & 0xFF) << 8) |
                ((byteInt[3] & 0xFF) << 0);
        return x;
    }


    //先序递归遍历所有的叶子结点
    public void PreOrder2(Node root) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            codingHashmap.put(root.character, root.coding);
//            System.out.println("节点值：" + root.val + "字符" + root.character + "编码：" + root.coding);
        }
        PreOrder2(root.left);
        PreOrder2(root.right);
    }
}
