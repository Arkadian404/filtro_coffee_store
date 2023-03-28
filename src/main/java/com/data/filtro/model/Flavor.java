package com.data.filtro.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "huongvi")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flavor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mahuongvi")
    private Integer id;

    @Column(name = "tenhuongvi")
    private String flavorName;

    @OneToMany(mappedBy = "flavor", cascade = CascadeType.ALL)
    private List<Product> products;

}
