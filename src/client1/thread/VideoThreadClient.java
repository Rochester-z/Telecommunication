package client1.thread;

import client1.ui.VideoChatUIClient;

public class VideoThreadClient implements Runnable{
    @Override
    public void run() {
        //视频为一个任务，启动一个线程来执行任务
        VideoChatUIClient ui = new VideoChatUIClient();
        ui.InitUI();
    }
}
