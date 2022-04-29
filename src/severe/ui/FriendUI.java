//package Severe.UI;
//
//import Severe.UI.MainUI.Friend.FriendPanel;
//import dao.UserDao;
//import dao.impl.daoimp;
//import Severe.Thread.MsgServerThread;
//
//import java.awt.*;
//import java.awt.event.*;
//import javax.swing.*;
//import javax.swing.event.TreeSelectionEvent;
//import javax.swing.event.TreeSelectionListener;
//import javax.swing.tree.DefaultMutableTreeNode;
//import javax.swing.tree.TreePath;
//import javax.swing.tree.TreeSelectionModel;
//
//public class FriendUI extends JFrame {
//
//
//    private JTree familyTree;      //JTree
//    private JScrollPane family_JScrollPane;      //滚动框
//    private TreePath path;   //用户选择的结点到根结点的路径
//    //    private DefaultMutableTreeNode selectedNode;    //被选中的节点
//    private JTextField nodeName_TextField;  //文本框
//    UserDao dao = new daoimp();      //数据库操作对象
//    String username;   //当前用户名
//
//
//    public FriendUI(UserDao dao, String name) {
//        this.dao = dao;
//        this.username = name;
//    }
//
//    ;
//
//    //初始化界面
//    public void initUI() {
//        this.setTitle("wemeet");
//        this.setSize(350, 650);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setLocationRelativeTo(null); //null设置为中心位置
//
//        FriendPanel bj = new FriendPanel();
//        Container container = this.getContentPane();   //获取窗体第三层容器
//        container.add(bj);    //给窗体第三层容器添加重写的面板对象
//        bj.setOpaque(true);
//        bj.setLayout(null);
//
//
//        //创建一个根节点
//        DefaultMutableTreeNode root = new DefaultMutableTreeNode("张三");
//        DefaultMutableTreeNode a1 = new DefaultMutableTreeNode("好友");
//        DefaultMutableTreeNode a2 = new DefaultMutableTreeNode("添加好友");
//        DefaultMutableTreeNode a3 = new DefaultMutableTreeNode("群");
//        root.add(a1);
//        root.add(a2);
//        root.add(a3);
//        DefaultMutableTreeNode a11 = new DefaultMutableTreeNode("我的好友");
//        DefaultMutableTreeNode a12 = new DefaultMutableTreeNode("家人");
//        DefaultMutableTreeNode a13 = new DefaultMutableTreeNode("同学");
//        DefaultMutableTreeNode a14 = new DefaultMutableTreeNode("黑名单");
//        a1.add(a11);
//        a1.add(a12);
//        a1.add(a13);
//        a1.add(a14);
//        DefaultMutableTreeNode a21 = new DefaultMutableTreeNode("账号");
//        DefaultMutableTreeNode a22 = new DefaultMutableTreeNode("添加至");
//        a2.add(a21);
//        a2.add(a22);
//        DefaultMutableTreeNode a31 = new DefaultMutableTreeNode("我的群");
//        DefaultMutableTreeNode a32 = new DefaultMutableTreeNode("创建群");
//        DefaultMutableTreeNode a33 = new DefaultMutableTreeNode("添加群");
//        a3.add(a31);
//        a3.add(a32);
//        a3.add(a33);
//
//
//        //创建树形组件
//        familyTree = new JTree(root);
//        //设置树形组件的字体，格式，大小
//        familyTree.setFont(new Font("微软雅黑", Font.PLAIN, 20));
//        //设置树形组件的单选模式，只能同时被选中一个结点
//        //先获得树形组件的选择模型对象，然后选择对象模型的单选模式
//        familyTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
//        //为树形组件添加选中结点的监听器
//        familyTree.addTreeSelectionListener(new TreeSelectionListener() {
//            //一旦用户选中结点的状态发生变化，立刻执行此方法
//            //用户必须两次选择的结点不同才会起作用，因为这样才代表结点状态改变
//            @Override
//            public void valueChanged(TreeSelectionEvent e) {
//                //当我们从树形组件上选择了一条结点的时候，那么从根结点到它的路径是确定的（因为是单继承）
//                path = e.getPath();  //获得根节点到被选中的结点的唯一路径
//            }
//        });
//
//
//        //创建滚动面板
//        family_JScrollPane = new JScrollPane(familyTree);
//        family_JScrollPane.setSize(350, 400);
//        family_JScrollPane.setLocation(0, 0);
//        bj.add(family_JScrollPane);
//
//
//        renameNode(username, root);
//        System.out.println("username" + username);
//
//        //数据库操作对象获取当前用户的信息（好友，黑名单）
//        String friend_name = dao.findFriend(username);
//        System.out.println("friend_name" + friend_name);
//        createNode(friend_name, a11);
//
//        String user_group = dao.finduser_group(username, friend_name);
//        System.out.println("当前用户" + username + "和好友" + friend_name + "的分组是" + user_group);
//        switch (user_group) {
//            case "家人":
//                createNode(friend_name, a12);
//                break;
//            case "同学":
//                createNode(friend_name, a13);
//                break;
//            default:
//
//        }
//
//        String blacklist = dao.findBlacklist(username);
//        System.out.println("blacklist" + blacklist);
//        createNode(blacklist, a14);
//        this.setVisible(true);
//
//
//        familyTree.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                int x = (int) e.getPoint().getX();
//                int y = (int) e.getPoint().getY();
//                System.out.println("x的坐标" + x);
//                System.out.println("y的坐标" + y);
////                TreePath path = familyTree.getPathForLocation(x, y);
////                familyTree.getComponentAt(x, y).repaint();
////                TreeCell.mouseRow = familyTree.getRowForLocation(x, y);
////                System.out.println("行数"+TreeCell.mouseRow);
//////                if(TreeCell.)
//////                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)familyTree.getLastSelectedPathComponent();
//////                if()
////                familyTree.repaint();
//
//                int curRow = familyTree.getRowForLocation(e.getX(), e.getY());
//                System.out.println("当前行数"+curRow);
//                TreePath curPath = familyTree.getPathForLocation(e.getX(), e.getY());
//                if (curRow != -1) {
//                    if (e.getClickCount() == 1) {
//                        int count = curPath.getPathCount();
//                        System.out.println("节点个数"+count);
//                        if(count > 2) {
////
//                        System.out.println("第一个节点值"+curPath.getPath()[0].toString());
//                        System.out.println("第二个节点值"+curPath.getPath()[1].toString());
//                        System.out.println("第三个节点值"+curPath.getPath()[2].toString());
//                            //如果上层节为好友，获取当前节点的名称，进行连接
//
//                            if ("好友".equals(curPath.getPath()[count - 1].toString())) {
//////                            javax.swing.JOptionPane.showMessageDialog(null, "测试点击");
//                                System.out.println("点击了我的好友");
//                            }else if("家人".equals(curPath.getPath()[count - 1].toString())){
//                                System.out.println("点击了家人");
//                            }else if("同学".equals(curPath.getPath()[count - 1].toString())){
//                                System.out.println("点击了同学");
//                            }else if("黑名单".equals(curPath.getPath()[count - 1].toString())){
//                                System.out.println("点击了黑名单");
//                            }
//                            if(count > 3){
//                                System.out.println("最后节点值"+curPath.getLastPathComponent().toString());
//                                MsgServerThread msgServerThread  = new MsgServerThread();
//                                msgServerThread.start();
//                                System.out.println("登录成功了");
//                            }
//                            System.out.println("父路径"+curPath.getParentPath());
//
//                        }
//
//                    }
//                }
//
//
//            }
//        });
//        familyTree.setCellRenderer(new DemoRenderer());
//
//
//    }
//
//
//    public void createNode(String nameString, DefaultMutableTreeNode node) {
//        //获得树形组件中被选中的结点
//        //这个方法的返回值是Object类型，此处需要强制类型转换
//        DefaultMutableTreeNode selectedNode = node;
//        selectedNode.add(new DefaultMutableTreeNode(nameString));
//        //展开被选中结点的路径上的所有结点
//        familyTree.expandPath(path);
//        //当我们给树形组件新增一个结点的时候，树形图上不会立刻显示新增的结点
//        //调用以下方法刷新树形组件
//        familyTree.updateUI();
//    }
//
//
//    public void deleteNode(String nameString) {
//        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) familyTree.getLastSelectedPathComponent();
//        if (selectedNode == null) {
//            System.out.println("请选中一个结点");
//            return;
//        }
//
//        if (selectedNode.isRoot()) {
//            System.out.println("不能删除根节点哦");
//            return;
//        }
//        selectedNode.removeFromParent();
//        familyTree.updateUI();
//    }
//
//
//    public void renameNode(String nameString, DefaultMutableTreeNode node) {
//        //获得树形组件中被选中的结点
//        //这个方法的返回值是Object类型，此处需要强制类型转换
//        DefaultMutableTreeNode selectedNode = node;
//        selectedNode.setUserObject(nameString);
//        familyTree.updateUI();
//    }
//
//
//    //检验用户是否选择了结点或进行了输入
//    private boolean checkNode_or_checkInput(DefaultMutableTreeNode node, String name) {
//        //判断用户是否选择了一个结点
//        if (node == null) {
//            System.out.println("请选择一个结点");
//            return false;
//        }
//        //判断用户是否进行了输入
//        if ("".equals(name)) {
//            System.out.println("请填写结点名称");
//            return false;
//        }
//        return true;
//    }
//}
//
//
//    //判断是否点击了好友
////    public void chatui(){}
////     createButton.addActionListener(new ActionListener() {
////        @Override
////        public void actionPerformed(ActionEvent arg0) {
////            //获得树形组件中被选中的结点
////            //这个方法的返回值是Object类型，此处需要强制类型转换
////            selectedNode = (DefaultMutableTreeNode)familyTree.getLastSelectedPathComponent();
////            String nameString = nodeName_TextField.getText();
////
////            if(checkNode_or_checkInput(selectedNode,nameString))
////            {
////                selectedNode.add(new DefaultMutableTreeNode(nameString));
////                //展开被选中结点的路径上的所有结点
////                familyTree.expandPath(path);
////                //当我们给树形组件新增一个结点的时候，树形图上不会立刻显示新增的结点
////                //调用以下方法刷新树形组件
////                familyTree.updateUI();
////                //创建之后清空文本域
////                nodeName_TextField.setText("");
////            }
////
////        }
////
////}