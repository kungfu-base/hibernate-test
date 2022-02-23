package com.kungfu.lazy;


import com.kungfu.domain.Customer;
import com.kungfu.domain.Order;
import com.kungfu.utils.HibernateUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class HibernateDemo2 {


    //关联级别懒加载
    //默认：与我关联的数据时，在使用时才会加载。
    public void fun1() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        System.out.println("customer----------sql语句发送");
        Customer load = (Customer) session.get(Customer.class, 1);
        System.out.println("order--------sql语句发送");
        for (Order o : load.getOrders()) {
            System.out.println(o.getName());
        }
        //--------------------------------------
        session.getTransaction().commit();
        session.close();
    }

    //关联级别懒加载
    //lazy：false
    public void fun2() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        Customer load = (Customer) session.get(Customer.class, 1);

        System.out.println("所有的--------sql语句发送");
        for (Order o : load.getOrders()) {
            System.out.println(o.getName());
        }
        //--------------------------------------
        session.getTransaction().commit();
        session.close();
    }


    //关联级别懒加载
    //lazy:false
    //fetch :join
    public void fun3() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        Customer load = (Customer) session.get(Customer.class, 1);

        System.out.println("左外连接查询--------sql语句发送");
        for (Order o : load.getOrders()) {
            System.out.println(o.getName());
        }
        //--------------------------------------
        session.getTransaction().commit();
        session.close();
    }

    //关联级别懒加载
    //lazy:false
    //fetch :subselect
    public void fun4() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        List<Customer> list = session.createQuery("from com.huhu.domain.Customer").list();
        for (Customer c : list) {
            System.out.println(c.getName() + c.getOrders().size());
        }
        //--------------------------------------
        session.getTransaction().commit();
        session.close();
    }


    //关联级别懒加载
    //lazy:true
    //fetch :subselect
    public void fun5() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        List<Customer> list = session.createQuery("from com.huhu.domain.Customer").list();
        System.out.println("使用order时才去数据库查询");
        for (Customer c : list) {
            System.out.println(c.getName() + c.getOrders().size());
        }
        //--------------------------------------
        session.getTransaction().commit();
        session.close();
    }

    //关联级别懒加载
    //lazy:true
    //fetch :select
    public void fun6() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        System.out.println("customer--------sql语句发送");
        Customer load = (Customer) session.get(Customer.class, 17);

        System.out.println("order--------sql语句发送");
        for (Order o : load.getOrders()) {
            System.out.println(o.getName());
        }
        //--------------------------------------
        session.getTransaction().commit();
        session.close();
    }

    //关联级别懒加载
    //lazy:false
    //fetch :select
    public void fun7() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        Customer load = (Customer) session.get(Customer.class, 17);

        System.out.println("所有的--------sql语句发送");
        for (Order o : load.getOrders()) {
            System.out.println(o.getName());
        }
        //--------------------------------------
        session.getTransaction().commit();
        session.close();
    }


    //关联级别懒加载
    //lazy:extra
    //fetch :select
    public void fun8() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        Customer load = (Customer) session.get(Customer.class, 17);

        System.out.println("-------查询数量count()------");
        System.out.println(load.getOrders().size());
        System.out.println("----------查询customer--------");
        for (Order o : load.getOrders()) {
            System.out.println(o.getName());
        }
        //--------------------------------------
        session.getTransaction().commit();
        session.close();
    }

    //关联级别懒加载
    //lazy:extra
    //fetch :subselect
    public void fun9() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        List<Customer> list = session.createQuery("from com.huhu.domain.Customer").list();
        for (Customer c : list) {
            System.out.println(c.getName() + "------------" + c.getOrders().size());
        }
        for (Customer cs : list) {
            for (Order o : cs.getOrders()) {
                System.out.println(cs.getName() + "------------" + o.getName());
            }
        }
        //--------------------------------------
        session.getTransaction().commit();
        session.close();
    }

    public static void main(String[] args) {
        HibernateDemo2 d = new HibernateDemo2();
        d.fun1();
    }
}
