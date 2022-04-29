package client.ui.common;

import client.ui.chatui.ChatUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Emoticon extends JDialog implements FocusListener, WindowFocusListener {
	public static void main(String[] args) {
		Emoticon emoticon = new Emoticon();
		emoticon.initGUI();
	}
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JScrollPane jScrollPane1;

	private JLabel label[] = new JLabel[105];   //存储表情
	private String marks[] = new String[label.length];  //表情的代号
	
	private ChatUI chatUI;
	public boolean click = false;  //判断是否打开了表情框
	public Emoticon(){}

	public Emoticon(ChatUI chatUI) {
		this.chatUI = chatUI;
		initGUI();
	}
	
	private void initGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 350, 300);
//		setUndecorated(true);
		setAlwaysOnTop(true);

		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		//面板设置网格布局12行0列
		contentPane.setLayout(new GridLayout(12, 0));
		
		jScrollPane1 = new JScrollPane();
		jScrollPane1.setViewportView(contentPane);
		getContentPane().add(jScrollPane1, BorderLayout.CENTER);  //在对话框中添加滚动框，滚动框中内嵌面板，面板中叠放表情标签
		jScrollPane1.getVerticalScrollBar().setUI(new MyScrollBarUI());
		// 屏蔽横向滚动条
		jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		String fileName = "";
		for (int i = 0; i < label.length; i++) {
			fileName = "src/resource/image/face/"+i+".gif";
//			System.out.println("url地址"+Emoticon.class.getClassLoader().getresource(fileName));
			label[i] = new JLabel(new ChatPic(Emoticon.class.getClassLoader().getResource(fileName), i), SwingConstants.CENTER);
			label[i].setBorder(BorderFactory.createLineBorder(new Color(225, 225, 225), 1));
			label[i].setToolTipText(":)" + i);
			marks[i] = ":)" + i;

			label[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					JLabel temp = (JLabel) e.getSource();
					temp.setBorder(BorderFactory.createLineBorder(Color.BLUE));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					JLabel temp = (JLabel) e.getSource();
					temp.setBorder(BorderFactory.createLineBorder(new Color(225, 225, 225), 1));
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					System.out.println("点击了表情");
					JLabel temp = (JLabel) e.getSource();
					ChatPic pic = (ChatPic) temp.getIcon();
					chatUI.insertIcon(pic);
					dispose();
					chatUI.image = null;
				}
			});

			contentPane.add(label[i]);
		}
			setVisible(true);
		}



	@Override
	public void focusGained(FocusEvent e) {
		System.out.println("获取焦点了");
		// TODO Auto-generated method stub
		
	}


	@Override
	public void focusLost(FocusEvent e) {
		System.out.println("失去焦点了");
		setVisible(false);
		dispose();
		chatUI.image = null;
	}

	@Override
	public void windowGainedFocus(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowLostFocus(WindowEvent e) {
		System.out.println("失去窗体焦点了");
		setVisible(false);
		dispose();  //关闭窗体资源，并释放内存后image为什么不为空
		chatUI.image = null;
	}

}
