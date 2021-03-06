package severe.ui.mainui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.*;


public class MyTreeUI extends BasicTreeUI {

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        JTree jTree = (JTree) c;
        jTree.setRootVisible(false);// 这个一定要设置，否则会报错类型转换异常
        jTree.setToggleClickCount(1);// 单击展开
        jTree.setCellRenderer(new MyTreeCellRenderer());
    }

    // 去除JTree的垂直线
    @Override
    protected void paintVerticalLine(Graphics g, JComponent c, int x, int top, int bottom) {
    }

    // 去除JTree的水平线
    @Override
    protected void paintHorizontalLine(Graphics g, JComponent c, int y, int left, int right) {
    }

    // 实现父节点与子节点左对齐
    @Override
    public void setLeftChildIndent(int newAmount) {
    }

    // 实现父节点与子节点右对齐
    @Override
    public void setRightChildIndent(int newAmount) {
    }

}