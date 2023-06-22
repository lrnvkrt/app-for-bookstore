package org.example.repository;

import org.example.SessionFactoryProvider;
import org.example.entity.Genre;
import org.example.entity.Product;
import org.example.entity.Supplier;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class ProductRepository {
    private SessionFactory sessionFactory;

    public ProductRepository() {
        sessionFactory = SessionFactoryProvider.getSessionFactory();
    }

    public List<Product> getProducts(){
        try(Session session = sessionFactory.openSession()){
            return session.createQuery("SELECT p FROM Product p", Product.class).getResultList();
        }
    }

    public List<Genre> getGenres(){
        try(Session session = sessionFactory.openSession()){
            return session.createQuery("SELECT g FROM Genre g", Genre.class).getResultList();
        }
    }

    public List<Supplier> getSuppliers(){
        try (Session session = sessionFactory.openSession()){
            return session.createQuery("SELECT s FROm Supplier s", Supplier.class).getResultList();
        }
    }
}
