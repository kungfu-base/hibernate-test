package com.kungfu.one2many;

import com.kungfu.domain.Customer;
import com.kungfu.domain.Order;
import com.kungfu.utils.HibernateUtils;
import org.hibernate.Session;

import java.util.Set;

public class HibernateDemo5 {

    //1.测试1对多关系中，保持操作
    // 前3条：insert  =>维护外键
    // 后两条：update =>维护外键
    // 解决 => 单纯的关系由其中一方来维护，另一方不需要来维护
    //外键维护的放弃，只能由非外键来所在对象来放弃

    //incerse 属性：true 放弃外键维护
    public void fun1() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        Customer customer=new Customer();
        customer.setName("huhu");

        Order order1=new Order();
        order1.setName("肥皂");

        Order order2=new Order();
        order2.setName("蜡烛");

        //维护关系
        //customer.getOrders().add(order1);
        //customer.getOrders().add(order2);

        order1.setCustomer(customer);
        order2.setCustomer(customer);

        session.save(customer);
        session.save(order1);
        session.save(order2);
        //--------------------------------------
        session.getTransaction().commit();
        session.close();
    }

    //多表关系的删除
    //删除用户时会先移除customer中引用的cid
    // 结论：维护一方对象，会自动维护另一方关系对象
    //如果将 customer 的inverse 属性：true  =>Customer会导致，order引用了无效的id，违反了外键约束

    public void fun2() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        Customer customer=(Customer)session.get(Customer.class,3);

        Set<Order> set = customer.getOrders();
        for(Order s:set){
            s.setCustomer(null);//设置订单不属于任何用户customer
        }

        session.delete(customer);
        //--------------------------------------
        session.getTransaction().commit();
        session.close();
    }

    public static void main(String[] args) {
        HibernateDemo5 d = new HibernateDemo5();
       // d.fun1();
        d.fun2();
        System.out.println("-----------------------------------------------");
    }

}
