package com.icodeap.facturacion_prod.respository;

import com.icodeap.facturacion_prod.models.BillDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillDetailsRepository extends JpaRepository<BillDetails,Integer> {
}
