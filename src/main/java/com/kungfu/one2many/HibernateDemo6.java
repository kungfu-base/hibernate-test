package com.kungfu.one2many;

import com.kungfu.domain.Customer;
import com.kungfu.domain.Order;
import com.kungfu.utils.HibernateUtils;
import org.hibernate.Session;

public class HibernateDemo6 {

    //增删
    //在保存customer时，自动保存order为保存的
    //cascade ==>save-udpate
    public void fun1() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        Customer customer = new Customer();
        customer.setName("huhu");

        Order order1 = new Order();
        order1.setName("肥皂");

        Order order2 = new Order();
        order2.setName("蜡烛");

        customer.getOrders().add(order1);
        customer.getOrders().add(order2);
        //维护关系

        session.save(customer);
        //--------------------------------------
        session.getTransaction().commit();
        session.close();
    }

    //级联修改
    public void fun2() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------

        Customer customer = (Customer) session.get(Customer.class, 6);

        for (Order f : customer.getOrders()) {
            f.setName("哇哈哈");
        }

        //--------------------------------------
        session.getTransaction().commit();
        session.close();
    }

    //cascade ：delete  a和b都被删除
    //inverse:false  6条语句
    //inverse:true  5条语句 不维护外键
    public void fun3() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------

        Customer customer = (Customer) session.get(Customer.class, 16);

        session.delete(customer);
        //--------------------------------------
        session.getTransaction().commit();
        session.close();
    }

    //delete注意：a和b都设cascade都为delete ：删除任一方都全部删除
    public void fun4() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------

        Order order = (Order) session.get(Order.class, 17);
        //Customer customer=(Customer)session.get(Customer.class,16);
        //找到关联的customer  select customer 删除所有order 删除customer
        session.delete(order);

        //--------------------------------------
        session.getTransaction().commit();
        session.close();
    }

    public static void main(String[] args) {
        HibernateDemo6 d = new HibernateDemo6();
        //d.fun1();
        //d.fun2();
        d.fun3();
        System.out.println("-----------------------------------------------");
    }

}
