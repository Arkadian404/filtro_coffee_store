package com.data.filtro.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "dathang")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "madathang")
    private Integer id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "makh", referencedColumnName = "makh")
    @JsonManagedReference
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "phuongthucthanhtoan", referencedColumnName = "id")
    @JsonManagedReference
    private PaymentMethod paymentMethod;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "orderId")
    //@JsonIgnore
    @JsonManagedReference
    private List<OrderDetail> orderDetails;


    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Shipper shipper;

}
