package com.icodeap.facturacion_prod.DTOs;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Data
@Setter
@Getter
public class ProductDTO {

    private Integer id;
    private String name;
    private String details;
    private BigDecimal price;

}
