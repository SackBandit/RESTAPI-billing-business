package com.icodeap.facturacion_prod.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name ="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String details;
    private BigDecimal price;

    @Column(updatable = false)
    @CreationTimestamp
    private  LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
