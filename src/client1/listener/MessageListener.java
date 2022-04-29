package client1.listener;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.OutputStream;

public class MessageListener implements ActionListener, MouseListener {
    JTextPane jTextPane;         //发送框对象
    OutputStream outputStream;   //输出流对象

    public MessageListener(JTextPane jTextPane, OutputStream outputStream){
        this.jTextPane = jTextPane;
        this.outputStream = outputStream;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //        System.out.println("向客户端传数据");
        String message = jTextPane.getText();  //获取输入框所有的内容

        byte[] messages = message.getBytes();   //将字符变成字节数组
//        System.out.println("messages的长度"+messages.length);
        try {
            outputStream.write(messages.length);  //输出流对象输出发送框的内容
            outputStream.write(messages);
            outputStream.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if(x > 530 && x < 640 && y > 590 && y < 670){
            System.out.println("启动你画我猜");
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("启动视频聊天");

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
}
