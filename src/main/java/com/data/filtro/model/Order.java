package com.data.filtro.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Entity
@Table(name = "dathang")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "madathang")
    private Integer id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "makh", referencedColumnName = "makh")
    private User user;

    @Column(name = "ngaydathang")
    private Date orderDate;

    @Column(name = "diachi")
    private String address;

    @Column(name = "zip")
    private Integer zip;

    @Column(name = "thanhpho")
    private String city;


    @Column(name = "SDT")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "tong")
    private Integer total;


    @Column(name = "tinhtrang")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phuongthucthanhtoan", referencedColumnName = "id")
    private PaymentMethod paymentMethod;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "orderId")
    private List<OrderDetail> orderDetails;

}
