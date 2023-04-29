package com.data.filtro.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Entity
@Table(name = "taikhoan")
@Data
@EqualsAndHashCode(exclude = "user")
@Component
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matk")
    private Integer id;

    @Column(name = "taikhoan")
    private String accountName;

    @Column(name = "matkhau")
    private String password;

    @Column(name = "ngaytao")
    private Date createdDate;

    @Column(name = "mavaitro")
    private Integer roleNumber;

    @Column(name = "tinhtrang")
    private Integer status;

    @Column(name = "password_reset_token")
    private String passwordResetToken;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonBackReference
    private User user;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonBackReference
    private Staff staff;

}
