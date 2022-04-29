package client.ui.mainui.recent;

import client.ui.mainui.MyTreeNode;
import client.ui.mainui.MyTreeUI;
import client.ui.mainui.TreeUtils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class RecentPanel extends JPanel {

    private static final long serialVersionUID = 1683067312766564107L;

    /**
     * 鼠标滑过
     */
    private Color HOVER_COLOR = new Color(200, 200, 200, 100);

    /**
     * 鼠标点击
     */
    private Color SELECT_COLOR = new Color(160, 160, 160, 100);

    private JTree jTree;

    private DefaultMutableTreeNode root;
    private DefaultTreeModel model;




	public RecentPanel() {
        root = new DefaultMutableTreeNode();
        model = new DefaultTreeModel(root);

        MyTreeNode cate = new MyTreeNode(new ImageIcon("image/arrow_left.png"), "最近聊天");
        for (int j = 1; j <= 10; j++) {
            MyTreeNode node = new MyTreeNode(new ImageIcon("src/resource/image/penguin.jpg"), "好友" + j, "我自横刀向天笑");
            cate.add(node);
        }
        root.add(cate);



        jTree = new JTree(model);
        jTree.setUI(new MyTreeUI());

        jTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                TreeUtils.restoreUI(root, model, true);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                TreePath path = jTree.getSelectionPath();
                if (path == null) {
                    return;
                }
                MyTreeNode node = (MyTreeNode) path.getLastPathComponent();
                if (node == null) {
                    return;
                }
                // 除了好友节点，其他节点都没有点击选中功能
                if (node.getLevel() != 2) {
                    return;
                }
                // 已选择了，就不管，避免重复绘制
                if (node.isSelect()) {
                    return;
                }
                TreeUtils.restoreUI(root, model, false);
                TreeUtils.setBackColor(model, node, SELECT_COLOR);
                node.setSelect(true);
            }
        });

        jTree.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                TreePath path = jTree.getPathForLocation(e.getX(), e.getY());
                if (path == null) {
                    return;
                }
                MyTreeNode node = (MyTreeNode) path.getLastPathComponent();
                if (node == null) {
                    return;
                }
                // 已选择了，就不管，避免重复绘制
                if (node.isSelect()) {
                    return;
                }
                TreeUtils.restoreUI(root, model, true);
                TreeUtils.setBackColor(model, node, HOVER_COLOR);
            }
        });

        this.add(jTree);



	}

    }


