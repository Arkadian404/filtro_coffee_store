package com.data.filtro.model.custom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BestSellingFlavors {
    private Integer id;
    private String flavorName;
    private Integer quantity;
    private Integer total;
}
