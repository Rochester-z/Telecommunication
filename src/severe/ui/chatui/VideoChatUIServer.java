package severe.ui.chatui;


import com.github.sarxos.webcam.Webcam;
import severe.thread.ServerReadThread;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class VideoChatUIServer extends JFrame implements Runnable{
    InputStream inputStream;
    DataInputStream dataInputStream;   //数据输入流接收图片视频
    OutputStream outputStream;
    DataOutputStream dataOutputStream;  //数据输出流发送图片视频
    Graphics g;


    @Override
    public void run() {
        //视频为一个任务，启动一个线程来执行任务
        //视频聊天
        this.InitUI();  //打开界面

    }




    public void InitUI(){
        this.setTitle("服务端视频聊天");
        this.setSize(800, 800);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo(null);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        g = this.getGraphics();
        startServer();

    }


    public void startServer() {
        try {
//          服务器默认ip：localhost，视频通信进程端口为53000
            ServerSocket serverSocket = new ServerSocket(53000);
            Socket client = serverSocket.accept();
            inputStream = client.getInputStream();
            outputStream = client.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }

        ServerReadThread serverReadThread = new ServerReadThread(inputStream);
        serverReadThread.setGraphic(g);
        new Thread(serverReadThread).start();

        showVideo();


    }


    public void showVideo() {
        //死循环可放在线程里
        new Thread() {
            public void run() {
                Webcam webcam = Webcam.getDefault();
                webcam.open();
                while (true) {
                    System.out.println("开始视频");
                    //得到一张图片
                    //先保存服务端传过来的所有RGB值，再直接对缓冲区图片的每个像素点直接设置像素值   //摄像头获取的图片默认宽320高180
                    BufferedImage image = webcam.getImage();
//                    System.out.println("=========摄像图片位"+image);
                    System.out.println("图片宽"+image.getWidth()+"图片长"+image.getHeight());
                    g.drawImage(image,100,100,300,300,null);
                    //发送图片给客户端
                    sendImg((BufferedImage) image);
                }
            }
        }.start();
    }


    public BufferedImage convertImg(BufferedImage image){
        BufferedImage bufferedImage = new BufferedImage(128,128,BufferedImage.TYPE_3BYTE_BGR);
        Graphics bg = bufferedImage.getGraphics();
        //在缓冲区200*200的画布上画一张200*200的图片，未画的地方为黑色，返回画布
        bg.drawImage(image,0,0,128,128,null);
        return bufferedImage;
    }

    //将Image转为BufferedImage，可获取RGB值，宽高。
    public BufferedImage ImageToBuffer(Image image){
        BufferedImage bufferedImage = new BufferedImage(128,128,BufferedImage.TYPE_3BYTE_BGR);
        Graphics bg = bufferedImage.getGraphics();
        //在缓冲区200*200的画布上画一张200*200的图片，未画的地方为黑色，返回画布
        bg.drawImage(image,0,0,128,128,null);
        return bufferedImage;

    }

    //发送一张图片
    public void sendImg(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        //发送图片宽高给客户端
        writeInt(w);
        writeInt(h);

        int[][] imagePixel = new int[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                imagePixel[i][j] = image.getRGB(i, j);
//                System.out.println("imagePixel[i][j]"+imagePixel[i][j]);
            }
        }

        byte[] bytes = intarrToBytearr(imagePixel);
        System.out.println(bytes.length);


        //write字节数组一次最多2^16次方=65536个字节，160000个字节分3次write,160000和16000，0的个数别看错了
        try {

            dataOutputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //二维整数数组转字节数组，先将二维整数数组转一维整数数组，再将一维整数数组转为一维字节数组
    public byte[] intarrToBytearr(int[][] intarr){
        int w = intarr.length;
        int h = intarr[0].length;
        int k=0;
        //将二维数组转一维
        int[] array = new int[w*h];
        for(int i=0; i<w; i++){
            for(int j=0; j<h; j++){
//                System.out.println("intarr[i][j]"+intarr[i][j]);
                array[k++] = intarr[i][j];
            }
        }




        int length = array.length;
        byte[] byteInt = new byte[length*4];
        int t=0;
        //将一维整数数组转为一维字节数组
        for(int i=0; i<array.length; i++){
            byte[] curbyte = IntToByte(array[i]);

            byteInt[t++] = curbyte[0];
            byteInt[t++] = curbyte[1];
            byteInt[t++] = curbyte[2];
            byteInt[t++] = curbyte[3];
        }
//        for(int i=0; i<byteInt.length; i++){
//            System.out.println(byteInt[i]);
//        }
        return byteInt;
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


    //读整数
    public void writeInt(int x){

        //整数用一个长度为4的字节数组表示
        byte[] byteInt = new byte[4];
        //int10进制可以直接进行&运算转成32位2进制，结果依旧为10进制，计算机只是存储用2进制
        //0x表示16进制，0xff二进制为11111111,表示无符号位255，有符号位-1。
        byteInt[0] =(byte)((x >> 24) & 0xFF);   //x1的32位2进制右移动24位，取最高的八位，高位自动补0，存储用二进制，结果依旧为10进制
        byteInt[1] =(byte)((x >> 16) & 0xFF);   //x1的32位2进制右移16位，取最高的16位，和0xFF与运算，截取第二组二进制
        byteInt[2] =(byte)((x >> 8) & 0xFF);    //x1的32位2进制右移8位，取最高的24位，与0xFF与运算，截取第三组二进制
        byteInt[3] =(byte)((x >> 0) & 0xFF);    //x1的32位2进制右移0位，取全部32位，与0xFF与运算，截取最低的八位

        //一次写入一个字节数组
        //从整数高位开始写
            //先传int二进制32位中最高的8位，其次倒数第二高，即一个int值化为32位2进制，进行与运算，化为4个10进制数
            try {
                outputStream.write(byteInt);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




    //画图必须在重绘里面画，不然不会出来，有很多图的格式画不出来
    public void paint(Graphics g){
      super.paint(g);
//        g.drawImage(bufferedImage,0,0,200,200,null);
    }


}






