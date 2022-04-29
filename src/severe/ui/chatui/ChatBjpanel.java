package severe.ui.chatui;

import javax.swing.*;
import java.awt.*;

public class ChatBjpanel extends JPanel {   //重写JPanel,使Jpanel容器可以添加图片
    Image img;
    public ChatBjpanel(){
        //聊天界面背景图片
        img = Toolkit.getDefaultToolkit().getImage("src/resource/image/bg.jpg");
    }


    //面板捕捉图片,重写绘制容器的方法
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int imwidth = img.getWidth(this);
        int imHeight = img.getHeight(this);         //定义图片的高度宽度
        int FWidth = getWidth();
        int FHeight = getHeight();//定义窗口的宽度、高度
        int x = (FWidth - imwidth) / 2;
        int y = (FHeight - imHeight) / 2;//计算图片的坐标,使图片显示在窗口正中间

        g.drawImage(img,0,0,FWidth,FHeight,null);
    }
}
