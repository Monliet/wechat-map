package com.linewell.dao.daoImpl;

import com.linewell.dao.UserAreaDao;
import com.linewell.domain.UserArea;
import com.linewell.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("UserAreaDao")
@Transactional
public class UserAreaDaoImpl implements UserAreaDao {
    @Override
    public List<UserArea> findByCode() {
        List<UserArea> list = new ArrayList<UserArea>();
        Session session = HibernateUtil.getSession();// openSession
        Transaction tx = session.beginTransaction();
        try {
            Query query = session.createQuery("from UserArea where code = 0");
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
    public UserArea findByUserId(String userId) {
        UserArea userArea = new UserArea();
        Session session = HibernateUtil.getSession();// openSession
        Transaction tx = session.beginTransaction();
        try {
            Query query = session.createQuery("from UserArea where userId = '"+userId+"'");
            userArea = (UserArea) query.uniqueResult();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            HibernateUtil.closeSession(session);
        }
        return userArea;
    }

    @Override
    public List<UserArea> findByAddressAndCode(String address) {
        List<UserArea> list = new ArrayList<UserArea>();
        Session session = HibernateUtil.getSession();// openSession
        Transaction tx = session.beginTransaction();
        try {
            Query query = session.createNativeQuery("SELECT u.id,u.name,u.telephone as phone,ua.address from user_area ua" +
                    " join user u on u.id = ua.user_id" +
                    " where ua.code = 0 AND ua.address like '%"+address+"%'");
            list = query.getResultList();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            HibernateUtil.closeSession(session);
        }
        return list;
    }
}
