package com.kungfu.cache;

import com.kungfu.domain.User;
import com.kungfu.utils.HibernateUtils;
import org.hibernate.Session;

/**
 * 对象的三种状态
 */
public class HibernateDemo2 {


    //session缓存的存在
    public void fun1() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        //发送select語句，从数据库取出记录，并封装成对象
        //持久话对象==>存到缓存
        User u1 = (User) session.get(User.class, 1);
        System.out.println("放入缓存");
        //再次查找是再缓存中查找，不答应sql语句
        User u2 = (User) session.get(User.class, 1);
        User u3 = (User) session.get(User.class, 1);
        System.out.println("使用的是否是同一个session:");
        System.out.println(u1 == u2);
        System.out.println(u1 == u3);
        //--------------------------------------
        session.getTransaction().commit();
        session.close();
    }

    public void fun2() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        User u1 = (User) session.get(User.class, 1);
        session.update(u1);
        //--------------------------------------
        session.getTransaction().commit();
        session.close();
    }

    public void fun3() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        User u1 = new User();
        u1.setId(4);
        u1.setName("hehe");
        u1.setPassword("huhu");

        session.update(u1);
        //--------------------------------------
        session.getTransaction().commit();
        session.close();
    }

    public static void main(String[] args) {
        HibernateDemo2 d = new HibernateDemo2();
        d.fun1();
        System.out.println("-----------------------------------------------");
        //d.fun3();
    }
}
