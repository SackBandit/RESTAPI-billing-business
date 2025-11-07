package com.icodeap.facturacion_prod.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;



@Entity
@Table(name = "facturas")
@Setter
@Getter
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;
    private String numberBill;
    private BigDecimal subTotal;
    private BigDecimal total;

    @Transient
    private final double IVA = 0.15;

    @CreationTimestamp
    private LocalDateTime date;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "factura", orphanRemoval = true)
    private Set<BillDetails> billDetailsList;

}
