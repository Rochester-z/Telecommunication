package client.thread;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class VideoReadThread implements Runnable{
    InputStream inputStream;
    Graphics g;
    DataInputStream dataInputStream;

    public void setGraphic(Graphics g){
        this.g = g;
    }
    public VideoReadThread(InputStream inputStream){
        this.inputStream = inputStream;
    }
    @Override
    public void run() {
        while (true){
            //接收一张服务器的图片图片
            receiveImage();

        }
    }
    int count = 0;
    public void receiveImage() {
        count++;
        System.out.println("收到图片数"+count);
        //先接收图片的宽高
        int w = readInt();
        int h = readInt();
        System.out.println("w的值"+w+"h的值"+h);

        //读取字节数组一次最多2^16次方=65536个字节，160000个字节分3次read再拼接，160000和16000，0的个数别看错了
        byte[] byteInt = new byte[w*h*4];
        try {
            dataInputStream = new DataInputStream(inputStream);
            //数据输入流读全字节数（超过65536）
            dataInputStream.readFully(byteInt);
        } catch (IOException e) {
            e.printStackTrace();
        }




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
        System.out.println("k值"+k);

        //先保存服务端传过来图片的所有像素值再画提高速度
        BufferedImage bufferedImage = new BufferedImage(320,180,BufferedImage.TYPE_INT_ARGB);

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

        //服务端摄像头传过来的图片默认大小320*180，缓冲区图片至少这么大，画出来的随便多大
        g.drawImage(bufferedImage,100,100,300,300,null);
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
        System.out.println("arraylength"+arraylength);
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












    //字节数组转整数
    public int ByteToInt(byte[] byteInt){
        //从高位开始读
            int x = ((byteInt[0] & 0xFF) << 24) |
                    ((byteInt[1] & 0xFF) << 16) |
                    ((byteInt[2] & 0xFF) << 8) |
                    ((byteInt[3] & 0xFF) << 0);
            return x;
    }


}
