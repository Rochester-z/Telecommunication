package client.ui.mainui;

import client.ui.mainui.friend.FriendPanel;
import client.ui.mainui.group.GroupPanel;
import client.ui.mainui.recent.RecentPanel;
import com.alee.laf.tabbedpane.TabStretchType;
import com.alee.laf.tabbedpane.TabbedPaneStyle;
import com.alee.laf.tabbedpane.WebTabbedPane;
import dao.UserDao;
import dao.impl.UserdaoImpl;
import entity.User;
import util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow extends JFrame {
    /** 主面板 */
    private JPanel mainpanel;

    /** 基本信息面板 */
    private JPanel baseInfo;
    /** 最小化按钮 */
    private JLabel minButton;
    /** 皮肤按钮 */
    private JLabel skinButton;
    /** 退出按钮 */
    private JLabel exitButton;
    /** 标记（左上角） */
    private JLabel productInfo;
    /** 头像 */
    private JLabel picture;
    /** 签名 */
    private JLabel signature;
    /** 昵称 */
    private JLabel nickName;

    /** 搜索框面板 */
    private JPanel searchInfo;
    /** 搜索框 */
    private JTextField searchText;
    /** 搜索按钮 */
    private JLabel searchButton;

    /** 好友信息面板 */
    private JPanel userInfo;
    /** 分类面板（联系人、群组、会话） */
    private WebTabbedPane typeInfo;
    /** 联系人面板 */
    private FriendPanel friendPanel;
    /** 群组面板 */
    private GroupPanel groupPanel;
    /** 最近聊天面板 */
    private RecentPanel recentPanel;


    /** 面板默认背景色 */
    private static Color defaultBgColor = Color.WHITE;
    /** 坐标（用于记录鼠标拖拽时，鼠标按下那一刻的坐标） */
    private Point point = new Point();

    UserDao userdao = new UserdaoImpl();      //数据库操作对象
    User user;    //当前用户

    public MainWindow(User user, UserDao userDao){
        this.userdao = userDao;
        this.user = user;
        this.initGUI();
        this.initListener();
        this.setVisible(true);
    }


    public void initGUI() {
        System.out.println(user.toString());
        setSize(300, 649);
        setLocationRelativeTo(null);
        setUndecorated(true);       //消除菜单栏
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // 主面板
        mainpanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(new ImageIcon("src/resource/image/bg.jpg").getImage(), 0, 0, null);
                this.setOpaque(false);
            }
        };
        mainpanel.setLayout(null);
        getContentPane().add(mainpanel, BorderLayout.CENTER);

        // 基本信息面板
        baseInfo = new JPanel();
        mainpanel.add(baseInfo);
        baseInfo.setLayout(null);
        baseInfo.setOpaque(false);
        //面板设置位置叠加起来很好看，按钮可以设置图片但有边框
        baseInfo.setBounds(0, 0, 300, 118);
        baseInfo.setBorder(Constants.GRAY_BORDER);
        //标记（左上角）  JLable太强了
        productInfo = new JLabel();
        baseInfo.add(productInfo);
        productInfo.setBounds(8, 0, 50, 20);
        productInfo.setText("Wemeet");
        //头像
        picture = new JLabel();
        baseInfo.add(picture);
        picture.setBounds(7, 33, 66, 66);
        picture.setBorder(Constants.GRAY_BORDER);
        picture.setIcon(new ImageIcon(new ImageIcon(user.getProfile())
                .getImage().getScaledInstance(65, 65, Image.SCALE_DEFAULT)));
        //昵称
        nickName = new JLabel();
        baseInfo.add(nickName);
        nickName.setFont(Constants.BASIC_FONT2);
        //nickName.setText(user.getNickName());
        nickName.setText(user.getNickname());
        nickName.setBounds(80, 25, 156, 32);
        //个性签名
        signature = new JLabel();
        baseInfo.add(signature);
        signature.setFont(Constants.BASIC_FONT);
        //signature.setText(user.getSignature());
        signature.setText(user.getSignature());
        signature.setToolTipText(user.getSignature());
        signature.setBounds(80, 59, 165, 32);
        //皮肤按钮
        skinButton = new JLabel();
        baseInfo.add(skinButton);
        skinButton.setBounds(205, 2, 33, 18);
        skinButton.setIcon(new ImageIcon("src/resource/image/skin.png"));
        //最小化按钮
        minButton = new JLabel();
        baseInfo.add(minButton);
        minButton.setBounds(233, 0, 31, 20);
        minButton.setIcon(new ImageIcon("src/resource/image/minimize.png"));
        //退出按钮
        exitButton = new JLabel();
        baseInfo.add(exitButton);
        exitButton.setBounds(261, 0, 39, 20);
        exitButton.setIcon(new ImageIcon("src/resource/image/close.png"));
