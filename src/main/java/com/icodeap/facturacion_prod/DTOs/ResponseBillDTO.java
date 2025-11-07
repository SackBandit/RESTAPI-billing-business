package com.icodeap.facturacion_prod.DTOs;

import jakarta.persistence.Transient;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
@Data
public class ResponseBillDTO {
    private  Integer id;
    private String numberBill;
    private BigDecimal subTotal;
    private BigDecimal total;
    private LocalDateTime date;
    private Set<ResponseDetailsBillDTO> responseDetails;

}
