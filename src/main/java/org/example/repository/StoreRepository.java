package org.example.repository;

import org.example.SessionFactoryProvider;
import org.example.entity.Store;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class StoreRepository {
    private SessionFactory sessionFactory;

    public StoreRepository() {
        sessionFactory = SessionFactoryProvider.getSessionFactory();
    }

    public List<Store> getStores(){
        try(Session session = sessionFactory.openSession()){
            return session.createQuery("SELECT s FROM Store s", Store.class).getResultList();
        }
    }
}
