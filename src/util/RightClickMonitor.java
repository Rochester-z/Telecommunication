package util;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RightClickMonitor implements MouseListener {
    JPopupMenu jpopupmenu = new JPopupMenu();   //弹出式菜单

    public RightClickMonitor(JPopupMenu jPopupMenu){
        this.jpopupmenu = jPopupMenu;
    }

    //自己写的一个显示的方法
    public void display(MouseEvent e)
    {
        if(e.isPopupTrigger())  //当右键被点击
        {
            jpopupmenu.show(e.getComponent(), e.getX(),e.getY());  //在点击的容器的位置显示弹出曹丹
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //当鼠标释放时，判断是否点击了右键
        this.display(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
