package com.bank.repository;

import com.bank.domain.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BankRepository {

    public final SessionFactory sessionFactory;

    public BankRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Users> getAllUsers() {
        Session session = sessionFactory.openSession();
        Query<Users> query = session.createQuery("FROM bank_users", Users.class);
        List<Users> result = query.getResultList();
        session.close();
        return result;
    }

    public Users getUser(int id) {
        Session session = sessionFactory.openSession();
        Users user = session.find(Users.class, id);
        session.close();
        return user;
    }

    public void save(Users user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(user);
        session.getTransaction().commit();
        session.close();
    }

    public void update(Users user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.merge(user);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(Users user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.remove(user);
        session.getTransaction().commit();
        session.close();
    }

    public void transfer(Users moneyFrom, Users moneyTo) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.merge(moneyFrom);
        session.merge(moneyTo);
        session.getTransaction().commit();
        session.close();
    }

    public Users findUserCard(String searchNumberCard) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query<Users> query = session.createQuery("SELECT u FROM bank_users u " +
                "WHERE u.numberCard = : search", Users.class);
        query.setParameter("search", searchNumberCard);
        List<Users> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
}
