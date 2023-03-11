package com.data.filtro.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="luong")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Salary {
    @Id
    @Column(name="maluong")
    private String id;

    @Column(name="vaitro")
    private String role;

    @Column(name="hinhthuc")
    private String term;

    @Column(name="luongtheogio")
    private int wage;
}
