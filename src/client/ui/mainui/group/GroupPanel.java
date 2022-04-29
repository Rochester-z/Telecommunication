package client.ui.mainui.group;

import client.ui.common.MyScrollBarUI;
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

public class GroupPanel extends JPanel {

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
    private JScrollPane jScrollPane;




	public GroupPanel() {



        root = new DefaultMutableTreeNode();
        model = new DefaultTreeModel(root);


        MyTreeNode cate1 = new MyTreeNode(new ImageIcon("src/respurce/image/arrow_left.png"), "我的QQ群1");
        for (int j = 1; j <= 3; j++) {
            MyTreeNode node = new MyTreeNode(new ImageIcon("src/resource/image/penguin.jpg"), "群" + j, "天不生夫子");
            cate1.add(node);
        }
        root.add(cate1);

        MyTreeNode cate2 = new MyTreeNode(new ImageIcon("src/image/arrow_left.png"), "我的QQ群2");
        for (int j = 1; j <= 3; j++) {
            MyTreeNode node = new MyTreeNode(new ImageIcon("src/resource/image/penguin.jpg"), "家人" + j, "万古如长夜");
            cate2.add(node);
        }
        root.add(cate2);
        MyTreeNode cate3 = new MyTreeNode(new ImageIcon("image/arrow_left.png"), "我的QQ群3");
        for (int j = 1; j <= 3; j++) {
            MyTreeNode node = new MyTreeNode(new ImageIcon("src/resource/image/penguin.jpg"), "同学" + j, "天不生我李淳罡");
            cate3.add(node);
        }

        root.add(cate3);



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
        jScrollPane = new JScrollPane();
        jScrollPane.setBorder(null);
        jScrollPane.setViewportView(jTree);  //在滚动面板中插入jTree
        //滚动条重绘
        jScrollPane.getVerticalScrollBar().setUI(new MyScrollBarUI());   //获取垂直滚动条并设置
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);//设置水平滚动条

        this.add(jScrollPane, BorderLayout.CENTER);

	}

    }


