package maintest;

import com.linewell.dao.UserDao;
import com.linewell.domain.User;
import com.linewell.domain.UserArea;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class entitytest {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Test
    public void test(){
        //创建配置对象(读取配置文档)
        Configuration config = new Configuration().configure();
        //创建会话工厂对象
        sessionFactory = config.buildSessionFactory();
        //会话对象
        session = sessionFactory.openSession();
        //开启事务
        transaction = session.beginTransaction();
        User user = new User();
        user.setId("10");
        user.setName("jack");
        user.setTelephone("17000000004");
        user.setArea("上海");
        session.save(user);
        //提交事务
        transaction.commit();
        //关闭事务
        session.close();
        sessionFactory.close();
    }

    @Test
    public void test1(){
        Configuration config = new Configuration().configure();
        //创建会话工厂对象
        sessionFactory = config.buildSessionFactory();
        //会话对象
        session = sessionFactory.openSession();
        //开启事务
        transaction = session.beginTransaction();
        Query query = session.createQuery("FROM User WHERE telephone = '17000000001'");
        User user = (User) query.uniqueResult();
        transaction.commit();
        System.out.println(user);
    }

    @Test
    public void test2(){
        Configuration config = new Configuration().configure();
        //创建会话工厂对象
        sessionFactory = config.buildSessionFactory();
        //会话对象
        session = sessionFactory.openSession();
        //开启事务
        transaction = session.beginTransaction();
        UserArea userArea = new UserArea();
        userArea.setId("2222");
        userArea.setUserId("222");
        userArea.setAreaCode("200");
        session.save(userArea);
        //提交事务
        transaction.commit();
        //关闭事务
        session.close();
        sessionFactory.close();
    }
}
