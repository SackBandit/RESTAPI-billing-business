package com.icodeap.facturacion_prod.respository;

import com.icodeap.facturacion_prod.models.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Integer> {
}
