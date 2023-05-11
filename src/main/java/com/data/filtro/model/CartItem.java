package com.data.filtro.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "giohang_chitiet")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magiohang", referencedColumnName = "magiohang")
    @JsonManagedReference
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masp", referencedColumnName = "masp")
    private Product product;

    @Column(name = "soluong")
    private Integer quantity;

    @Column(name = "giatien")
    private Integer price;

    @Column(name = "tong")
    private Integer total;

    @Column(name = "thoigianmua")
    private Date purchasedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magiohangtam", referencedColumnName = "id")
    private GuestCart guestCart;
}
