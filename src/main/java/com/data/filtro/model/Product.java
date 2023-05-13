package com.data.filtro.model;


import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sanpham")
@Data
@ToString(exclude = {"flavor", "category", "cartItemList"})
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "masp")
    private Integer id;

    @Column(name = "tensanpham")
    private String productName;

    @Column(name = "soluong")
    private Integer quantity;

    @Column(name = "daban")
    private Integer sold;

    @Column(name = "giatien")
    private Integer price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mahuongvi", referencedColumnName = "mahuongvi")
    @JsonManagedReference
    private Flavor flavor;

    @Column(name = "mota")
    private String description;

    @Column(name = "anh")
    private String image;

    @Column(name = "ngaytao")
    private Date createdDate;

    @Column(name = "tinhtrang")
    private Integer status;

    @Column(name = "giamgia")
    private Integer discount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "madanhmuc", referencedColumnName = "madanhmuc")
    //@JsonManagedReference
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CartItem> cartItemList;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<InvoiceDetail> invoiceDetails;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Feedback> feedbacks;
}
