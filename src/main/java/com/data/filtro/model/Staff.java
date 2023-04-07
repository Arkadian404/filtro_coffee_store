package com.data.filtro.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "nhanvien")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manv")
    private Integer id;

    @Column(name = "hoten")
    private String name;

    @Column(name = "ngaysinh")
    private Date dob;

    @Column(name = "gioitinh")
    private String sex;

    @OneToOne
    @JoinColumn(name = "maluong", referencedColumnName = "maluong")
    private Salary salary;

    @OneToOne
    @JoinColumn(name = "matk", referencedColumnName = "matk")
    private Account account;

    @Column(name = "tinhtrang")
    private Integer status;
}
