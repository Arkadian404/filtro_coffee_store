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
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "madathang", referencedColumnName = "madathang")
    private Order orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masp", referencedColumnName = "masp")
    private Product product;

    @Column(name = "soluong")
    private Integer quantity;

    @Column(name = "giatien")
    private Integer price;

    @Column(name = "tong")
    private Integer total;
}
