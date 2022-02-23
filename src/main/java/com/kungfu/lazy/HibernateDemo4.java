package com.kungfu.lazy;


import com.kungfu.domain.Customer;
import com.kungfu.domain.Order;
import com.kungfu.utils.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

/**
 * 批量策略HQL查询语句
 */
public class HibernateDemo4 {


    //查询所有客户
    //bath-size :决定一次加载几个对象得集合数据。sql语句使用in加载多个用户得订单
    //默认：与我关联的数据时，在使用时才会加载。
    public void fun1() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        List<Customer> list = session.createQuery("from com.huhu.domain.Customer").list();
        //根据客户查询所有订单数
        for(Customer c:list){
            System.out.println(c.getOrders().size());
        }
        //--------------------------------------
        session.getTransaction().commit();
        session.close();
    }


    public static void main(String[] args) {
        HibernateDemo4 d = new HibernateDemo4();
        d.fun1();
    }
}
