package com.data.filtro.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "giohang")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "makh")
    @OneToOne
    @JoinColumn(name = "makh", referencedColumnName = "makh")
    private User user;

    @Column(name = "thoigianmua")
    private Date purchasedDate;

    @Column(name = "trangthai")
    private int status;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> cartItemList;
}
