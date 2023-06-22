package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "method_of_payment", schema = "public", catalog = "bookstore")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MethodOfPayment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_method_of_payment")
    private Integer idMethodOfPayment;
    @Basic
    @Column(name = "name")
    private String name;
}
