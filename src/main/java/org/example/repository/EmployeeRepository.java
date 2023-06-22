package org.example.repository;

import org.example.SessionFactoryProvider;
import org.example.entity.Employee;
import org.example.entity.EmployeeStatus;
import org.example.entity.EmploymentContract;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class EmployeeRepository {
    private SessionFactory sessionFactory;

    public EmployeeRepository() {
        this.sessionFactory = SessionFactoryProvider.getSessionFactory();
    }

    public List<Employee> getEmployees(){
        try (Session session = sessionFactory.openSession()){
            return session
                    .createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
        }
    }

    public List<EmployeeStatus> getStatuses(){
        try (Session session = sessionFactory.openSession()){
            return  session
                    .createQuery("SELECT s FROM EmployeeStatus s", EmployeeStatus.class).getResultList();
        }
    }

    public void saveEmployee(Employee employee) {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.merge(employee);
            transaction.commit();
        }
    }

    public EmployeeStatus getStatusWorking() {
        try (Session session = sessionFactory.openSession()){
            return session.createQuery("SELECT s FROM EmployeeStatus s where idEmployeeStatus = 0", EmployeeStatus.class).getSingleResult();
        }
    }

    public void saveEmploymentContract(EmploymentContract employmentContract) {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.persist(employmentContract);
            transaction.commit();
        }
    }

    public void deleteEmployee(Employee selectedEmployee) {
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.remove(selectedEmployee);
            transaction.commit();
        }
    }
}
