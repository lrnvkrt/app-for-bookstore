package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "delivery_item", schema = "public", catalog = "bookstore")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeliveryItem {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_delivery_item")
    private Integer idDeliveryItem;
    @Basic
    @Column(name = "purchasing_price")
    private Double purchasingPrice;
    @Basic
    @Column(name = "quantity")
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "id_delivery")
    private Delivery delivery;
    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;
}
