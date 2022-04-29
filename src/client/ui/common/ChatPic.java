package client.ui.common;

import javax.swing.*;
import java.net.URL;

public class ChatPic extends ImageIcon {
	
	/** 下标 */
	private int index;
	/** 代号 */
	private int number;
	
	public ChatPic(URL url, int im) {
		super(url);
		this.number = im;
	}

	public ChatPic(URL url, int index, int im) {
		super(url);
		this.number = im;
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

}
