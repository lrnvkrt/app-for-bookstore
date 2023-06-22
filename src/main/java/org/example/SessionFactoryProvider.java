package org.example;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;

/*
Класс, предоставляющий фабрики сессий hibernate
 */
public class SessionFactoryProvider {
    //Поле, хранящее фабрику сессий
    private static final SessionFactory sessionFactory = createSessionFactory();

    private static SessionFactory createSessionFactory(){
        try {
            return new Configuration().configure(
                    new File("src\\main\\resources\\hibernate.cfg.xml")
            ).buildSessionFactory();
        } catch (Throwable e) {
            System.out.println("Initialization failed" + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    //Получение фабрики сессий
    public static SessionFactory getSessionFactory() {return  sessionFactory;}

    //Закрытие фабрики сессий
    public static void shutdown() {getSessionFactory().close();}
}
