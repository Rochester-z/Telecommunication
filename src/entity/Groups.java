package entity;

public class Groups {
    /**g_id */
    String g_id;
    /**分组名称*/
    String group_name;



    /**
     * 无参构造方法
     */
    public Groups(){}

    public Groups(String g_id, String group_name) {
        this.g_id = g_id;
        this.group_name = group_name;
    }

    public String getG_id() {
        return g_id;
    }

    public void setG_id(String g_id) {
        this.g_id = g_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    @Override
    public String toString() {
        return "Groups{" +
                "g_id='" + g_id + '\'' +
                ", group_name='" + group_name + '\'' +
                '}';
    }
}
