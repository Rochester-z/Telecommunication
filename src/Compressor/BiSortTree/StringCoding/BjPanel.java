package Compressor.BiSortTree.StringCoding;

import javax.swing.*;
import java.awt.*;

public class BjPanel extends JPanel {           //重写JPanel,使Jpanel容器可以添加图片
    Image img;

    //JFrame 背景图片
    public BjPanel() {
//        img = Toolkit.getDefaultToolkit().getImage("D:\\IDEA 2019\\test\\Image\\chess2.jpg");
        img = Toolkit.getDefaultToolkit().getImage("Img/bg.jpg");
    }
        //面板捕捉图片
        public void paintComponent(Graphics g){        //重写绘制容器方法
            super.paintComponent(g);
            int imwidth = img.getWidth(this);
            int imHeight = img.getHeight(this);         //定义图片的高度宽度
            int FWidth = getWidth();
            int FHeight = getHeight();//定义窗口的宽度、高度
            int x = (FWidth - imwidth) / 2;
            int y = (FHeight - imHeight) / 2;//计算图片的坐标,使图片显示在窗口正中间

            g.drawImage(img,x,y,null);
//            g.drawImage(img, 300, 300, null);
        }


    }
