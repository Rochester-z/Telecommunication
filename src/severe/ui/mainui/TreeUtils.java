package severe.ui.mainui;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;


public class TreeUtils {

    /**
     * 设置Node背景颜色
     *
     * @param model 模型，需要用它来刷新jtree
     * @param node  自定义的MyTreeNode节点
     * @param color 颜色
     */
    public static void setBackColor(DefaultTreeModel model, MyTreeNode node, Color color) {
        JPanel panel = null;

        if (node.getLevel() == 1) {
            panel = (JPanel) node.getGroupView();
        }

        if (node.getLevel() == 2) {
            panel = (JPanel) node.getBuddyView();
        }

        // 鼠标move的时候，此方法会被频繁调用，用颜色做一下比对，降低性能开销
        if (panel != null && panel.getBackground() != color) {
            panel.setBackground(color);
            model.reload(node);
        }
    }

    /**
     * 重置jtree的UI，将一些操作效果都还原
     *
     * @param root         根节点
     * @param model        模型
     * @param retainSelect 是否保留已选的Node
     */
    public static void restoreUI(DefaultMutableTreeNode root, DefaultTreeModel model, boolean retainSelect) {
        int count = root.getChildCount();
        if (count <= 0) {
            return;
        }
        // 重置二级节点UI
        for (int i = 0; i < count; i++) {
            MyTreeNode group = (MyTreeNode) root.getChildAt(i);
            restoreGroupUI(model, group);
            // 重置三级节点UI
            for (int j = 0; j < group.getChildCount(); j++) {
                MyTreeNode buddy = (MyTreeNode) group.getChildAt(j);
                // 保留已选择的Node
                if (retainSelect && buddy.isSelect()) {
                    continue;
                }
                restoreBuddyUI(model, buddy);
            }
        }
    }

    /**
     * 重置分组节点的UI
     *
     * @param model 模型
     * @param node  自定义Node
     */
    private static void restoreGroupUI(DefaultTreeModel model, MyTreeNode node) {
        node.getGroupView().setBackground(null);
        node.setSelect(false);
        model.reload(node);
    }

    /**
     * 重置好友节点的UI
     *
     * @param model 模型
     * @param node  自定义Node
     */
    private static void restoreBuddyUI(DefaultTreeModel model, MyTreeNode node) {
        node.getBuddyView().setBackground(null);
        node.setSelect(false);
        model.reload(node);
    }

}