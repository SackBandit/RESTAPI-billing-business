package com.icodeap.facturacion_prod.controllers;

import com.icodeap.facturacion_prod.DTOs.RequestBillDTO;
import com.icodeap.facturacion_prod.DTOs.ResponseBillDTO;
import com.icodeap.facturacion_prod.services.BillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/bills")
public class BillController {

    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @PostMapping
    public ResponseBillDTO save(@RequestBody RequestBillDTO requestBillDTO) {
        return billService.save(requestBillDTO);
    }

    @GetMapping
    public ResponseEntity<List<ResponseBillDTO>> findAll() {
        List<ResponseBillDTO> list = billService.findAll();
        if(list.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);

    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseBillDTO> findById(@PathVariable Integer id) {
      Optional<ResponseBillDTO> responseBillDTO = billService.findById(id);
      return responseBillDTO.map(
                ResponseEntity::ok
      ).orElseGet(
              ()-> ResponseEntity.notFound().build()
      );

    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        billService.deletebyId(id);
    }
}
