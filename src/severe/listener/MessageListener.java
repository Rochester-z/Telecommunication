package severe.listener;
import client1.thread.VideoThreadClient;
import severe.thread.VideoThreadServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;

public class MessageListener implements MouseListener {
    JTextPane jTextPane;         //发送框对象
    OutputStream outputStream;   //输出流对象
    Image image = new ImageIcon("imageqq/clothes.jpg").getImage();
    //创建文件对象
    File file = new File("C:\\Users\\zhuqi\\Desktop\\Study\\JAVA\\多表子查询练习.txt");

    Graphics g;
//    ImageIcon image = new ImageIcon("imageqq/face.jpg");

    public void setGraphics(Graphics g){
        this.g = g;
    }

    public MessageListener(OutputStream outputStream, JTextPane jTextPane){
        this.outputStream = outputStream;
        this.jTextPane = jTextPane;
    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        //点击发送的时候先判断消息的类型，文字为1，图片位2，文件为3。
//        String type = e.getActionCommand();
//        if("发送消息".equals(type)){
//            sendWord();
//        }else if("发送图片".equals(type)){
//            sendImg((BufferedImage)image);
//        }else if("发送文件".equals(type)){
//            sendDocument(file);
//        }
//    }

    @Override
    public void mouseClicked(MouseEvent e) {
        sendWord();
//        int x = e.getX();
//        int y = e.getY();
//        System.out.println("x坐标"+x+"y的坐标"+y);
//
//        if(x > 530 && x < 640 && y > 590 && y < 670){
//            System.out.println("启动你画我猜");
//
//        }
//        if(x > 10 && x < 40 && y > 20 && y < 60){
//            System.out.println("启动视频聊天");
//            //函数里有死循环，不用线程会卡死
//            VideoThreadServer videoThreadServer = new VideoThreadServer();
//            new Thread(videoThreadServer).start();
//
//            VideoThreadClient videoThreadClient = new VideoThreadClient();
//            new Thread(videoThreadClient).start();
//
//        }
//
//        if(x > 45 && x < 140 && y > 540 && y < 570){
////            jTextPane.insertIcon(image);
//            System.out.println("插入表情包");
//        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }



    //发文字
    public void sendWord(){
        String message = jTextPane.getText();  //获取输入框所有的内容
        byte[] messages = message.getBytes();   //将字符变成字节数组
//        System.out.println("messages的长度"+messages.length);
        try {
            //文字用一个字节1标记
            outputStream.write(1);
            outputStream.write(messages.length);  //输出流对象输出发送框的内容
            outputStream.write(messages);
            outputStream.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }



    //发送一张图片
    public void sendImg(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);   //数据输出流，发送图片文件
        //发送图片宽高给客户端
        try {
            dataOutputStream.writeInt(w);
            dataOutputStream.writeInt(h);
        } catch (IOException e) {
            e.printStackTrace();
        }



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


    //发文件
    public void sendDocument(File file){
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);   //数据输出流，发送图片文件
            //创建文件输入流对象
            FileInputStream fileInputStream = new FileInputStream(file);
            //获取文件的字节长度，为整数
            int filelen = fileInputStream.available();
            //读文件
            byte[] fileBytes = new byte[filelen];
            fileInputStream.read(fileBytes);
            //文件输入流用完后要关闭
            fileInputStream.close();

            //传文件
            //     消息头        消息体
            //协议：消息类型文件 3 文件名字节数 文件名 文件字节数 文件内容的字节
            dataOutputStream.writeByte(3);
            //写名字字节的长度
            String name = "编程范式—面向对象.txt";
            byte[] nameBytes = name.getBytes();
            int namelength = nameBytes.length;
            dataOutputStream.writeInt(namelength);
            //写名字内容
            dataOutputStream.write(nameBytes);
            //写文件字节的长度
            dataOutputStream.writeInt(filelen);
            //写文件的内容
            dataOutputStream.write(fileBytes);
            System.out.println("发送文件成功");

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

}
