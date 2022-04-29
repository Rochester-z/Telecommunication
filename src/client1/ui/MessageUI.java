//package client1.ui;
//
//import severe.listener.MessageListener;
//import severe.ui.chatui.ChatBjpanel;
//
//import javax.swing.*;
//import java.awt.*;
//import java.io.OutputStream;
//
//public class MessageUI extends JFrame {
//    OutputStream outputStream;   //输出流对象
//
//
//    public MessageUI(OutputStream outputStream){
//        this.outputStream = outputStream;
//    }
//
//    public JTextPane InitUI(){
//
//        setTitle("meet客户端1");
//        setSize(800,800);
//        setDefaultCloseOperation(3);
//        setLocationRelativeTo(null);      //窗体在中间位置
//
//        //设置窗体为流式布局,窗体默认边界中央布局
//
//        ChatBjpanel bj = new ChatBjpanel();
//        Container container = this.getContentPane();   //获取窗体第三层容器
//        container.add(bj);    //给窗体第三层容器添加重写的面板对象
//        bj.setOpaque(true);
//        //给窗体添加了重写的面板后窗体不可设置为流式布局,面板可以设置
//        //流式布局管理器对象，用Flowlayout流式布局管理器类创建对象，jf.setlayout函数参数为对象；面板默认，非窗体
//        bj.setLayout(null);
//
//
//
//
//        //接收显示框
//        JTextPane jTextPane1 = new JTextPane();
//        jTextPane1.setBounds(0,335,495,200);
//        //滚动面板
//        JScrollPane jScrollPane1 = new JScrollPane(jTextPane1);
//
//        //发送显示框
//        JTextPane jTextPane2 = new JTextPane();
//        jTextPane2.setBounds(0,580,500,120);
//        //滚动面板
//        JScrollPane jScrollPane2 = new JScrollPane(jTextPane2);
//
//        JButton btn1 = new JButton("发送消息");
//        btn1.setBounds(0,710,90,35);
//        btn1.setBackground(Color.cyan);
//        MessageListener messageListener = new MessageListener(jTextPane2,outputStream);
//        btn1.addActionListener(messageListener);
//        bj.addMouseListener(messageListener);   //捕捉坐标启动你画我猜
//
//        JButton btn2 = new JButton("发送图片");
//        btn2.setBounds(90,710,90,35);
//        btn2.setBackground(Color.cyan);
//        btn2.addActionListener(messageListener);
//
//
//        JButton btn3 = new JButton("发送文件");
//        btn3.setBounds(180,710,90,35);
//        btn3.setBackground(Color.cyan);
//        btn3.addActionListener(messageListener);
//
//
//        //接收框在上
//        //发送框在下
//        bj.add(jTextPane1);
//        bj.add(jTextPane2);
//        bj.add(btn1);
//        bj.add(btn2);
//        bj.add(btn3);
//
//        setVisible(true);
//
//
//        return jTextPane1;   //发送框的内容自己打上去,不用返回,接受框的内容需要在服务端接收端读取,需要返回
//    }
//
//    public void paint(Graphics g){
//        super.paint(g);
//    }
//}