//
        // 搜索框面板
        searchInfo = new JPanel();
        mainpanel.add(searchInfo);
        searchInfo.setLayout(null);
        searchInfo.setOpaque(false);
        searchInfo.setBounds(0, 117, 300, 32);
        searchInfo.setBorder(Constants.GRAY_BORDER);
        //搜索框
        searchText = new JTextField();
        searchText.setText(Constants.SEARCH_TXT);
        searchInfo.add(searchText);
        searchText.setOpaque(false);
        searchText.setFocusable(false);
        searchText.setBounds(40, 1, 253, 30);
        searchText.setBorder(BorderFactory.createEmptyBorder());
        //搜索按钮
        searchButton = new JLabel();
        searchInfo.add(searchButton);
        searchButton.setBounds(1, 1, 45, 30);
        searchButton.setOpaque(false);
        searchButton.setBackground(defaultBgColor);
        searchButton.setIcon(new ImageIcon("src/resource/image/search_icon.png"));
//
        // 好友、群组、最近聊天面板
        userInfo = new JPanel();
        mainpanel.add(userInfo);
        userInfo.setLayout(new BorderLayout());
        userInfo.setOpaque(false);
        userInfo.setBounds(0, 148, 300, 500);
        userInfo.setBackground(defaultBgColor);
        // 类型面板（好友、群组、最近聊天） 选项卡面板，在一个窗体中有多个界面并在这些界面内随意切换
        typeInfo = new WebTabbedPane();
        userInfo.add(typeInfo, BorderLayout.CENTER);
        typeInfo.setOpaque(false);
        typeInfo.setTabbedPaneStyle(TabbedPaneStyle.attached);//不高亮边框
        typeInfo.setTabStretchType(TabStretchType.always);//适应宽度
        typeInfo.setTopBg(new Color(240, 240, 240, 60));
        typeInfo.setBottomBg(new Color(255, 255, 255, 160));
        typeInfo.setSelectedTopBg(new Color(240, 240, 255, 50));
        typeInfo.setSelectedBottomBg(new Color(240, 240, 255, 50));
        typeInfo.setBackground(new Color(255, 255, 255, 200));
        typeInfo.setBorder(Constants.GRAY_BORDER);


        //好友列表
        friendPanel = new FriendPanel(user, userdao);
        typeInfo.addTab(null, new ImageIcon(new ImageIcon("src/resource/image/tab_boddy.png")
                .getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT)), friendPanel);
        //群组列表
        groupPanel = new GroupPanel();
        typeInfo.addTab(null, new ImageIcon(new ImageIcon("src/resource/image/tab_group.png")  //界面名称设置为图片下接界面内容
                .getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT)), groupPanel);
        //最近聊天列表
        recentPanel = new RecentPanel();
        typeInfo.addTab(null, new ImageIcon(new ImageIcon("src/resource/image/tab_recent.png")
                .getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT)), recentPanel);


    }


    public void initListener() {
        // 主窗体事件
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                point.x = e.getX();
                point.y = e.getY();
            }
        });
        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point p = getLocation();
                setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
            }
        });
        // 头像区域事件（边框变色）
        picture.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                picture.setBorder(Constants.LIGHT_GRAY_BORDER);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                picture.setBorder(Constants.ORANGE_BORDER);
            }
        });
        // 换肤按钮事件
        skinButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                skinButton.setIcon(new ImageIcon("src/resource/image/skin.png"));
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                skinButton.setIcon(new ImageIcon("src/resource/image/skin_active.png"));
            }
        });
        // 最小化按钮事件
        minButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                minButton.setIcon(new ImageIcon("src/resource/image/minimize.png"));
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                minButton.setIcon(new ImageIcon("src/resource/image/minimize_active.png"));
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                setVisible(false);
            }
        });
        // 退出按钮事件
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                exitButton.setIcon(new ImageIcon("src/resource/image/close.png"));
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                exitButton.setIcon(new ImageIcon("src/resource/image/close_active.png"));
            }
//            @Override
            public void mouseReleased(MouseEvent e) {
                System.exit(0);  //结束进程
            }
        });
        // 搜索框鼠标事件
        searchText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Constants.SEARCH_TXT.equals(searchText.getText().trim())) {   //trim去除字符串首尾空格
                    searchText.setOpaque(true);
                    searchText.setFocusable(true);
                    searchText.requestFocus();
                    searchText.setText("");
                    searchText.updateUI(); //更新组件状态

                    searchButton.setOpaque(true);
                    searchButton.updateUI();
                }
            }
        });
        // 搜索框失去焦点事件
        searchText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                searchText.setOpaque(false);
                searchText.setFocusable(false);
                searchText.setText(Constants.SEARCH_TXT);
                searchText.updateUI();

                searchButton.setOpaque(false);
                searchButton.updateUI();
            }
        });
    }

}
