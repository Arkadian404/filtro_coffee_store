package com.data.filtro.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "khachhang")
@Data
@EqualsAndHashCode(exclude = {"cart"})
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "makh")
    private Integer id;

    @Column(name = "hoten")
    private String name;

    @Column(name = "ngaysinh")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    @Column(name = "gioitinh")
    private String sex;

    @Column(name = "diachi")
    private String address;

    @Column(name = "zip")
    private Integer zip;

    @Column(name = "thanhpho")
    private String city;

    @Column(name = "email")
    private String email;

    @Column(name = "sdt")
    private String phoneNumber;

    @Column(name = "tinhtrang")
    private Integer status;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "matk", referencedColumnName = "matk")
    @JsonManagedReference
    private Account account;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Order> orders;
}
