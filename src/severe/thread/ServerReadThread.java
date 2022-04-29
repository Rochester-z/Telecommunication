package severe.thread;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ServerReadThread implements Runnable{
    InputStream inputStream;
    DataInputStream dataInputStream;
    Graphics g;

    public void setGraphic(Graphics g){
        this.g = g;
    }
    public ServerReadThread(InputStream inputStream){
        this.inputStream = inputStream;
    }
    @Override
    public void run() {
        while (true){
            //接收一张客户端的图片图片
            receiveImage();

        }
    }
    int count = 0;
    public void receiveImage() {
        count++;
        //先接收图片的宽高

        dataInputStream = new DataInputStream(inputStream);
        int w = 0;
        int h = 0;
        try {
            w = dataInputStream.readInt();
            h = dataInputStream.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //读取字节数组一次最多2^16次方=65536个字节，160000个字节分3次read再拼接，160000和16000，0的个数别看错了
        byte[] byteInt = new byte[w*h*4];
        try {
            dataInputStream.readFully(byteInt);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        for(int i=byteInt.length-1; i>30000; i--){
//            System.out.println("byteInt"+byteInt[i]);
//
//        }



        int[] array = bytearrToIntarr(byteInt);


        //再接收图片的像素值
        int[][] imagePixel = new int[w][h];


        int k=0;
        //二维数组第一个数0位一维0 1为1 2为2 3为3 100为100 101为101 102为102 w*h为39999
        //一维整数数组转二维整数数组
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
//                System.out.println("字节数组的值"+byteInt[k++]);
                    //获取像素的一维数组
//                System.out.println("array[k]"+array[k]);
                    int rgb = array[k++];

                    imagePixel[i][j] = rgb;
                }
            }

        //先保存客户端传过来图片的所有像素值再画提高速度
        BufferedImage bufferedImage = new BufferedImage(400,400,BufferedImage.TYPE_INT_ARGB);

        Graphics bg = bufferedImage.getGraphics();
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                //先保存服务端传过来的所有RGB值，再直接对缓冲区图片的每个像素点直接设置像素值效率高
//                System.out.println("imagePixel"+imagePixel[i][j]);
                bufferedImage.setRGB(i,j,imagePixel[i][j]);
//                System.out.println(imagePixel[i][j]);

//                Color color = new Color(imagePixel[i][j]);
//                bg.setColor(color);
//                //画一个像素点
//                bg.fillOval(i, j, 1, 1);
            }
        }

        //客户端摄像头传过来的图片默认大小320*180，缓冲区图片至少这么大，画出来的随便多大
        g.drawImage(bufferedImage,400,400,300,300,null);
        System.out.println("户撒静安寺登记卡上");
    }



    public int readInt() {
        //从高位开始读
        try {
//            System.out.println("进入read");
            int x = ((inputStream.read() & 0xFF) << 24) |
                    ((inputStream.read() & 0xFF) << 16) |
                    ((inputStream.read() & 0xFF) << 8) |
                    ((inputStream.read() & 0xFF) << 0);
//            System.out.println("阻塞了");
            return x;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }

    }



    //图片的一维字节数组转二维整数数组，先将一维字节数组转一维整数数组
    public int[] bytearrToIntarr(byte[] bytes) {

//        for(int i=15930; i<16000;i++){
//            System.out.println(bytes[i]);
//        }
        int length = bytes.length;
        System.out.println("length"+length);
        int arraylength = length / 4;
        int[] arr = new int[arraylength];



        int k = 0;
        for (int i = 0; i < bytes.length-3; i=i+4) {
            //将一维字节数组分割，每份字节数组为一个整数
//            System.out.println("bytes[i]"+bytes[i]);
            byte[] curbyte = new byte[4];
            curbyte[0] = bytes[i];
            curbyte[1] = bytes[i+1];
            curbyte[2] = bytes[i+2];
            curbyte[3] = bytes[i+3];
            arr[k++] = ByteToInt(curbyte);
        }

        return arr;

    }










    //二维整数数组转字节数组，先将二维数组转一维
    public byte[] intarrToBytearr(int[][] intarr){
        int w = intarr.length;
        int h = intarr[0].length;
        int k=0;
        //将二维数组转一维
        int[] array = new int[w*h];
        for(int i=0; i<w; i++){
            for(int j=0; j<h; j++){
                array[k++] = intarr[i][j];
            }
        }


        int length = array.length;
        byte[] byteInt = new byte[length*4];
        int t=0;
        //将一维整数数组转为一维字节数组
        for(int i=0; i<length; i++){
            byte[] curbyte = IntToByte(array[i]);
            for(int j=0; j<4; j++) {
                byteInt[t++] = curbyte[j];
            }
        }
        return byteInt;
    }


    //字节数组转整数
    public int ByteToInt(byte[] byteInt){
        //从高位开始读
            int x = ((byteInt[0] & 0xFF) << 24) |
                    ((byteInt[1] & 0xFF) << 16) |
                    ((byteInt[2] & 0xFF) << 8) |
                    ((byteInt[3] & 0xFF) << 0);
            return x;
    }


    //整数转字节数组
    public byte[] IntToByte(int x){
        //整数用一个长度为4的字节数组表示
        byte[] byteInt = new byte[4];
        //int10进制可以直接进行&运算转成32位2进制，结果依旧为10进制，计算机只是存储用2进制
        //0x表示16进制，0xff二进制为11111111,表示无符号位255，有符号位-1。
        byteInt[0] =(byte)((x >> 24) & 0xFF);   //x1的32位2进制右移动24位，取最高的八位，高位自动补0，存储用二进制，结果依旧为10进制
        byteInt[1] =(byte)((x >> 16) & 0xFF);   //x1的32位2进制右移16位，取最高的16位，和0xFF与运算，截取第二组二进制
        byteInt[2] =(byte)((x >> 8) & 0xFF);    //x1的32位2进制右移8位，取最高的24位，与0xFF与运算，截取第三组二进制
        byteInt[3] =(byte)((x >> 0) & 0xFF);    //x1的32位2进制右移0位，取全部32位，与0xFF与运算，截取最低的八位
        return byteInt;
    }







}
