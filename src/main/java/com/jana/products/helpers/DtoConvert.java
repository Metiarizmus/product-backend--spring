package com.jana.products.helpers;

import com.jana.products.dto.ProductDTO;
import com.jana.products.dto.UserDTO;
import com.jana.products.model.Measure;
import com.jana.products.model.Product;
import com.jana.products.model.Type;
import com.jana.products.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DtoConvert {

    private final ModelMapper modelMapper;

    public UserDTO convertUser(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public ProductDTO convertProduct(Product product, Measure measure, Type type) {

        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        productDTO.setMeasureName(measure.getName());
        productDTO.setTypeName(type.getName());

        return productDTO;
    }

    public ProductDTO convertProduct(Product product) {

        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        productDTO.setMeasureName(productDTO.getMeasureName());
        productDTO.setTypeName(productDTO.getTypeName());

        return productDTO;
    }
}
