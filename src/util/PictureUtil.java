package util;

import javax.swing.*;

public class PictureUtil {

	public static ImageIcon getPicture(String name) {
		ImageIcon icon = new ImageIcon(PictureUtil.class.getClassLoader()
				.getResource("src/resource/image/" + name));
		return icon;
	}

}
