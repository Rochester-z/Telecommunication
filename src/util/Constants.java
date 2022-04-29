package util;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Constants {

	// 服务器IP，一般来说就是本机
	public static String SERVER_IP = "127.0.0.1";
	// 服务器port
	public static int SERVER_PORT = 5555;

	// 消息类型
	/** 普通 */
	public static String GENRAL_MSG = "0";
	/** 抖动 */
	public static String SHAKE_MSG = "1";
	/** 回文 */
	public static String PALIND_MSG = "2";
	/** 图片 */
//	感觉用不上
//	public static String PICTURE_MSG = "3";
	/** 登录 */
	public static String LOGIN_MSG = "4";
	/** 退出 */
	public static String EXIT_MSG = "5";
	/** 注册 */
	public static String REGISTER_MSG = "6";
	/** 用户信息(修改、查看) */
	public static String INFO_MSG = "7";
	/** 请求添加好友 */
	public static String REQUEST_ADD_MSG = "8";
	/** 回应添加好友 */
	public static String ECHO_ADD_MSG = "9";
	/** 删除分组（user） */
	public static String DELETE_USER_CATE_MSG = "10";
	/** 删除成员（user） */
	public static String DELETE_USER_MEMBER_MSG = "11";
	/** 添加分组（user） */
	public static String ADD_USER_CATE_MSG = "12";
	/** 修改分组（user） */
	public static String EDIT_USER_CATE_MSG = "13";

	public static String USER = "user";
	public static String GROUP = "group";
	public static String SUCCESS = "success";
	public static String FAILURE = "failure";
	public static String YES = "yes";
	public static String NO = "no";

	/** 空格代码 */
	public static String SPACE = "\u0008";  //转义字符
	/** 换行代码 */
	public static String NEWLINE = "\n";
	/** 左斜杠 */
	public static String LEFT_SLASH = "/";

	public static String SEARCH_TXT = "搜索";
	public static String DEFAULT_CATE = "我的好友";
	public static String NONAME_CATE = "未命名";
	
	// 微软雅黑
	public static Font BASIC_FONT = new Font("微软雅黑", Font.PLAIN, 12);
	public static Font BASIC_FONT2 = new Font("微软雅黑", Font.TYPE1_FONT, 12);
	// 楷体
	public static Font DIALOG_FONT = new Font("楷体", Font.PLAIN, 16);
	public static Font DIALOG_FONT2 = new Font("楷体", Font.PLAIN, 24);
	
	public static Border GRAY_BORDER = BorderFactory.createLineBorder(Color.GRAY);
	public static Border ORANGE_BORDER = BorderFactory.createLineBorder(Color.ORANGE);
	public static Border LIGHT_GRAY_BORDER = BorderFactory.createLineBorder(Color.LIGHT_GRAY);

	public static int NO_OPTION = 1;
	public static int YES_OPTION = 0;
}
