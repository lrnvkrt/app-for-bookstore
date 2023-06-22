package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employment_contract", schema = "public", catalog = "bookstore")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmploymentContract {
    @GeneratedValue(generator = "employment_contract_id_employment_contract_seq")
    @SequenceGenerator(name = "employment_contract_id_employment_contract_seq"
    ,sequenceName = "employment_contract_id_employment_contract_seq", allocationSize = 1)
    @Id
    @Column(name = "id_employment_contract")
    private Integer idEmploymentContract;
    @Basic
    @Column(name = "position")
    private String position;
    @Basic
    @Column(name = "salary")
    private Double salary;
    @Basic
    @Column(name = "hours_of_work")
    private Integer hoursOfWork;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "employmentContract")
    private Employee employee;
}
