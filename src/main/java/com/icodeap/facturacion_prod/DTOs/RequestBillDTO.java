package com.icodeap.facturacion_prod.DTOs;

import lombok.Data;

import java.util.Set;
@Data
public class RequestBillDTO {
    private Integer id;
    private String BillNumber;
    private Set<RequestDetailsBillDTO> details;

}
