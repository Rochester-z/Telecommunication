package client.ui.chatui;

import client.thread.MsgClientThread;
import client.ui.common.Emoticon;
import client.ui.common.MyScrollBarUI;
import entity.User;

import util.Constants;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;


//当进入聊天界面时，与客户端建立连接。（改进，与服务端建立连接转发）
public class ChatUI extends JFrame {

    /** 主面板 */
    private JPanel mainpanel;

    /** 好友信息面板 */
    private JPanel friendInfo;
    /** 最小化按钮 */
    private JLabel minButton;
    /** 退出按钮 */
    private JLabel exitButton;
    /** 标记（左上角） */
    private JLabel productInfo;
    /**好友头像*/
    private JLabel picture;
    /**好友昵称*/
    private JLabel nickname;

    /** 历史消息面板 */
    private JPanel history;
    /**历史消息文本框 */
    private JTextPane historyJTextPane;
    /**历史消息滚动面板*/
    private JScrollPane historyScroll;

    /** 功能面板 */
    private JPanel tool;
    /**抖动按钮*/
    private JLabel shake;
    /**表情按钮*/
    private  JLabel expression;
    /** 字体按钮 */
    private JLabel textFont;
    /**文件按钮 */
    private JLabel file;
    /**图片按钮 */
    private JLabel img;
    /**视频按钮*/
    private JLabel video;

    /** 发送消息面板 */
    private JPanel input;
    /**发送消息框*/
    JTextPane inputTextPane;
    /**发送消息滚动面板*/
    JScrollPane inputScroll;

    /** 关闭按钮 */
    private JButton quitButton;
    /** 发送按钮 */
    private JLabel sendButton;

    /** 坐标（用于记录鼠标拖拽时，鼠标按下那一刻的二维坐标） */
    private Point point = new Point();

    /** 记录图片 */
    /**图片对话框*/
    public Emoticon image;
    private StringBuffer imgBuffer = new StringBuffer();
    private String msg;
    public int position;

    /**字体面板*/
    private JPanel fontPane;
    private boolean isFonting;// 是否正在编辑字体
    private JComboBox fontName = null;// 字体名称下拉列表框
    private JComboBox fontSize = null;// 字号大小下拉列表框
    private JComboBox fontStyle = null;// 文字样式下拉列表框
    private JComboBox fontForeColor = null;// 文字颜色下拉列表框
    private JComboBox fontBackColor = null;// 文字背景颜色下拉列表框
    private String[] str_name = { "宋体", "黑体", "Dialog", "Gulim" };
    private String[] str_Size = { "15", "17", "19", "21", "23" };
    private String[] str_Style = { "常规", "斜体", "粗体", "粗斜体" };
    private String[] str_Color = { "黑色", "橙色", "黄色", "绿色" };
    private String[] str_BackColor = { "白色", "灰色", "淡红", "淡蓝", "淡黄", "淡绿" };

    User user;  //当前用户
    User friend; //当前用户的聊天好友



    //聊天窗口传入当前用户和好友
    public ChatUI(User user, User frienduser){
        this.user = user;
        this.friend = frienduser;
    }

    public void InitUI(){

        setSize(900,700);
        setDefaultCloseOperation(3);
        setLocationRelativeTo(null);      //窗体在中间位置
        setUndecorated(true);



        mainpanel = new JPanel(){
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                    g.drawImage(new ImageIcon("src/resource/image/chatbg.jpg").getImage(), 0, 0, 900,700,null);
                    this.setOpaque(false);
                }
        };

        mainpanel.setLayout(null);
        getContentPane().add(mainpanel,BorderLayout.CENTER);


        //好友信息面板
        friendInfo = new JPanel();
        mainpanel.add(friendInfo);
        friendInfo.setLayout(null);
        friendInfo.setOpaque(false);    //设置不透明，不显示颜色
        friendInfo.setBounds(0,0,900,50);
//        friendInfo.setBorder(Constants.GRAY_BORDER);

        //最小化按钮
        minButton = new JLabel();
        friendInfo.add(minButton);
        minButton.setBounds(833, 0, 31, 20);
        minButton.setIcon(new ImageIcon("src/resource/image/minimize.png"));
        //退出按钮
        exitButton = new JLabel();
        friendInfo.add(exitButton);
        exitButton.setBounds(861, 0, 39, 20);
        exitButton.setIcon(new ImageIcon("src/resource/image/close.png"));

        //好友昵称
        nickname = new JLabel();
        friendInfo.add(nickname);
        nickname.setFont(Constants.DIALOG_FONT2);
        nickname.setBounds(410,0,60,50);
        nickname.setText(friend.getNickname());

