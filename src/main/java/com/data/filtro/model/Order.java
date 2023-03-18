package com.data.filtro.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "dathang")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "madathang")
    private int id;

    @Column(name = "makh")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "makh", referencedColumnName = "makh")
    private User user;

    @Column(name = "ngaydathang")
    private Date orderDate;

    @Column(name = "diachi")
    private String address;

    @Column(name = "tong")
    private int total;
}
