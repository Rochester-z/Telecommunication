package client1.thread;


import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MsgClientThread extends Thread{
    public JFrame ui;

    public void run(){   //可以不加while,线程一直执行直到客户端连接
        ui.setVisible(false);
        //创建对象不自动调用主函数
        startServer();



    }


    public void startServer(){
        Socket client = null;
        try {
//          客户端设置连接服务器的ip为本机ip
            client = new Socket("127.0.0.1", 9999);   //创建10个客户端
        } catch (IOException e) {
            e.printStackTrace();
        }


//        System.out.println("连接成功");


        //创建客户端输入输出流对象
        InputStream inputStream = null;  //从服务端读入数据
        try {
            inputStream = client.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        OutputStream outputStream = null; //客户端向服务端输出数据
        try {
            outputStream = client.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //客户端先创建一个窗体,在发送框,接受框上写东西
        //发送框绑定客户端输出流对象,向服务端传输数据,接收框绑定客户端输入流对象,从服务端读入数据
//        MessageUI ui2 = new MessageUI(outputStream);
//        JTextPane jTextPane = ui2.InitUI();    //获取客户端窗体返回的接受框对象
//
//
//        ReadThread readThread = new ReadThread(inputStream, jTextPane);
//        new Thread(readThread).start();




    }
}
