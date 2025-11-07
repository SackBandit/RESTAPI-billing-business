package com.icodeap.facturacion_prod.services;

import com.icodeap.facturacion_prod.DTOs.RequestBillDTO;
import com.icodeap.facturacion_prod.DTOs.RequestDetailsBillDTO;
import com.icodeap.facturacion_prod.DTOs.ResponseBillDTO;
import com.icodeap.facturacion_prod.DTOs.ResponseDetailsBillDTO;
import com.icodeap.facturacion_prod.models.Bill;
import com.icodeap.facturacion_prod.models.BillDetails;
import com.icodeap.facturacion_prod.models.Product;
import com.icodeap.facturacion_prod.respository.BillRepository;
import com.icodeap.facturacion_prod.respository.ProductRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BillService {

    private final BillRepository billRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public BillService(BillRepository billRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.billRepository = billRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    //Get all bills
    public List<ResponseBillDTO> findAll() {
        List<Bill> bills = billRepository.findAll();
        return bills.stream().map(bill -> {
            ResponseBillDTO dto = modelMapper.map(bill, ResponseBillDTO.class);
            if (bill.getBillDetailsList() != null) {
                Set<ResponseDetailsBillDTO> responseDetails = bill.getBillDetailsList().stream()
                        .map(d -> modelMapper.map(d, ResponseDetailsBillDTO.class))
                        .collect(Collectors.toSet());
                dto.setResponseDetails(responseDetails);
            }
            return dto;
        }).collect(Collectors.toList());
    }
    //Get bill by id
    public Optional<ResponseBillDTO> findById(Integer id) {
        return billRepository.findById(id)
                .map(bill -> {
                    ResponseBillDTO dto = modelMapper.map(bill, ResponseBillDTO.class);
                    if (bill.getBillDetailsList() != null) {
                        Set<ResponseDetailsBillDTO> responseDetails = bill.getBillDetailsList().stream()
                                .map(d -> modelMapper.map(d, ResponseDetailsBillDTO.class))
                                .collect(Collectors.toSet());
                        dto.setResponseDetails(responseDetails);
                    }
                    return dto;
                });
    }

    //Delete bill by id
    public void deletebyId(Integer id) {
        billRepository.deleteById(id);
    }
    //Save bill
    @Transactional
    public ResponseBillDTO save(RequestBillDTO requestBillDTO ) {
        if (requestBillDTO == null || requestBillDTO.getDetails() == null || requestBillDTO.getDetails().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La factura debe contener detalles.");
        }

        Bill bill = new Bill();
        BigDecimal subTotal = BigDecimal.ZERO;
        Set<BillDetails> details = new HashSet<>();

        bill.setNumberBill(requestBillDTO.getBillNumber());
        for (RequestDetailsBillDTO detailDTO : requestBillDTO.getDetails()) {
            Product product = productRepository.findById(detailDTO.getIdProduct())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Product not found with id: " + detailDTO.getIdProduct()));

            BigDecimal totalProduct = product.getPrice().multiply(BigDecimal.valueOf(detailDTO.getAmount()));
            subTotal = subTotal.add(totalProduct);

            BillDetails billDetails = new BillDetails();
            billDetails.setIdProduct(detailDTO.getIdProduct());
            billDetails.setPrice(product.getPrice());
            billDetails.setAmount(detailDTO.getAmount());
            billDetails.setTotal(totalProduct);
            billDetails.setFactura(bill);

            details.add(billDetails);
        }
        bill.setBillDetailsList(details);
        bill.setSubTotal(subTotal);
        bill.setTotal(subTotal.add(subTotal.multiply(BigDecimal.valueOf(bill.getIVA()))));

        Bill savedBill = billRepository.save(bill);

        // Mapear el bill guardado al DTO y poblar explícitamente los detalles
        ResponseBillDTO response = modelMapper.map(savedBill, ResponseBillDTO.class);
        if (savedBill.getBillDetailsList() != null) {
            Set<ResponseDetailsBillDTO> responseDetails = savedBill.getBillDetailsList().stream()
                    .map(d -> modelMapper.map(d, ResponseDetailsBillDTO.class))
                    .collect(Collectors.toSet());
            response.setResponseDetails(responseDetails);
        }

        return response;
    }

}