package severe;

import javax.swing.*;
import java.awt.*;

public class Test extends JFrame{
    Image image = new ImageIcon("src/resource/image/Expression2.png").getImage();
    public static void main(String[] args) {
        Test test = new Test();
        test.InitUI();
    }


    
    public void InitUI(){
        this.setSize(700, 700);
        this.setVisible(true);
        Graphics g = this.getGraphics();
    }


    public void paint(Graphics g){
        super.paint(g);
        g.drawLine(100, 100, 500, 500);
        System.out.println("图片"+image);
        g.drawImage(image, 10, 10, 200,200,null);
    }
}
