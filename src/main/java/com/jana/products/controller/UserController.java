package com.jana.products.controller;

import com.jana.products.enums.ProductEnum;
import com.jana.products.helpers.DtoConvert;
import com.jana.products.repository.UserRepo;
import com.jana.products.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/users")
public class UserController {

    private final UserRepo userRepo;
    private final DtoConvert convert;
    private final ProductService productService;

    @GetMapping("/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email, Principal principal) {

        return new ResponseEntity<>(convert.convertUser(userRepo.findByEmail(email)), HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Integer id) {
        return new ResponseEntity<>(productService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<List<?>> getListProducts() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/change-status/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable Integer id, @RequestParam("status") ProductEnum status, Principal principal) {

        return new ResponseEntity<>(productService.changeStatus(id, status, principal.getName()), HttpStatus.OK);

    }

    @GetMapping("/cart/products")
    public ResponseEntity<List<?>> getListProductsInCart(Principal principal) {
        return new ResponseEntity<>(productService.listProductsInCartForUser(principal.getName(), ProductEnum.ORDERING), HttpStatus.OK);
    }
}
