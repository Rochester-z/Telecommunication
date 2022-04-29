package client.thread;


import client.listener.MessageListener;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MsgClientThread extends Thread{

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

    public void setSendButton(JLabel sendButton) {
        this.sendButton = sendButton;
    }

    public String getFriendname() {
        return friendname;
    }

    public void setFriendname(String friendname) {
        this.friendname = friendname;
    }

    static MsgClientThread msgClientThread = new MsgClientThread();


    private MsgClientThread(){

    }

    public static MsgClientThread getsingleton(){
        return msgClientThread;
    }


    public void run(){   //可以不加while,线程一直执行直到客户端连接
        //创建对象不自动调用主函数
        startClient();
    }


    public void startClient(){
        Socket client = null;
        try {
//          客户端设置连接服务器的ip为本机ip
            client = new Socket("127.0.0.1", 9999);   //创建10个客户端
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("连接成功");


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


        //发送框需要绑定服务端客户端对象的输出流向客户端传输
        //发消息线程需要绑定发送框
        MessageListener messageListener = new MessageListener(outputStream, inputTextPane);
        //添加两个监听器
        sendButton.addMouseListener(messageListener);
        //接受框需要绑定服务端客户端对象的输入流从客户端读入数据
        //读消息线程绑定接收框
        ReadThread readThread = new ReadThread(inputStream, historyJTextPane, friendname);
        new Thread(readThread).start();


    }
}
