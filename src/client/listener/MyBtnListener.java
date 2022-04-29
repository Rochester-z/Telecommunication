package client.listener;


import client.ui.mainui.MainWindow;
import dao.UserDao;
import dao.impl.UserdaoImpl;
import entity.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyBtnListener implements ActionListener {
    public JTextField nameInput1 ;
    public JTextField nameInput2 ;
    public JFrame ui;


    //jdk1.8包在rt压缩包里
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String btnstring = actionEvent.getActionCommand();
        String name = nameInput1.getText();
        String password = nameInput2.getText();
        UserDao userdao = new UserdaoImpl();      //数据库操作类
        if (btnstring.equals("登录")) {
            //单个进程死循环了
            //当账号正确,且密码正确时,进行下一步,打开对话框
            User user = userdao.login(name,password);   //账号密码对应的用户
            if(!user.equals(null)) {          //当用户非空
                System.out.println("name"+user.getUsername());
                System.out.println("password"+user.getPassword());
                MainWindow mainWindow = new MainWindow(user, userdao);
                //隐藏登录界面
                ui.setVisible(false);
            }
            else{
                System.out.println("密码错误");
            }
        }
        else if (btnstring.equals("退出")) {
            System.out.println("用户点击了" + btnstring);

        }
        else if(btnstring.equals("注册账号")){
//            Scanner in = new Scanner(System.in);  //获取,输入的内容
//            String username = in.nextLine();
//            System.out.println("username" + username);
//            String word = in.nextLine();
//            dao.insert(name,password);

        }
    }
}
