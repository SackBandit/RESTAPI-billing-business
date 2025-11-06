package com.icodeap.facturacion_prod.services;

import com.icodeap.facturacion_prod.DTOs.ProductDTO;
import com.icodeap.facturacion_prod.models.Product;
import com.icodeap.facturacion_prod.respository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }


    public ProductDTO save(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    public List<ProductDTO> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
    }

    public ProductDTO findById(Integer id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return modelMapper.map(product, ProductDTO.class);
    }


    public boolean deleteById(Integer id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<ProductDTO> update(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        if (productRepository.existsById(product.getId())) {
            Product updatedProduct = productRepository.save(product);
            return Optional.of(modelMapper.map(updatedProduct, ProductDTO.class));
        }
        return Optional.empty();
    }



}
