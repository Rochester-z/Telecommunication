package client.thread;

import javax.swing.*;
import java.io.*;

public class ReadThread implements Runnable {
    InputStream inputStream;
    JTextPane jTextPane;
    DataInputStream dataInputStream;  //数据输入流读图片，文件
    String friendname;   //好友名

    public ReadThread(InputStream inputStream, JTextPane jTextPane, String friendname) {
        this.inputStream = inputStream;
        this.jTextPane = jTextPane;
        this.friendname = friendname;

        //属性最先被加载，之后是构造函数
        dataInputStream = new DataInputStream(inputStream);
    }

    @Override
    public void run() {
        //一直读取数据
        while (true) {
            //如果连接成功继续读取数据
            //首先读消息首字节，判断消息的类型
            int type = 0;
            try {
                type = dataInputStream.readByte();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (type == 1) {
                //接收文字
                receiveWord();
            } else if (type == 2) {
                //接收图片
                receiveImage();
            } else if (type == 3) {
                //接受文件
                receiveFile();
            }
        }
    }


    //读消息
    public void receiveWord() {
        int messagelength = 0;  //首字节存储消息的长度
        try {
            //必须读取到字节，无字节一直在读取，处于阻塞状态
            messagelength = inputStream.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] messagebytes = new byte[messagelength];  //创建消息长度的字节数组保存消息
        //当服务端关闭时，客户端读取数据线程一直打开，此时读不到数据，出错
        try {
            inputStream.read(messagebytes);  //数组中存储接下来的消息
        } catch (IOException e) {
            e.printStackTrace();
        }
//            System.out.println("消息的长度是" + messagelength);

        String getmessage = new String(messagebytes);   //将从客户端读入的数据转成字符,每次读数据读当前输入框的数据
//                System.out.println("jTextPane" + jTextPane);
        String msg = jTextPane.getText();     //获取接收框的内容
        //接收框显示所有的内容
        jTextPane.setText(msg + friendname + ":" + getmessage + "\n");         //设置接收框的内容"\n"换行
    }



    //读文件
    public void receiveFile() {
        //协议：消息类型文件 3 文件名字节数 文件名 文件字节数 文件内容的字节
        try {

            //收文件
            //读名字字节的长度
            int namelength;
            namelength = dataInputStream.readInt();
            System.out.println("名字字节的长度"+namelength);
            //读名字字节的内容
            byte[] nameBytes = new byte[namelength];
            dataInputStream.read(nameBytes);
            //读文件字节的长度
            int filelength = dataInputStream.readInt();
            System.out.println("文件字节的长度"+filelength);
            byte[] fileBytes = new byte[filelength];
            //读文件字节的内容
            dataInputStream.read(fileBytes);

            //写文件
            String filename = new String(nameBytes);
            File file = new File("src/zq0922/Client1/"+filename);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(fileBytes);
            System.out.println("接收文件成功");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void receiveImage(){


    }


}
