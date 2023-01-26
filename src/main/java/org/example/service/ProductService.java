package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.entity.Product;
import org.example.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repo;
    private final ObjectMapper mapper;

    public String getProduct(String productId) throws JsonProcessingException {
        return toJson(repo.getProduct(productId));
    }

    public String addProduct(String productJson) throws JsonProcessingException {
        Product product = mapper.readValue(productJson, Product.class);
        return toJson(repo.addProduct(product));
    }

    public void deleteProduct(String productId) {
        repo.deleteProduct(productId);
    }

    public String updateProduct(String productJson) throws JsonProcessingException {
        Product product = mapper.readValue(productJson, Product.class);
        return toJson(repo.updateProduct(product));
    }

    private String toJson(Object obj) throws JsonProcessingException {
        if (obj == null) return null;
        else return mapper.writeValueAsString(obj);
    }

}