        //视频功能
        video = new JLabel();
        friendInfo.add(video);
        video.setBounds(10, 12, 30, 30);
        video.setToolTipText("视频");
        video.setIcon(new ImageIcon(new ImageIcon("src/resource/image/Video.png")
                .getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));


        //历史消息面板
        history = new JPanel();
        mainpanel.add(history);
        history.setOpaque(false);
        history.setBounds(0,50,900,440);
//        history.setBorder(Constants.ORANGE_BORDER);
        history.setLayout(new BorderLayout());  //滚动面板设置center从而占满面板

        //历史消息文本框
        historyJTextPane = new JTextPane();
//      historyJTextPane.setEditable(false);//不允许编辑

        historyScroll = new JScrollPane();
        //滚动条重绘
        historyScroll.setViewportView(historyJTextPane);
        historyScroll.getVerticalScrollBar().setUI(new MyScrollBarUI());   //获取垂直滚动条并设置
        historyScroll.setBorder(Constants.LIGHT_GRAY_BORDER); //设置水平滚动条
//      historyScroll.setBounds(0,60,700,400);   //滚动面板内嵌文本框，null布局只能对滚动面板设置大小从而改变文本框大小
        history.add(historyScroll);


        //功能面板 内嵌空布局为相对位置
        tool = new JPanel();
        mainpanel.add(tool);
        tool.setLayout(null);
        tool.setOpaque(false);
        tool.setBounds(0,490,900,40);
//        tool.setBorder(Constants.GRAY_BORDER);

        shake = new JLabel();
        tool.add(shake);
        shake.setBounds(10, 6, 30, 30);
        shake.setToolTipText("抖动");
        //Lable标签图标默认很小，设置比例放大
        shake.setIcon(new ImageIcon(new ImageIcon("src/resource/image/Shake.png") //图片名称位置无大小写区别
                .getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));

        expression = new JLabel();
        tool.add(expression);
        expression.setBounds(40, 6, 30, 30);
        expression.setToolTipText("表情");
        expression.setIcon(new ImageIcon(new ImageIcon("src/resource/image/Expression2.png")
                .getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));

        textFont = new JLabel();
        tool.add(textFont);
        textFont.setBounds(75, 6, 30, 30);
        textFont.setToolTipText("字体");
        textFont.setIcon(new ImageIcon(new ImageIcon("src/resource/image/Textfont.png")
                .getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));

        file = new JLabel();
        tool.add(file);
        file.setBounds(110, 6, 30, 30);
        file.setToolTipText("文件");
        file.setIcon(new ImageIcon(new ImageIcon("src/resource/image/File.png")
                .getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));


        img = new JLabel();
        tool.add(img);
        img.setBounds(145, 6, 30, 30);
        img.setToolTipText("图片");
        img.setIcon(new ImageIcon(new ImageIcon("src/resource/image/Img2.png")
                .getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));


        //发送消息面板
        input = new JPanel();
        mainpanel.add(input);
        input.setOpaque(false);
        input.setBounds(0, 530, 900, 132);
//        input.setBorder(Constants.ORANGE_BORDER);
        input.setLayout(new BorderLayout());

        //发送消息文本框
        inputTextPane = new JTextPane();
        inputScroll = new JScrollPane();
        //滚动条重绘
        inputScroll.setViewportView(inputTextPane);
        inputScroll.getVerticalScrollBar().setUI(new MyScrollBarUI());   //获取垂直滚动条并设置
        inputScroll.setBorder(Constants.LIGHT_GRAY_BORDER); //设置水平滚动条
        input.add(inputScroll, BorderLayout.CENTER);

        // 关闭按钮
        quitButton = new JButton();
        mainpanel.add(quitButton);
        quitButton.setBounds(680, 665, 90, 30);
        quitButton.setBorder(Constants.LIGHT_GRAY_BORDER);
        quitButton.setIcon(new ImageIcon("src/resource/image/quitButton.png"));

        // 发送按钮
        sendButton = new JLabel();
        mainpanel.add(sendButton);
        sendButton.setBounds(790, 665, 90, 30);
        sendButton.setBorder(Constants.LIGHT_GRAY_BORDER);
        sendButton.setIcon(new ImageIcon("src/resource/image/sendButton.png"));


