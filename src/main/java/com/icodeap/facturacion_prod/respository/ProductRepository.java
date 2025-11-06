package com.icodeap.facturacion_prod.respository;

import com.icodeap.facturacion_prod.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {
}
