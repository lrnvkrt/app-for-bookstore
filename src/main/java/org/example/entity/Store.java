package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Store {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_store")
    private Integer idStore;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToMany(mappedBy = "stores", fetch = FetchType.EAGER)
    private Set<Employee> employees;
    @OneToMany(mappedBy = "store")
    List<Delivery> deliveries;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
        return Objects.equals(idStore, store.idStore) && Objects.equals(address, store.address) && Objects.equals(phoneNumber, store.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idStore, address, phoneNumber);
    }
}
