package com.data.filtro.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "nhanvien")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Staff implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manv")
    private Integer id;

    @Column(name = "hoten")
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "ngaysinh")
    private Date dob;

    @Column(name = "gioitinh")
    private String sex;

    @Column(name = "sdt")
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "matk", referencedColumnName = "matk")
    @JsonManagedReference
    private Account account;

    @Column(name = "tinhtrang")
    private Integer status;
}
