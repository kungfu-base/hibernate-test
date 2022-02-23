package com.kungfu.cache;

import com.kungfu.domain.User;
import com.kungfu.utils.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

/**
 * 对象的三种状态的细节问题
 */
public class HibernateDemo3 {


    //保存对象时使用的save方法保存对象使用persist发发
    public void fun1() {
        Session session = HibernateUtils.openSession();
        //session.beginTransaction();
        //--------------------------------------
        User user = new User();
        user.setName("lala");
        user.setPassword("1111");

        //session.save(user);
        session.persist(user);
        //--------------------------------------
        //session.getTransaction().commit();
        session.close();
    }


    //1.HQL会使用一级查询，但也发送sql语句
    public void fun2() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        List<User> list1 = session.createQuery("from  User ").list();
        List<User> list2 = session.createQuery("from  User ").list();
        List<User> list3 = session.createQuery("from  User ").list();

        //--------------------------------------
        session.getTransaction().commit();
        session.close();
    }

    //2.HQL语句批量查询解过是否进入缓存 查询结果会放入缓存中
    public void fun3() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        List<User> list1 = session.createQuery("from  User ").list();
        User u = (User) session.get(User.class, 3);
        //--------------------------------------
        session.getTransaction().commit();
        session.close();
    }

    //3.SQL查詢结果会不会进入结果会不会放入缓存中:如果把查询结果封装到对象时会放入缓存
    public void fun4() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        List<User> list1 = session.createSQLQuery("select * from t_user ").addEntity(User.class).list();
        User u = (User) session.get(User.class, 1);
        System.out.println(u);
        //--------------------------------------
        session.getTransaction().commit();
        session.close();
    }

    //3.SQL查詢结果会不会进入结果会不会放入缓存中：纯面向数据库的查询不会放入缓存
    public void fun5() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        List list1 = session.createSQLQuery("select * from t_user ").list();
        User u = (User) session.get(User.class, 1);
        System.out.println(u);
        //--------------------------------------
        session.getTransaction().commit();
        session.close();
    }

    public static void main(String[] args) {
        HibernateDemo3 d = new HibernateDemo3();
        d.fun1();
        System.out.println("-----------------------------------------------");
    }

    //criteria =>也会把查询结果放入缓存
}
