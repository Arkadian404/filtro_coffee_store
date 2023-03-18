package com.data.filtro.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hoadon_chitiet")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    @Column(name = "mahoadon")
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    @JoinColumn(name = "masp")
    private Product product;

    @Column(name = "soluong")
    private int quantity;

    @Column(name = "giatien")
    private int price;

    @Column(name = "tong")
    private int total;
}
