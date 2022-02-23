package com.kungfu.lazy;

import com.kungfu.domain.Customer;
import com.kungfu.domain.Order;
import com.kungfu.utils.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

/**
 * 加载策略：多对一
 */
public class HibernateDemo3 {


    //fetch:select
    //lazy:false
    //默认：与我关联的数据时，在使用时才会加载。
    public void fun1() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        Order order = (Order) session.get(Order.class, 17);
        System.out.println(order.getCustomer().getName());
        //--------------------------------------
        session.getTransaction().commit();
        session.close();
    }

    //fetch:select
    //lazy:proxy
    //Customer类加载策略：lazy ：false
    //默认：与我关联的数据时，在使用时才会加载。
    public void fun2() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        Order order = (Order) session.get(Order.class, 17);

        //--------------------------------------
        session.getTransaction().commit();
        session.close();
    }

    //fetch:select
    //lazy:proxy
    //Customer类加载策略：lazy ：true
    //默认：与我关联的数据时，在使用时才会加载。
    public void fun3() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        Order order = (Order) session.get(Order.class, 19);
        System.out.println(order.getCustomer().getName());
        //--------------------------------------
        session.getTransaction().commit();
        session.close();
    }

    //fetch:join
    //lazy:proxy|false
    //Customer类加载策略：lazy ：true
    //默认：与我关联的数据时，在使用时才会加载。
    public void fun4() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        Order order = (Order) session.get(Order.class, 19);
        //--------------------------------------
        session.getTransaction().commit();
        session.close();
    }

    public static void main(String[] args) {
        HibernateDemo3 d = new HibernateDemo3();
        d.fun4();
    }
}
