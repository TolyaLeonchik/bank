package com.bank.repository;

import com.bank.domain.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BankRepository {

    public final EntityManager entityManager;

    public BankRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Users> getAllUsers() {
        Query query = entityManager.createQuery("FROM bank_users");
        return query.getResultList();
    }

    public Users getUser(int id) {
        return entityManager.find(Users.class, id);
    }

    public void save(Users user) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }

    public void update(Users user) {
        entityManager.getTransaction().begin();
        entityManager.merge(user);
        entityManager.getTransaction().commit();
    }

    public void delete(Integer id) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Users.class, id));
        entityManager.getTransaction().commit();
    }

    public void transfer(Users moneyFrom, Users moneyTo) {
        entityManager.getTransaction().begin();
        entityManager.merge(moneyFrom);
        entityManager.merge(moneyTo);
        entityManager.getTransaction().commit();
    }

    public Users findUserCard(String searchNumberCard) {
        TypedQuery<Users> query = entityManager.createQuery("select u from bank_users u where u.numberCard = :search", Users.class);
        query.setParameter("search", searchNumberCard);
        List<Users> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
}
