package entity;

public class GroupOwner {
    /**go_id主键*/
    String go_id;
    /**用户id */
    String own_id;
    /**分组id*/
    String g_id;

    public GroupOwner(){}

    public GroupOwner(String go_id, String own_id, String g_id) {
        this.go_id = go_id;
        this.own_id = own_id;
        this.g_id = g_id;
    }

    public String getGo_id() {
        return go_id;
    }

    public void setGo_id(String go_id) {
        this.go_id = go_id;
    }

    public String getOwn_id() {
        return own_id;
    }

    public void setOwn_id(String own_id) {
        this.own_id = own_id;
    }

    public String getG_id() {
        return g_id;
    }

    public void setG_id(String g_id) {
        this.g_id = g_id;
    }

    @Override
    public String toString() {
        return "GroupOwner{" +
                "go_id='" + go_id + '\'' +
                ", own_id='" + own_id + '\'' +
                ", g_id='" + g_id + '\'' +
                '}';
    }
}
