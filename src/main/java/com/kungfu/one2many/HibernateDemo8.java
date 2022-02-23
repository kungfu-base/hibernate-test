package com.kungfu.one2many;

import com.kungfu.domain.Customer;
import com.kungfu.utils.HibernateUtils;
import org.hibernate.Session;

public class HibernateDemo8 {

    //增删
    //cascade ==>all-delete-orphan =>save-update,delete,delete-orphan 无外键引用则被删除
    public void fun1() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        Customer customer = new Customer();
        customer.setName("huhu");

        session.save(customer);
        session.getTransaction().commit();
        session.close();
    }

    public void fun2() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        Customer customer = (Customer) session.get(Customer.class, 18);
        session.delete(customer);

        //--------------------------------------
        session.getTransaction().commit();
        session.close();
    }

    public static void main(String[] args) {
        HibernateDemo8 d = new HibernateDemo8();
        //d.fun1();
        d.fun2();
        System.out.println("-----------------------------------------------");
    }

}
