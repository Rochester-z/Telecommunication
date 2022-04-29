package entity;

/**
 * 用户实体类
 */
public class User {
    /**uid*/
    private String uid;
    /**用户账号*/
    private String username;
    /**用户密码*/
    private String password;
    /**用户昵称*/
    private String nickname;
    /**用户签名*/
    private String signature;
    /**用户头像位置*/
    private String profile;




    /**
     * 无参构造方法
     */
    public User(){}

    /**
     * 有参构造方法
     * @param username
     * @param password
     * @param nickname
     */
    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }


    /**
     * 有参构造方法
     * @param uid
     * @param username
     * @param password
     * @param nickname
     * @param signature
     * @param profile
     */
    public User(String uid, String username, String password, String nickname, String signature, String profile) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.signature = signature;
        this.profile = profile;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", signature='" + signature + '\'' +
                ", profile='" + profile + '\'' +
                '}';
    }
}
