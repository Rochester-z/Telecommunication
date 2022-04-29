package severe.ui.mainui;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;


public class MyTreeCellRenderer extends DefaultTreeCellRenderer {

    private static final long serialVersionUID = -3617708634867111249L;

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean sel, boolean expanded, boolean leaf, int row,
                                                  boolean hasFocus) {
        MyTreeNode node = (MyTreeNode) value;

        // 根节点从0开始，依次往下
        // 一级节点
        if (node.getLevel() == 1) {
            if (expanded) {
                node.getIconLabel().setIcon(new ImageIcon("src/resource/image/arrow_down.png"));
            } else {
                node.getIconLabel().setIcon(new ImageIcon("src/resource/image/arrow_left.png"));
            }
            return node.getGroupView();
        }

        // 二级节点
        if (node.getLevel() == 2) {
//            node.getIconLabel().setIcon(new ImageIcon("src/resource/image/penguin.jpg"));
            return node.getBuddyView();
        }
        return this;
    }

}