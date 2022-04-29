package client.listener;


import severe.thread.MsgServerThread;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String name = e.getActionCommand();
//        MsgServerThread msgServerThread = new MsgServerThread();
//      msgServerThread.ui = ui;        //将监听器中登录界面传给线程类，来隐藏登录界面
//        msgServerThread.start();
    }
}
