package client.ui.mainui.friend;

import client.ui.chatui.ChatUI;
import client.ui.common.MyScrollBarUI;
import client.ui.mainui.MyTreeNode;
import client.ui.mainui.MyTreeUI;
import client.ui.mainui.TreeUtils;
import dao.GroupMembersDao;
import dao.GroupOwnerDao;
import dao.GroupsDao;
import dao.UserDao;
import dao.impl.GroupDaoImpl;
import dao.impl.GroupMembersDaoImpl;
import dao.impl.GroupOwnerDaoImpl;
import entity.GroupMembers;
import entity.GroupOwner;
import entity.Groups;
import entity.User;
import util.RightClickMonitor;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;


public class FriendPanel extends JPanel {

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

    UserDao userdao;      //数据库操作类
    User user;   //当前用户
    public FriendPanel(User user, UserDao userDao)
    {
        this.user = user;
        this.userdao = userDao;
        initGUI();
    }

    private void initGUI() {
        root = new DefaultMutableTreeNode();
        model = new DefaultTreeModel(root);
        //好友分组从数据库groups_owner表导入
        GroupOwnerDao groupOwnerDao = new GroupOwnerDaoImpl();
        GroupMembersDao groupMembersDao = new GroupMembersDaoImpl();
        GroupsDao groupsDao = new GroupDaoImpl();
        //在groups表中通过owner_id获取对应的所有行，owner_id为user表中的uid，在service层需要用的时候调用
        List <GroupOwner> list = groupOwnerDao.getListByOwnid(user.getUid());
        System.out.println("列表长度"+list.size());
        for(GroupOwner groupOwner : list){
            String g_id = groupOwner.getG_id();
            Groups group = groupsDao.getGroupsByGid(g_id);
            //添加所有的分组
            String group_name = group.getGroup_name();
            MyTreeNode cate = new MyTreeNode(new ImageIcon("image/arrow_left.png"), group_name);
            root.add(cate);
            //添加该分组对应的所有好友
            //分组对应好友从group_members表导入
            //根据分组id和用户id查询GroupMembers对应所有行
            List<GroupMembers> list1 = groupMembersDao.getListByGidAndOwnid(g_id,user.getUid());
            System.out.println("list1的大小"+list1.size());
            for(int i=0; i<list1.size(); i++){
                String mem_id = list1.get(i).getMem_id();
                User frienduser = userdao.findUserByUid(mem_id);
                System.out.println("图片地址"+frienduser.getProfile());
                MyTreeNode node = new MyTreeNode(frienduser);
                cate.add(node);
            }
        }


        jTree = new JTree(model);
        jTree.setUI(new MyTreeUI());

        jTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                System.out.println("结束");
                TreeUtils.restoreUI(root, model, true);
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                TreePath path = jTree.getSelectionPath();
                if (path == null) {
                    //path为空返回
                    return;
                }
                MyTreeNode node = (MyTreeNode) path.getLastPathComponent();
                System.out.println("节点的level"+node.getLevel());

                if (node == null) {
                    //节点为空返回
                    return;
                }
                // 除了好友节点，其他节点都没有点击选中功能
                if (node.getLevel() == 2) {
                    System.out.println("节点是好友"+ node.getName());
                    //判断好友的名称
                    if (node.isSelect()) {
                        //好友被点击了，已选择了，打开聊天框，就不管，避免重复绘制
                        System.out.println("好友"+node.getName()+"被点击了2次");
                        ChatUI chatUI = new ChatUI(user,node.getFrienduser());
                        chatUI.InitUI();
                        chatUI.initListener();
                        return;
                    }
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
//                    System.out.println("移动path为空");
                    return;
                }
                MyTreeNode node = (MyTreeNode) path.getLastPathComponent();
                if (node == null) {
//                    System.out.println("移动节点为空");
                    return;
                }
                // 已选择了，就不管，避免重复绘制
                if (node.isSelect()) {
//                    System.out.println("移动节点被选择了");
                    return;
                }
                TreeUtils.restoreUI(root, model, true);
                TreeUtils.setBackColor(model, node, HOVER_COLOR);
            }
        });


        JPopupMenu jpopupmenu = new JPopupMenu();   //弹出式菜单
        JMenuItem jmenuitem1 = new JMenuItem("发送即时消息");
        JMenuItem jmenuitem2 = new JMenuItem("删除好友");
        JMenuItem jmenuitem3 = new JMenuItem("移动好友至");
        jmenuitem1.setBackground(Color.cyan);
        jmenuitem2.setBackground(Color.cyan);
        jmenuitem3.setBackground(Color.cyan);
        jpopupmenu.add(jmenuitem1);   //弹出式菜单添加一个菜单项
        jpopupmenu.add(jmenuitem2);   //弹出式菜单添加一个菜单项
        jpopupmenu.add(jmenuitem3);   //弹出式菜单添加一个菜单项
        jTree.addMouseListener(new RightClickMonitor(jpopupmenu));


        this.add(jTree);
        jScrollPane = new JScrollPane();
        jScrollPane.setBorder(null);
        jScrollPane.setViewportView(jTree);  //在滚动面板中插入jTree
        //滚动条重绘
        jScrollPane.getVerticalScrollBar().setUI(new MyScrollBarUI());   //获取垂直滚动条并设置个数
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);//设置水平滚动条策略

        this.add(jScrollPane, BorderLayout.CENTER);
    }


}