package com.data.filtro.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.checkerframework.checker.formatter.qual.Format;
import org.checkerframework.checker.units.qual.C;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "shipper")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shipper implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matk", referencedColumnName = "matk")
    private Account account;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "madathang", referencedColumnName = "madathang")
    private Order order;

    @Column(name = "ngayvanchuyen")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date shippedDate;


    @Column(name = "ngaygiaohang")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deliveredDate;

    @Column(name = "tinhtrang")
    private Integer status;
}
