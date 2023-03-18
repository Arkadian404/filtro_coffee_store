package com.data.filtro.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "luong")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maluong")
    private int id;

    @Column(name = "vaitro")
    private String role;

    @Column(name = "hinhthuc")
    private String term;

    @Column(name = "luongtheogio")
    private int wage;
}
