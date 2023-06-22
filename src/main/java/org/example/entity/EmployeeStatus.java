package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "employee_status", schema = "public", catalog = "bookstore")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeStatus {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_employee_status")
    private Integer idEmployeeStatus;
    @Basic
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "employeeStatus")
    List<Employee> employeeList;
}
