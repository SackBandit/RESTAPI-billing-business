package com.icodeap.facturacion_prod.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "bill_details")
public class BillDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer idProduct;
    private Integer amount;
    private BigDecimal price;
    private BigDecimal total;
    @CreationTimestamp
    private LocalDateTime date;

    @ManyToOne
    @JsonIgnore
    private Bill factura;


}
