package severe.thread;

import severe.listener.MessageListener;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MsgServerThread extends Thread {
//   public JFrame ui;         //public其他包下也可调用


    /**发送消息框*/
    JTextPane inputTextPane;
    /**历史消息文本框 */
    private JTextPane historyJTextPane;
    /** 发送按钮 */
    private JLabel sendButton;       //将发送按钮传入通信线程
    String friendname;               //将好友名传入通信线程

    public JTextPane getInputTextPane() {
        return inputTextPane;
    }

    public void setInputTextPane(JTextPane inputTextPane) {
        this.inputTextPane = inputTextPane;
    }

    public JTextPane getHistoryJTextPane() {
        return historyJTextPane;
    }

    public void setHistoryJTextPane(JTextPane historyJTextPane) {
        this.historyJTextPane = historyJTextPane;
    }

    public JLabel getSendButton() {
        return sendButton;
    }

    public String getFriendname() {
        return friendname;
    }

    public void setFriendname(String friendname) {
        this.friendname = friendname;
    }

    public void setSendButton(JLabel sendButton) {
        this.sendButton = sendButton;
    }

    static MsgServerThread msgServerThread = new MsgServerThread();


    //单例模式，不需要调用构造方法，设为私有且空
    private MsgServerThread(){
    }
    //单例模式，确保点击好友时，聊天线程只被创建一次
    public static MsgServerThread getsingleton(){
        return msgServerThread;
    }

    public void run(){
        startServer();
    }


    public void startServer() {


        //1.建立服务端和客户端连接对象
        ServerSocket serverSocket = null;
        try {
            //服务器默认ip:localhost，设置进程端口为9999
            serverSocket = new ServerSocket(9999);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("等待连接");
        //2.创建服务端中的客户端对象,直到有客户端连接上才启动,监听连接
        Socket client = null;
        try {
            client = serverSocket.accept();     //有阻塞必须放线程里
        } catch (IOException e) {
            e.printStackTrace();
        }

        OutputStream outputStream = null;      //向客户端传出数据
        //3.创建服务端中的客户端对象的输入输出流
        try {
            outputStream = client.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            outputStream.flush();  //输出所有数据刷新缓冲,管道强制退出
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream inputStream = null;         //从客户端中读入数据
        try {
            inputStream = client.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //发送框需要绑定服务端客户端对象的输出流向客户端传输
        //发消息线程需要绑定发送框
        MessageListener messageListener = new MessageListener(outputStream, inputTextPane);
        //添加两个监听器
        sendButton.addMouseListener(messageListener);
        //接受框需要绑定服务端客户端对象的输入流从客户端读入数据
        //读消息线程绑定接收框
        ReadThread readThread = new ReadThread(inputStream, historyJTextPane, friendname);
        new Thread(readThread).start();


        System.out.println("=========================通信成功");



    }
}
