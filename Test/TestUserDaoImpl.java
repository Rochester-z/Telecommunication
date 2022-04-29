import dao.UserDao;
import dao.impl.UserdaoImpl;
import org.junit.Test;


import java.sql.SQLException;

public class TestUserDaoImpl {
    @Test
    public void testFindAll() throws SQLException {
        UserDao dao = new UserdaoImpl();
//        dao.findAll();
    }


    @Test
    public void testInsert() throws SQLException {
        UserDao dao = new UserdaoImpl();
//        dao.insert("aobama", "911");
    }

    @Test
    public void testDelete(){
        UserDao dao = new UserdaoImpl();
//        dao.delete(2);
    }

    @Test
    public void testLogin(){
        UserDao dao = new UserdaoImpl();
        dao.login("188435564","zq97planned");
//        dao.login("188435564","zq97planned' OR '1=1");

        //statement安全问题
        //1.statement执行其实是拼接sql语句,先拼接sql语句,然后再一起执行
        //前面先凭借sql语句,如果变量里面带有了数据库的关键字,那么一并认为是关键字,不认为是普通的字符串
        //SELECT * FROM t_user WHERE username = 'admin' AND PASSWORD = '123122109' OR '1=1'
        //SELECT * FROM t_user WHERE username = 'admin' AND PASSWORD = '10088' OR '1=1'
    }


    @Test
    public void testUpdate(){
        UserDao dao = new UserdaoImpl();
//        dao.update(1,"zhangsan");
    }
}
