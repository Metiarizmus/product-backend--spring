package com.jana.products.service;

import com.jana.products.dto.ProductDTO;
import com.jana.products.enums.ProductEnum;
import com.jana.products.helpers.DtoConvert;
import com.jana.products.model.Measure;
import com.jana.products.model.Product;
import com.jana.products.model.Type;
import com.jana.products.model.User;
import com.jana.products.repository.MeasureRepo;
import com.jana.products.repository.ProductRepo;
import com.jana.products.repository.TypeRepo;
import com.jana.products.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepo productRepo;
    private final MeasureRepo measureRepo;
    private final TypeRepo typeRepo;
    private final UserRepo userRepo;
    private final DtoConvert dtoConvert;

    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO, String email) {

        User creator = userRepo.findByEmail(email);

        Measure measure = new Measure(productDTO.getMeasureName());
        Type type = new Type(productDTO.getTypeName());

        measureRepo.save(measure);
        typeRepo.save(type);

        Product product = new Product(productDTO.getProductName(), productDTO.getPrice(), productDTO.getImage(), ProductEnum.USUAL);
        product.setMeasure(measure);
        product.setType(type);
        product.setCreatorId(creator.getId());
        product.setUsers(Collections.singleton(creator));

        productRepo.save(product);

        log.info("create product {}", product.getName());
        return productDTO;
    }

    public ProductDTO getById(Integer id) {

        log.info("get product by id {}", id );
        return dtoConvert.convertProduct(productRepo.findById(id).get());
    }

    public List<ProductDTO> findAll() {
        return productRepo.findAll().stream().map(dtoConvert::convertProduct).collect(Collectors.toList());
    }

    public List<ProductDTO> findAllByCreatorId(String email) {
        log.info("get all product by admin {}", email);
        return productRepo.findAllByCreatorId(userRepo.findByEmail(email).getId()).stream().map(dtoConvert::convertProduct).collect(Collectors.toList());
    }


    @Transactional
    public ProductDTO editProduct(ProductDTO productDTO) {
        Measure measure = new Measure(productDTO.getMeasureName());
        Type type = new Type(productDTO.getTypeName());

        measureRepo.save(measure);
        typeRepo.save(type);

        Optional<Product> product = productRepo.findById(productDTO.getId());
        product.get().setName(productDTO.getProductName());
        product.get().setPrice(productDTO.getPrice());
        product.get().setImage(productDTO.getImage());
        product.get().setMeasure(measure);
        product.get().setType(type);

        productRepo.save(product.get());

        log.info("edit product with id = {}", productDTO.getId());
        return productDTO;
    }

    @Transactional
    public ProductDTO changeStatus(Integer id, ProductEnum productEnum, String email) {
        Optional<Product> product = productRepo.findById(id);
        product.get().setProductEnum(productEnum);
        productRepo.save(product.get());

        User user = userRepo.findByEmail(email);
        user.getProducts().add(product.get());

        userRepo.save(user);

        log.info("change status of product with id = {} status = {}", id, productEnum);

        return dtoConvert.convertProduct(product.get());
    }

    public List<ProductDTO> listProductsInCartForUser(String email, ProductEnum productEnum) {
        return productRepo.findAllByUsers_EmailAndAndProductEnum(email, productEnum).stream().map(dtoConvert::convertProduct).collect(Collectors.toList());
    }

}
