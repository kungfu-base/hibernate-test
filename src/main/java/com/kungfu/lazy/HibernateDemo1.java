package com.kungfu.lazy;


import com.kungfu.domain.Customer;
import com.kungfu.utils.HibernateUtils;
import org.hibernate.Session;

public class HibernateDemo1 {


    //类级别懒加载
    //load方法
    //class lazy属性
    //默认值：true load获得时，会返回代理对象，不查询数据库，使用时才查询
    public void fun1() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        Customer load = (Customer) session.load(Customer.class, 1);
        System.out.println("----------------sql语句未发送");
        System.out.println(load.getName());
        //--------------------------------------
        session.getTransaction().commit();
        session.close();
    }


    //类级别懒加载
    //load方法
    //class lazy属性
    //默认值：false load方法执行就会发送sql语句，和get方法一致
    public void fun2() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        Customer load = (Customer) session.load(Customer.class, 1);
        System.out.println("----------------sql语句发送");
        System.out.println(load.getName());
        //--------------------------------------
        session.getTransaction().commit();
        session.close();
    }


    public static void main(String[] args) {
        HibernateDemo1 d = new HibernateDemo1();
        d.fun2();
    }
}
