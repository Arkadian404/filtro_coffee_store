package com.data.filtro.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "huongvi")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flavor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mahuongvi")
    private Integer id;

    @Column(name = "tenhuongvi")
    private String flavorName;

    @Column(name = "mota")
    private String description;

    @Column(name = "tinhtrang")
    private Integer status;

    @OneToMany(mappedBy = "flavor", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Product> products;

}
