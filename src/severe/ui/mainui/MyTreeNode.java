package severe.ui.mainui;

import entity.User;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;


public class MyTreeNode extends DefaultMutableTreeNode {

    private static final long serialVersionUID = 1007068847268622569L;

    /**
     * 图片
     */
    private Icon icon;

    /**
     * 文字
     */
    private String name;

    /**
     * 签名
     */
    private String sign;

    /**
     * 是否选中
     */
    private boolean select;

    private JPanel groupPanel;
    private JPanel buddyPanel;

    private JLabel iconLabel;
    private JLabel nameLabel;
    private JLabel signLabel;

    private User frienduser;  //当前节点代表的好友

    public User getFrienduser() {
        return frienduser;
    }

    public void setFrienduser(User frienduser) {
        this.frienduser = frienduser;
    }

    public MyTreeNode() {
    }

    /**
     * 初始化分组节点
     *
     * @param name 名称
     */
    public MyTreeNode(Icon icon, String name) {
        this.icon = icon;
        this.name = name;
        // 初始化UI
        initCateGUI();
    }

    /**
     * 初始化好友节点
     *
     * @param icon 头像
     * @param nick 昵称
     * @param sign 签名
     */
    public MyTreeNode(Icon icon, String nick, String sign) {
        this.icon = icon;
        this.name = nick;
        this.sign = sign;
        // 初始化UI
        initNodeGUI();
    }

    /**
     * 只传入节点代表的好友，参入为好友的属性
     * @param frienduser
     */
    public MyTreeNode(User frienduser) {
        this.frienduser = frienduser;
        this.icon = new ImageIcon(frienduser.getProfile());
        this.name = frienduser.getNickname();
        this.sign = frienduser.getSignature();

        // 初始化UI
        initNodeGUI();
    }


    /**
     * 自定义分组UI
     */
    private void initCateGUI() {
        groupPanel = new JPanel();
        groupPanel.setLayout(null);
//		groupPanel.setOpaque(false);
        // 这里大家注意，当我们写好UI之后可能会发现他的颜色不太对，
        // 这时候千万不要用上面那句，不然当我们想再次改变其颜色的时候，就生效不了
        // 红绿蓝分别为255的这个颜色趋近于透明，我们可以用它来代替setOpaque
//		groupPanel.setBackground(new Color(255,255,255));
        // 突然发现置成null也可以
        groupPanel.setBackground(null);
        groupPanel.setPreferredSize(new Dimension(300, 25));
//		groupPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        iconLabel = new JLabel(icon);
        iconLabel.setBounds(6, 5, 20, 16);
        groupPanel.add(iconLabel);

        nameLabel = new JLabel(name);
        nameLabel.setBounds(23, 0, 132, 28);
        groupPanel.add(nameLabel);
    }

    /**
     * 自定义好友UI
     */
    private void initNodeGUI() {
        buddyPanel = new JPanel();
        buddyPanel.setLayout(null);
        buddyPanel.setBackground(null);
        buddyPanel.setPreferredSize(new Dimension(300, 50));
//		buddyPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        iconLabel = new JLabel(icon);
        iconLabel.setBounds(8, 4, 42, 42);
        buddyPanel.add(iconLabel);

        nameLabel = new JLabel(name);
        nameLabel.setBounds(59, 5, 132, 19);
        buddyPanel.add(nameLabel);

        signLabel = new JLabel(sign);
        signLabel.setBounds(59, 28, 132, 17);
        buddyPanel.add(signLabel);
    }

    /**
     * 将自定义UI返回给渲染器	<br/>
     * 供渲染器调用，返回的必须是一个Component
     *
     * @return
     */
    public Component getGroupView() {
        return groupPanel;
    }

    /**
     * 将自定义UI返回给渲染器	<br/>
     * 供渲染器调用，返回的必须是一个Component
     *
     * @return
     */
    public Component getBuddyView() {
        return buddyPanel;
    }

    public JLabel getIconLabel() {
        return iconLabel;
    }

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public JLabel getSignLabel() {
        return signLabel;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

}