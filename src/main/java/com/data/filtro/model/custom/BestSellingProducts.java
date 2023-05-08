package com.data.filtro.model.custom;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BestSellingProducts {
    private Integer productId;
    private String productName;
    private Integer quantity;
    private Integer total;

}
