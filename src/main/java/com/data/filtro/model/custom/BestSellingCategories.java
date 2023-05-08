package com.data.filtro.model.custom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BestSellingCategories {

    private Integer id;
    private String categoryName;
    private Integer quantity;
    private Integer total;

}
