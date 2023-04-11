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
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mahoadon", referencedColumnName = "mahoadon")
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masp")
    private Product product;

    @Column(name = "soluong")
    private Integer quantity;

    @Column(name = "giatien")
    private Integer price;

    @Column(name = "tong")
    private Integer total;
}
