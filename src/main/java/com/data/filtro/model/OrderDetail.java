package com.data.filtro.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dathang_chitiet")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "madathang", referencedColumnName = "madathang")
    @Column(name = "madathang")
    private Order orderId;

    @Column(name = "masp")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masp", referencedColumnName = "masp")
    private Product product;

    @Column(name = "soluong")
    private int quantity;

    @Column(name = "giatien")
    private int price;

    @Column(name = "tong")
    private int total;
}
