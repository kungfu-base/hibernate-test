package com.kungfu.api;

import com.kungfu.domain.User;
import com.kungfu.utils.HibernateUtils;
import org.hibernate.Session;

/**
 * api
 */
public class HibernateDemo4 {


    //clear 清空一级缓存
    public void fun1() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        User user1 = (User) session.get(User.class, 5);
        //清空缓存
        session.clear();
        User user2 = (User) session.get(User.class, 5);
        //--------------------------------------
        session.getTransaction().commit();
        session.close();
    }

    //evict 将缓存中的对象移除
    public void fun3() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        User user1 = (User) session.get(User.class, 5);
        //清空缓存
        session.evict(user1);
        User user2 = (User) session.get(User.class, 5);
        //--------------------------------------
        session.getTransaction().commit();
        session.close();
    }

    //强制刷新缓存中的对象
    public void fun2() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        User user1 = (User) session.get(User.class, 5);
        //將缓存中的对象与数据库中的数据同步。在发送sql查询
        session.refresh(user1);
        //--------------------------------------
        session.getTransaction().commit();
        session.close();
    }

    //flush  对比快照提交缓存中的对象
    public void fun4() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        User user1 = (User) session.get(User.class, 5);
        user1.setName("666");
        //立刻同步（提交）session缓存中的对象到数据库
        session.flush();
        //將缓存中的对象与数据库中的数据同步。在发送sql查询
        //-------------------------------------
        session.getTransaction().commit();
        session.close();
    }

    //5.update 与 saveOrUpdate方法  在主键自增
    public void fun5() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        User user = new User();
        user.setId(5);
        user.setName("666");
        user.setPassword("1234568495");

        //可以同時完成保存或更新
        //如果主鍵為null ：insert  反之 ： update
        session.saveOrUpdate(user);
        //-------------------------------------
        session.getTransaction().commit();
        session.close();
    }

    //6.在使用hibernate时，要注意避免出现，两个相同的id对象，放入缓存中
    //主键必设置
    public void fun6() {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        //--------------------------------------
        User u = (User) session.get(User.class, 5);
        session.evict(u);

        User u2 = (User) session.get(User.class, 5);
        session.update(u);//将u重新变为持久化
        //-------------------------------------
        session.getTransaction().commit();
        session.close();
    }

    public static void main(String[] args) {
        HibernateDemo4 d = new HibernateDemo4();
        d.fun6();
        d.fun5();
        d.fun4();
        d.fun3();
        d.fun2();
        d.fun1();
        System.out.println("-----------------------------------------------");
    }

    //criteria =>也会把查询结果放入缓存
}
