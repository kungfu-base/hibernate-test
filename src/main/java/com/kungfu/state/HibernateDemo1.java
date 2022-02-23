package com.kungfu.state;

import com.kungfu.domain.User;
import com.kungfu.utils.HibernateUtils;
import org.hibernate.Session;

/**
 * 对象的三种状态
 */
public class HibernateDemo1 {


    //三种状态
    public void fun1() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        User u = new User();          //status : 瞬时状态
        u.setName("tom");           //status : 瞬时状态
        u.setPassword("1234");      //status : 瞬时状态

        //保存
        session.save(u);            //status : 持久状态
        //调用save方法时,数据库有没有对应记录
        //没有记录，但最终会被同步到数据库中，仍时持久状态
        //--------------------------------------
        session.getTransaction().commit();      //status : 持久状态
        session.close();            //status : 游离状态
    }


    // 三种状态的转换  瞬时=》持久
    public void fun2() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        User u = new User();           //status : 瞬时状态
        u.setName("he");             //status : 瞬时状态
        u.setPassword("123456");     //status : 瞬时状态

        session.save(u);             //status : 持久状态
        //--------------------------------------
        session.getTransaction().commit();      //status : 持久状态
        //事务提交时会把持久对象保存到数据库
        //increment=>hibernate 自动查询最大id，然后生成主键
        //assigned =>自动生成主键
        session.close();
    }

    // 瞬时=》游离 瞬时：没有关联没有id 游离：没有关联有id(与数据库对应的id)
    public void fun3() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------//status : 持久状态
        User u = new User();
        u.setId(2);         //此时为游离状态
        //--------------------------------------
        session.getTransaction().commit();      //status : 持久状态
        //事务提交时会把持久对象保存到数据库
        session.close();
    }

    //持久=》瞬时1 持久：有关联，有id，瞬时：无关联无id
    public void fun4() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------//status : 持久状态
        //通过get方法，得到持久状态对象
        User u = (User) session.get(User.class, 2); //持久状态
        //--------------------------------------
        session.getTransaction().commit();
        //事务提交时会把持久对象保存到数据库
        session.close();  //游离状态
        u.setId(null);
    }

    //持久=》瞬时1  持久：有关联，有id，瞬时：无关联无id
    public void fun5() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------//status : 持久状态
        //通过get方法，得到持久状态对象
        User u = (User) session.get(User.class, 1); //持久状态
        session.evict(u);       //将User对象和session的关联移除
        u.setId(null);          //瞬时对象
        //--------------------------------------
        session.getTransaction().commit();
        session.save(u);
        //事务提交时会把持久对象保存到数据库
        session.close();  //游离状态
    }

    //  持久=》游离   持久：有关联，有id
    public void fun6() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------//status : 持久状态
        //通过get方法，得到持久状态对象
        User u = (User) session.get(User.class, 1); //持久状态
        session.evict(u);       //将User对象和session的关联移除
        //--------------------------------------
        session.getTransaction().commit(); //游离
        session.close();  //游离状态
    }

    // 游离=》瞬时  瞬时：无关联无id
    public void fun7() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------//status : 持久状态
        //通过get方法，得到持久状态对象
        User u = (User) session.get(User.class, 1); //持久状态

        session.evict(u);       //瞬时
        u.setId(null);          //瞬时
        //--------------------------------------
        session.getTransaction().commit(); //瞬时
        session.close();  //瞬时状态
    }

    //    游离=》持久  是否与session关联
    public void fun8() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------//status : 持久状态
        //通过get方法，得到持久状态对象
        User u = (User) session.get(User.class, 1); //持久状态

        session.evict(u);       //游离
        session.update(u);      //持久
        //--------------------------------------
        session.getTransaction().commit(); //持久  打印updata语句
        session.close();  //瞬时状态
    }

    public void fun9() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------//
        //通过get方法，得到持久状态对象
        User u = (User) session.get(User.class, 1);

        u.setName("nenen");
        //--------------------------------------
        session.getTransaction().commit();
        session.close();  //瞬时状态
    }

    public static void main(String[] args) {
        HibernateDemo1 d = new HibernateDemo1();
        d.fun1();
        //d.fun9();
    }
}