        // 编辑字体（只做了几个示例）
        fontPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                try { // 使用Windows的界面风格
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        mainpanel.add(fontPane);
        fontPane.setBounds(1, 450, 800, 40);
        fontPane.setBorder(Constants.LIGHT_GRAY_BORDER);
        fontPane.setLayout(new BoxLayout(fontPane, BoxLayout.X_AXIS)); //盒式布局，从左到右水平布置组件
        //JComboBox下拉列表框
        fontName = new JComboBox(str_name); // 字体名称
        fontName.setFont(Constants.BASIC_FONT);
        fontSize = new JComboBox(str_Size); // 字号
        fontSize.setFont(Constants.BASIC_FONT);
        fontStyle = new JComboBox(str_Style); // 样式
        fontStyle.setFont(Constants.BASIC_FONT);
        fontForeColor = new JComboBox(str_Color); // 颜色
        fontForeColor.setFont(Constants.BASIC_FONT);
        fontBackColor = new JComboBox(str_BackColor); // 背景颜色
        fontBackColor.setFont(Constants.BASIC_FONT);

        // 开始将所需组件加入容器
        JLabel jlabel_1 = new JLabel("字体：");
        jlabel_1.setFont(Constants.BASIC_FONT);
        JLabel jlabel_2 = new JLabel("样式：");
        jlabel_2.setFont(Constants.BASIC_FONT);
        JLabel jlabel_3 = new JLabel("字号：");
        jlabel_3.setFont(Constants.BASIC_FONT);
        JLabel jlabel_4 = new JLabel("颜色：");
        jlabel_4.setFont(Constants.BASIC_FONT);
        JLabel jlabel_5 = new JLabel("背景：");
        jlabel_5.setFont(Constants.BASIC_FONT);
        fontPane.add(jlabel_1); // 加入标签
        fontPane.add(fontName); // 加入组件
        fontPane.add(jlabel_2);
        fontPane.add(fontStyle);
        fontPane.add(jlabel_3);
        fontPane.add(fontSize);
        fontPane.add(jlabel_4);
        fontPane.add(fontForeColor);
        fontPane.add(jlabel_5);
        fontPane.add(fontBackColor);

        setVisible(true);

        //开启通信线程
        startChar();
    }

