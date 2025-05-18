package com.example.controller;

import com.example.dto.OrderDTO;
import com.example.dto.ProductDTO;
import com.example.entity.Order;
import com.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDTO){
        List<ProductDTO> productDetails = new ArrayList<>();
        for(Long productId: orderDTO.getProductIds()){
            try {
                    ProductDTO product = restTemplate.getForObject("http://localhost:8081/products" + productId, ProductDTO.class);
                    productDetails.add(product);
                }catch (RestClientException e){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Failed to fecth product with id" + productId);
            }
        }
        Order order = new Order();
        order.setCustomerName(orderDTO.getCustomerName());
        order.setOrderDate(LocalDate.now());
        order.setProductIds(orderDTO.getProductIds());
        return ResponseEntity.status(HttpStatus.CREATED).body(productDetails);
    }
}
