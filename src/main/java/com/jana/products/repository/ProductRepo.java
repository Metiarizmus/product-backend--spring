package com.jana.products.repository;

import com.jana.products.enums.ProductEnum;
import com.jana.products.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface ProductRepo extends JpaRepository<Product, Integer> {

    Optional<Product> findById(Integer id);
    List<Product> findAllByCreatorId(Integer creatorId);
    List<Product> findAllByUsers_EmailAndAndProductEnum(String email, ProductEnum productEnum);

}
