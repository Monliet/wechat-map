package com.linewell.dao.daoImpl;

import com.linewell.dao.UserDao;
import com.linewell.domain.User;
import com.linewell.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("userDao")
@Transactional
public class UserDaoImpl implements UserDao {


    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<User>();
        Session session = HibernateUtil.getSession();// openSession
        Transaction tx = session.beginTransaction();
        try {
            Query query = session.createQuery("from User");
            list = query.getResultList();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            HibernateUtil.closeSession(session);
        }
        return list;
    }

    @Override
    public User findByUserNameAndTelephone(String userName,String telephone) {
        Session session = HibernateUtil.getSession();// openSession
        Transaction tx = session.beginTransaction();
        User user = new User();
        try {
            Query query = session.createQuery("FROM User WHERE telephone = '"+telephone+"'" +
                    " AND name = '"+userName+"'");
            user = (User) query.uniqueResult();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            HibernateUtil.closeSession(session);
        }
        return user;
    }

    @Override
    public User findByTelephone(String telephone) {
        Session session = HibernateUtil.getSession();// openSession
        Transaction tx = session.beginTransaction();
        User user = new User();
        try {
            Query query = session.createQuery("FROM User WHERE telephone = '"+telephone+"'");
            user = (User) query.uniqueResult();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            HibernateUtil.closeSession(session);
        }
        return user;
    }

    @Override
    public User findById(String userId) {
        Session session = HibernateUtil.getSession();// openSession
        Transaction tx = session.beginTransaction();
        User user = new User();
        try {
            Query query = session.createQuery("FROM User WHERE id = '"+userId+"'");
            user = (User) query.uniqueResult();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            HibernateUtil.closeSession(session);
        }
        return user;
    }
}
