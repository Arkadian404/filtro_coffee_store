package com.data.filtro.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Entity
@Table(name = "taikhoan")
@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matk")
    private Integer id;

    @Column(name = "taikhoan")
    private String username;

    @Column(name = "matkhau")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "ngaytao")
    private Date createdDate;

    @Column(name = "mavaitro")
    private int roleNumber;

    @Column(name = "tinhtrang")
    private Integer status;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private User user;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private Staff staff;

}
