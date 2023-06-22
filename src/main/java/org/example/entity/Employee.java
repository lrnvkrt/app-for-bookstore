package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee {
    //Используется для генерируемых полей
    @GeneratedValue(generator = "employee_id_employee_seq")
    @SequenceGenerator(name = "employee_id_employee_seq"
    ,sequenceName = "employee_id_employee_seq", allocationSize = 1)
    @Id
    @Column(name = "id_employee")
    private Integer idEmployee;
    @Basic
    @Column(name = "medical_book_number")
    private String medicalBookNumber;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "surname")
    private String surname;
    @Basic
    @Column(name = "patronymic")
    private String patronymic;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_employment_contract")
    private EmploymentContract employmentContract;
    @ManyToOne
    @JoinColumn(name = "id_employee_status")
    private EmployeeStatus employeeStatus;
    @Basic
    @Column(name = "employee_id_card_number")
    private String employeeCardNumber;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "employee_store",
            joinColumns = {@JoinColumn(name = "id_employee")},
            inverseJoinColumns = {@JoinColumn(name = "id_store")}
    )
    private Set<Store> stores;
}
