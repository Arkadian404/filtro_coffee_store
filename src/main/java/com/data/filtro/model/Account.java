package com.data.filtro.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "taikhoan")
@Data
@EqualsAndHashCode(exclude = "user")
@AllArgsConstructor
@NoArgsConstructor
public class Account implements Serializable {
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mavaitro", referencedColumnName = "mavaitro")
    @JsonManagedReference
    private Role role;

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

    @OneToMany(mappedBy = "account")
    private List<Shipper> shippers;


}
