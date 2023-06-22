package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Delivery {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_delivery")
    private Integer idDelivery;
    @Basic
    @Column(name = "date")
    private Date date;
    @ManyToOne
    @JoinColumn(name = "id_store")
    private Store store;
    @OneToMany(mappedBy = "delivery", fetch = FetchType.EAGER)
    private List<DeliveryItem> deliveryItems;
    @ManyToOne
    @JoinColumn(name = "id_supplier")
    private Supplier supplier;
}
