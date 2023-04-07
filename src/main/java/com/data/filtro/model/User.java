package com.data.filtro.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Entity
@Table(name = "khachhang")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "makh")
    private Integer id;

    @Column(name = "hoten")
    private String name;

    @Column(name = "ngaysinh")
    private Date dob;

    @Column(name = "gioitinh")
    private String sex;

    @Column(name = "email")
    private String email;

    @Column(name = "sdt")
    private String phoneNumber;

    @Column(name = "tinhtrang")
    private Integer status;


    @OneToOne
    @JoinColumn(name = "matk", referencedColumnName = "matk")
    private Account account;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;
}
