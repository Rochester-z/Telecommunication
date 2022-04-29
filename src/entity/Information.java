package entity;

public class Information {
    /**用户名*/
    private String username;
    /**用户消息*/
    private String news;
    /**用户图片*/
    private String image;
    /**用户文件*/
    private String file;


    /**
     * 无参构造方法
     */
    public Information(){}

    /**
     * 有参构造方法
     * @param username
     * @param news
     * @param image
     * @param file
     */
    public Information(String username, String news, String image, String file) {
        this.username = username;
        this.news = news;
        this.image = image;
        this.file = file;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
