package com.jana.products.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jana.products.dto.ProductDTO;
import com.jana.products.repository.ProductRepo;
import com.jana.products.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequestMapping("/api/admins")
public class AdminController {

    private final ProductService productService;
    private final ObjectMapper objectMapper;
    private final ProductRepo productRepo;

    @PostMapping("/products")
    public ResponseEntity<?> createProduct(@RequestParam(name = "product") String productJson,
                                           @RequestParam("file") MultipartFile image,
                                           Principal principal) {

        try {
            ProductDTO productDTO = objectMapper.readValue(productJson, ProductDTO.class);
            productDTO.setImage(image.getBytes());

            productService.createProduct(productDTO, principal.getName());

            return new ResponseEntity<>(productDTO, HttpStatus.CREATED);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_GATEWAY);
    }


    @GetMapping("/products")
    public ResponseEntity<List<?>> getListMyProducts(Principal principal) {
        return new ResponseEntity<>(productService.findAllByCreatorId(principal.getName()), HttpStatus.OK);
    }

    @PutMapping("/products")
    public ResponseEntity<?> editProduct(@RequestParam(name = "product") String productJson) {

        try {
            ProductDTO productDTO = objectMapper.readValue(productJson, ProductDTO.class);

            productService.editProduct(productDTO);

            return new ResponseEntity<>(productDTO, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_GATEWAY);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Integer> deleteProduct(@PathVariable Integer id) {
        productRepo.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
