package com.linewell.utils;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class HibernateUtil {
    private static SessionFactory factory;

    static {
        try {
            //创建Configuration对象，读取hibernate.cfg.xml文件，完成初始化
            Configuration cfg = new Configuration().configure();
            factory = cfg.buildSessionFactory();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static SessionFactory getSessionFactory() {
        return factory;
    }

    public static Session getSession() {
        return factory.openSession();
    }

    //增加
    public static boolean save(Object obj){
        Transaction tran=null;
        Session session = getSession();
        boolean result=false;
        try{
            tran=session.beginTransaction();
            session.save(obj);
            tran.commit();
            result=true;
        }
        catch (Exception e){
            if(tran!=null){
                //事物回滚
                tran.rollback();
            }
        }
        finally{
            if(session!=null){
                //关闭session
                session.close();
            }
        }
        return result;
    }

    //删除
    public static boolean delete(Object obj){
        Transaction tran=null;
        Session session = getSession();
        boolean result=false;
        try{
            tran=session.beginTransaction();
            session.delete(obj);
            tran.commit();
            result=true;
        }
        catch (Exception e){
            if(tran!=null){
                //事物回滚
                tran.rollback();
            }
        }
        finally{
            if(session!=null){
                //关闭session
                session.close();
            }
        }
        return result;
    }

    //修改
    public static boolean update(Object obj){
        Transaction tran=null;
        Session session = getSession();
        boolean result=false;
        try{
            tran=session.beginTransaction();
            session.update(obj);
            tran.commit();
            result=true;
        }
        catch (Exception e){
            if(tran!=null){
                //事物回滚
                tran.rollback();
            }
        }
        finally{
            if(session!=null){
                //关闭session
                session.close();
            }
        }
        return result;
    }

    //关闭session
    public static void closeSession(Session session) {
        if (session != null) {
            if (session.isOpen()) {
                session.close();
            }
        }
    }
}
