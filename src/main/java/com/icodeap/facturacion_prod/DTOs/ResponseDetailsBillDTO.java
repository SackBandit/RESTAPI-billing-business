package com.icodeap.facturacion_prod.DTOs;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ResponseDetailsBillDTO {
    private Integer idProduct;
    private Integer amount;
    private BigDecimal price;
    private BigDecimal total;
}
