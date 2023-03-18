package com.data.filtro.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "giohang_chitiet")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magiohang", referencedColumnName = "magiohang")
    private Cart cart;

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
