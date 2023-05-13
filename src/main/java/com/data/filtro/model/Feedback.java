package com.data.filtro.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "phanhoi")
@AllArgsConstructor
@NoArgsConstructor
public class Feedback implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "masp", referencedColumnName = "masp")
    @JsonManagedReference
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "makh", referencedColumnName = "makh")
    @JsonManagedReference
    private User user;

    @Column(name = "noidung")
    private String content;

    @Column(name = "ngayph")
    private Date date;
}
