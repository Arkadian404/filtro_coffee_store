package com.data.filtro.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="sanpham")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="masp")
    private Integer id;

    @Column(name="tensanpham")
    private String productName;

    @Column(name="soluong")
    private int quantity;

    @Column(name="giatien")
    private int price;

    @Column(name="mota")
    private String description;

    @Column(name = "anh")
    private String image;

    @Column(name="tinhtrang")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "madanhmuc", referencedColumnName = "madanhmuc")
    private Category category;

}
