package com.kungfu.one2many;

import com.kungfu.domain.Customer;
import com.kungfu.domain.Order;
import com.kungfu.utils.HibernateUtils;
import org.hibernate.Session;

import java.util.Iterator;

public class HibernateDemo7 {

    //增删
    //cascade ==>delete-orphan =>孤儿删除 无外键引用则被删除
    public void fun1() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        Customer customer = (Customer) session.get(Customer.class, 17);

        Iterator<Order> iterator = customer.getOrders().iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();

        }
        //--------------------------------------
        session.getTransaction().commit();
        session.close();
    }

    public static void main(String[] args) {
        HibernateDemo7 d = new HibernateDemo7();
        d.fun1();
        //d.fun2();
        System.out.println("-----------------------------------------------");
    }

}
