package com.linewell.dao.daoImpl;

import com.linewell.dao.PoliceAffDao;
import com.linewell.domain.PoliceAff;
import com.linewell.utils.HibernateUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("PoliceAffDao")
public class PoliceAffDaoImpl implements PoliceAffDao {


    @Override
    public List<PoliceAff> findAll(Integer code, String pushName, String affMsg, String address,String startTime,String endTime) {
        List<PoliceAff> list = new ArrayList<PoliceAff>();
        Session session = HibernateUtil.getSession();// openSession
        Transaction tx = session.beginTransaction();
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT pa.*,u.name,u.telephone from police_aff pa" +
                    " join user u on u.id = pa.push_id where 1 = 1");
            if(code != null){
                sb.append(" and pa.code = '"+code+"'");
            }
            if(StringUtils.isNotEmpty(pushName)){
                sb.append(" and u.name = '"+pushName+"'");
            }
            if(StringUtils.isNotEmpty(affMsg)){
                sb.append(" and pa.affMsg = '"+affMsg+"'");
            }
            if(StringUtils.isNotEmpty(address)){
                sb.append(" and pa.aff_address = '"+address+"'");
            }
            if(StringUtils.isNotEmpty(startTime)){
                sb.append(" and DATE_FORMAT(pa.create_time, '%YYYY-%MM-%DD %hh:mm:ss') >= date_format('"+startTime+"', '%Y-%m-%d')");
            }
            if(StringUtils.isNotEmpty(endTime)){
                sb.append(" and DATE_FORMAT(pa.create_time, '%YYYY-%MM-%DD %hh:mm:ss') <= date_format('"+endTime+"', '%Y-%m-%d')");
            }
            String sql = sb.toString();
            Query query = session.createNativeQuery(sql);
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
    public List<PoliceAff> findByPushId(String userId) {
        List<PoliceAff> list = new ArrayList<PoliceAff>();
        Session session = HibernateUtil.getSession();// openSession
        Transaction tx = session.beginTransaction();
        try {
            Query query = null;
            if(StringUtils.isNotEmpty(userId)){
                query = session.createQuery("from PoliceAff WHERE pushId = '"+userId+"' AND code = 1");
            }else{
                query = session.createQuery("from PoliceAff WHERE 0=1 ");
            }
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
    public List<PoliceAff> findByReadOrUnread(Integer code) {
        List<PoliceAff> list = new ArrayList<PoliceAff>();
        Session session = HibernateUtil.getSession();// openSession
        Transaction tx = session.beginTransaction();
        try {
            Query query = session.createQuery("from PoliceAff WHERE code = '"+code+"'");
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
    public PoliceAff findById(String affId) {
        Session session = HibernateUtil.getSession();// openSession
        Transaction tx = session.beginTransaction();
        PoliceAff user = new PoliceAff();
        try {
            Query query = session.createQuery("FROM PoliceAff WHERE id = '"+affId+"'");
            user = (PoliceAff) query.uniqueResult();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            HibernateUtil.closeSession(session);
        }
        return user;
    }
}
