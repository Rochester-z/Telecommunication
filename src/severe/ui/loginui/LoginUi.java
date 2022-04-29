package severe.ui.loginui;
/**- 创建一个自己的类
        - 定义这个具备的属性（尺寸 标题...）和方法(组成窗体结构  初始化窗体的方法 )
        - 初始化方法
        - 1、创建一个窗体对象
        - 2、设置这个窗体对象的属性值
        - 3、设置窗体对象的相关功能 （关闭选项，可视化 布局规则）
        - 4、创建小组件对象 （按钮 输入框 ...）
        - 5、设置小组件对象的属性和功能
        - 6、将小组件对象加载到窗体对象上
        - 创建主函数 实力化这个类的对象 调用初始化界面的*/


import severe.listener.MyBtnListener;

import javax.swing.*;
import java.awt.*;

public class LoginUi extends JFrame{
    public static void main(String[] args){
        //  - 创建主函数 实力化这个类的对象 调用初始化界面的方法*/
        LoginUi loginui = new LoginUi();
        loginui.initUI();
    }


    //初始化窗体的方法
    public void initUI(){
        //qq整合美颜相机
        //1.调用javax.swing界面开发工具包中的JFrame窗体类创建一个Jf窗体对象
        //2.设置窗体的标题，尺寸大小
        this.setTitle("wemeet");
        this.setSize(350,650);
        this.setLocationRelativeTo(null);  //null设置为中心位置
        //3.设置窗体的功能，如关1闭，可视化，布局
        //关闭选项
        //EXIT_ON_CLOSE为JFrame类的属性值
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//EXIT_ON_CLOSE为JFrame类的属性值

        Bjpanel bj = new Bjpanel();
        Container container = this.getContentPane();   //获取窗体第三层容器
        container.add(bj);    //给窗体第三层容器添加重写的面板对象
        bj.setOpaque(true);
        //给窗体添加了重写的面板后窗体不可设置为流式布局,面板可以设置
        //流式布局管理器对象，用Flowlayout流式布局管理器类创建对象，jf.setlayout函数参数为对象；面板默认，非窗体

        //4.创建小组件对象，为什么不需要调用包路径
        JButton btn1 = new JButton();//按钮类创建按钮对象
        JButton btn2 = new JButton();
        JButton btn3 = new JButton();
        JButton btn4 = new JButton();
        JButton btn5 = new JButton();
        JTextField nameInput1 = new JTextField();//文字类创建输入框对象
        JPasswordField nameInput2 = new JPasswordField();  //密码输入框
        // 复选框
        JCheckBox nameInput3 = new JCheckBox();
        JCheckBox nameInput4 = new JCheckBox();
        JLabel namejla1 = new JLabel();//标签类创建名字+对象，添加文字
        namejla1.setText("账号：");
        JLabel namejla2 = new JLabel();
        namejla2.setText("密码：");

        JLabel jlabel = new JLabel();   //空标签,布局按钮的位置
        Dimension dimension = new Dimension(350,410);
        jlabel.setPreferredSize(dimension);


        //ImageIcon类创建img对象，参数为图片路径+格式 JLabel类定义imajla对象 img对象传入参数
//        ImageIcon img = new ImageIcon("C:\\Users\\zhuqi\\Desktop\\login.png");
//        ImageIcon img = new ImageIcon("imageqq/login.png");
//        JLabel imgjla = new JLabel(img);

        // 复选框
        // JCheckBox


        //设置小组件的属性和功能
        btn1.setText("自动登录");
        btn2.setText("登录");
//      btn3.setText("退出");
        btn4.setText("记住密码");
        btn5.setText("注册账号");


        //namejla.setSize()代码不适用于小组件对象
        //小组件设置尺寸大小需要先创建尺寸对象
        Dimension inputDim1 = new Dimension(240,30);
        //inputDim
        nameInput1.setPreferredSize(inputDim1);
        Dimension inputDim2 = new Dimension(240,30);
        nameInput2.setPreferredSize(inputDim2);
        Dimension btn2Dim = new Dimension(220,40);
        btn2.setPreferredSize(btn2Dim);

        //给按钮设置背景颜色
        btn1.setBackground(Color.cyan);
        btn2.setBackground(Color.cyan);
        btn3.setBackground(Color.cyan);
        btn4.setBackground(Color.cyan);
        btn5.setBackground(Color.cyan);


        // 6、将小组件对象加载到窗体对象上
        bj.add(jlabel);
        bj.add(namejla1);
        bj.add(nameInput1);
        bj.add(namejla2);
        bj.add(nameInput2);
        bj.add(btn1);
        bj.add(nameInput3);
        bj.add(btn4);
        bj.add(nameInput4);
        bj.add(btn5);
        bj.add(btn2);


        this.setVisible(true);

        MyBtnListener my = new MyBtnListener();
        my.ui = this;         //将登录界面传给监听器来隐藏界面，大学生是学生
        btn2.addActionListener(my);
        btn3.addActionListener(my);
        btn5.addActionListener(my);
        nameInput1.addActionListener(my);
        nameInput2.addActionListener(my);
        my.nameInput1 = nameInput1;
        my.nameInput2 = nameInput2;
    }

    public void paint(Graphics g){
        super.paint(g);
    }

}
