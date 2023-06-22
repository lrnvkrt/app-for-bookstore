package org.example.repository;

import org.example.SessionFactoryProvider;
import org.example.entity.MethodOfPayment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class MethodOfPaymentRepository {
    private SessionFactory sessionFactory;

    public MethodOfPaymentRepository() {
        sessionFactory = SessionFactoryProvider.getSessionFactory();
    }

    public List<MethodOfPayment> getMethods(){
        try (Session session = sessionFactory.openSession()){
            return session.createQuery("SELECT m FROM MethodOfPayment m", MethodOfPayment.class)
                    .getResultList();
        }
    }
}