    //接收通信线程的输入输出流
    InputStream inputStream;
    OutputStream outputStream;
    public void startChar(){

        //      与好友建立通信，开启通信线程，调用start方法是线程方法（可不带while），否则是普通方法

        //单例模式创建唯一通信线程
        MsgClientThread msgClientThread = MsgClientThread.getsingleton();
        //将发送框接收框传输给通信线程
        msgClientThread.setInputTextPane(inputTextPane);
        msgClientThread.setHistoryJTextPane(historyJTextPane);
        //将发送按钮传输给通信线程
        msgClientThread.setSendButton(sendButton);
        //将好友名传给通信线程
        String friendname = friend.getNickname();
        msgClientThread.setFriendname(friendname);


        msgClientThread.start();
        //主线程直接运行完，不会等其他线程
//       inputStream = msgServerThread.getInputStream();
//       outputStream = msgServerThread.getOutputStream();

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


        // 工具栏-抖动
        shake.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                shake.setBorder(BorderFactory.createEmptyBorder());
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                shake.setBorder(Constants.LIGHT_GRAY_BORDER);
            }
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                client.sendMsg(createMsg(Constants.SHAKE_MSG, null));
//            }
        });


        // 工具栏-表情
        expression.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                expression.setBorder(BorderFactory.createEmptyBorder());
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                expression.setBorder(Constants.LIGHT_GRAY_BORDER);
            }

            public void mouseClicked(MouseEvent e) {
                if (image == null) {
                    image = new Emoticon(ChatUI.this);   //匿名内部类调用外部类的对象
                    image.setVisible(true);
                    // 设置控件相对于父窗体的位置
                    Point loc = getLocationOnScreen();  //获取主窗体左上角位置
                    image.setBounds(loc.x + 20, loc.y + 100, 350, 300);
//                  image.requestFocus();

                }
                else {
                    image.dispose();
                    image = null;
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
        });


        // 工具栏-字体
        textFont.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                if (!isFonting) {  //当离开字体标签且不在编辑字体
                    textFont.setBorder(BorderFactory.createEmptyBorder());
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                textFont.setBorder(Constants.LIGHT_GRAY_BORDER);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!isFonting) {
                    isFonting = true;  //当在编辑字体时，缩小历史消息面板，在下方留出空间插入字体面板
                    textFont.setBorder(Constants.LIGHT_GRAY_BORDER);
                    history.setBounds(0, 50, 900, 400);
                } else {
                    isFonting = false; //当不在编辑字体时，还原历史消息面板大小，隐藏字体面板
                    textFont.setBorder(BorderFactory.createEmptyBorder());
                    history.setBounds(0, 50, 900, 440);
                }
            }
        });

        // 字体名称添加选项监听器
        fontName.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                updateInputStyle();
                updatahistoryStyle();
            }
        });
        //字号大小添加选项监听器
        fontSize.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                updateInputStyle();
                updatahistoryStyle();
            }
        });
        //文字样式添加选项监听器
        fontStyle.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                updateInputStyle();
                updatahistoryStyle();
            }
        });
        //文字颜色添加选项监听器
        fontForeColor.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                updateInputStyle();
                updatahistoryStyle();
            }
        });
        //文字背景颜色添加选项监听器
        fontBackColor.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                updateInputStyle();
                updatahistoryStyle();
            }
        });

        //文件标签添加监听器
        file.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showFileOpenDialog(mainpanel,inputTextPane);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                file.setBorder(Constants.LIGHT_GRAY_BORDER);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                file.setBorder(BorderFactory.createEmptyBorder());
            }
        });

        //图片添加监听器
        img.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showImgOpenDialog(mainpanel,inputTextPane);
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
                img.setBorder(BorderFactory.createEmptyBorder());
            }
        });

        //发送消息按钮添加监听器
        sendButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("发送了消息");
                super.mouseClicked(e);
                String msg = inputTextPane.getText();     //获取发送消息框的内容
                //历史消息文本框显示发送消息框的内容
                String historyMsg = historyJTextPane.getText();   //先获取接收框的消息
                historyJTextPane.setText(historyMsg + user.getNickname() + ":" + msg +"\n");  //设置接收框的内容"\n"换行

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                sendButton.setBorder(Constants.LIGHT_GRAY_BORDER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                sendButton.setBorder(BorderFactory.createEmptyBorder());
            }
        });


        //视频聊天按钮添加监听器
        video.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("客户端开启视频");
                super.mouseClicked(e);
                //启动视频线程
                VideoChatUIClient videoChatUIServer = new VideoChatUIClient();
                new Thread(videoChatUIServer).start();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                sendButton.setBorder(Constants.LIGHT_GRAY_BORDER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                sendButton.setBorder(BorderFactory.createEmptyBorder());
            }
        });



        quitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ChatUI.this.dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                quitButton.setBorder(Constants.LIGHT_GRAY_BORDER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                quitButton.setBorder(BorderFactory.createEmptyBorder());
            }
        });

    }

    //发送框插入表情
    public void insertIcon(ImageIcon icon) {
        inputTextPane.insertIcon(icon);
    }


    //更新发送框的字体样式
    private void updateInputStyle() {
        //获取发送框的文字样式
        StyledDocument doc = inputTextPane.getStyledDocument();

        //设置文字样式的属性
        //doc的getFontSet
        doc.setParagraphAttributes(0, doc.getLength(), getFontSet(null), false);
    }

    //更新历史消息文本框的字体样式
    private void updatahistoryStyle(){
        //获取历史消息文本框的文字样式
        StyledDocument doc = historyJTextPane.getStyledDocument();
        //设置文字样式的属性
        doc.setParagraphAttributes(0, doc.getLength(), getFontSet(null), false);
    }

    private SimpleAttributeSet getFontSet(Color color) {    //传入的字体集为空
        SimpleAttributeSet set = new SimpleAttributeSet();  //初始化简单属性集，返回值给doc
        if (null != color) {// 固定头信息
            StyleConstants.setBold(set, false);
            StyleConstants.setItalic(set, false);
            StyleConstants.setFontSize(set, 15);
            StyleConstants.setFontFamily(set, "宋体");
            StyleConstants.setForeground(set, color);
            return set;
        }

        // 字体名称
        StyleConstants.setFontFamily(set, (String)fontName.getSelectedItem());//样式常量（传入简单属性集）设置字体系列
        // 字号
        StyleConstants.setFontSize(set, Integer.valueOf((String)fontSize.getSelectedItem()));//样式常量（传入简单属性集）设置字体大小

        // 获取字体样式下拉框中选择的样式索引
        int styleIndex = fontStyle.getSelectedIndex();
        if (styleIndex == 0) {// 常规
            StyleConstants.setBold(set, false);   //样式常量（传入简单属性集）设置粗体
            StyleConstants.setItalic(set, false); //设置斜体
        }
        if (styleIndex == 1) {// 斜体
            StyleConstants.setBold(set, false);
            StyleConstants.setItalic(set, true);
        }
        if (styleIndex == 2) {// 粗体
            StyleConstants.setBold(set, true);
            StyleConstants.setItalic(set, false);
        }
        if (styleIndex == 3) {// 粗斜体
            StyleConstants.setBold(set, true);
            StyleConstants.setItalic(set, true);
        }
        //获取字体颜色下拉框中选择的字体颜色索引
        int foreIndex = fontForeColor.getSelectedIndex();
        if (foreIndex == 0) {// 黑色
            StyleConstants.setForeground(set, Color.BLACK);  //样式常量（传入简单属性集）设置前景为黑色
        }
        if (foreIndex == 1) {// 橙色
            StyleConstants.setForeground(set, Color.ORANGE);
        }
        if (foreIndex == 2) {// 黄色
            StyleConstants.setForeground(set, Color.YELLOW);
        }
        if (foreIndex == 3) {// 绿色
            StyleConstants.setForeground(set, Color.GREEN);
        }
        //获取字体背景颜色下拉框中选择的背景颜色索引
        int backIndex = fontBackColor.getSelectedIndex();
        if (backIndex == 0) {// 白色
            StyleConstants.setBackground(set, Color.WHITE);  //样式常量（传入简单属性集）设置背景为白色
        }
        if (backIndex == 1) {// 灰色
            StyleConstants.setBackground(set, new Color(200, 200, 200));
        }
        if (backIndex == 2) {// 淡红
            StyleConstants.setBackground(set, new Color(255, 200, 200));
        }
        if (backIndex == 3) {// 淡蓝
//            System.out.println("选择了蓝色");
            StyleConstants.setBackground(set, new Color(200, 200, 255));
        }
        if (backIndex == 4) {// 淡黄
            StyleConstants.setBackground(set, new Color(255, 255, 200));
        }
        if (backIndex == 5) {// 淡绿
            StyleConstants.setBackground(set, new Color(200, 255, 200));
        }
        return set;
    }



    public void showFileOpenDialog(Component parent, JTextPane msgTextArea){

        //创建一个文件选择器
        JFileChooser filechooser = new JFileChooser();
        //设置默认显示的文件夹为当前文件夹
        filechooser.setCurrentDirectory(new File("./"));

        //设置文件的选择模式，文件和文件夹皆可选
        filechooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        //设置是否允许多选
        filechooser.setMultiSelectionEnabled(true);

        //添加可用的文件过滤器，第一个参数是描述，第二个参数是需要过滤的文件扩展名
//      filechooser.addChoosableFileFilter(new FileNameExtensionFilter("zip","zip","rar"));
//      filechooser.addChoosableFileFilter(new FileNameExtensionFilter("image","jpg","jpeg","png","gif"));
        //设置默认的使用的文件过滤器
//      filechooser.setFileFilter(new FileNameExtensionFilter("txt","txt"));


        //打开文件选择框（线程将被阻塞，直到文件选择框被关闭）
        int result = filechooser.showOpenDialog(parent);  //在父框上打开文件选择框
        if(result == JFileChooser.APPROVE_OPTION) {//如果点击了“确定”
            //获取选择的文件路径
            File file = filechooser.getSelectedFile();
            System.out.println("file" + file);
            String msg = inputTextPane.getText();     //获取接收框的内容
            //接收框显示所有的内容
            inputTextPane.setText(msg + file.getAbsolutePath() + "\n");         //设置接收框的内容"\n"换行
        }

    }

    public void showImgOpenDialog(Component parent, JTextPane msgTextArea){
        //创建一个文件选择器
        JFileChooser filechooser = new JFileChooser();
        //设置默认显示的文件夹为当前文件夹
        filechooser.setCurrentDirectory(new File("./"));

        //设置文件的选择模式，文件和文件夹皆可选
        filechooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        //设置是否允许多选
        filechooser.setMultiSelectionEnabled(true);

        //添加可用的文件过滤器，第一个参数是描述，第二个参数是需要过滤的文件扩展名
//      filechooser.addChoosableFileFilter(new FileNameExtensionFilter("zip","zip","rar"));
        //设置默认的使用的文件过滤器
        filechooser.setFileFilter(new FileNameExtensionFilter("image","jpg","jpeg","png","gif"));


        //打开文件选择框（线程将被阻塞，直到文件选择框被关闭）
        int result = filechooser.showOpenDialog(parent);  //在父框上打开文件选择框
        if(result == JFileChooser.APPROVE_OPTION) {//如果点击了“确定”
            //获取选择的文件路径
            File file = filechooser.getSelectedFile();
            System.out.println("file" + file);
            String msg = inputTextPane.getText();     //获取接收框的内容
            //接收框显示所有的内容
            inputTextPane.setText(msg + file.getAbsolutePath() + "\n");         //设置接收框的内容"\n"换行
        }

    }







    public void paint(Graphics g){
        super.paint(g);
    }
}
