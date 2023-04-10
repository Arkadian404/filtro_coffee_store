package com.data.filtro.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "phuongthuc_thanhtoan")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ten")
    private String name;

    @Column(name = "mota")
    private String description;

    @OneToMany(fetch = FetchType.LAZY)
    List<Order> orders;
}
