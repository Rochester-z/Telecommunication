package severe.thread;

import severe.ui.chatui.VideoChatUIServer;

public class VideoThreadServer implements Runnable{
    @Override
    public void run() {
        //视频为一个任务，启动一个线程来执行任务
        VideoChatUIServer ui = new VideoChatUIServer();
        ui.InitUI();
    }
}
