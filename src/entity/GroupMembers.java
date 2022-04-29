package entity;

public class GroupMembers {
    /**gm_id*/
    String gm_id;
    /**分组id，可在Group表查询分组类型*/
    String g_id;
    /**所属者id*/
    String own_id;
    /**成员id*/
    String mem_id;


    /**
     * 无参构造方法
     */
    public GroupMembers(){}

    /**
     * 有参构造方法
     * @param gm_id
     * @param g_id
     * @param own_id
     * @param mem_id
     */
    public GroupMembers(String gm_id, String g_id, String own_id, String mem_id) {
        this.gm_id = gm_id;
        this.g_id = g_id;
        this.own_id = own_id;
        this.mem_id = mem_id;
    }

    public String getGm_id() {
        return gm_id;
    }

    public void setGm_id(String gm_id) {
        this.gm_id = gm_id;
    }

    public String getG_id() {
        return g_id;
    }

    public void setG_id(String g_id) {
        this.g_id = g_id;
    }

    public String getOwn_id() {
        return own_id;
    }

    public void setOwn_id(String own_id) {
        this.own_id = own_id;
    }

    public String getMem_id() {
        return mem_id;
    }

    public void setMem_id(String mem_id) {
        this.mem_id = mem_id;
    }

    @Override
    public String toString() {
        return "GroupMembers{" +
                "gm_id='" + gm_id + '\'' +
                ", g_id='" + g_id + '\'' +
                ", own_id='" + own_id + '\'' +
                ", mem_id='" + mem_id + '\'' +
                '}';
    }
}
