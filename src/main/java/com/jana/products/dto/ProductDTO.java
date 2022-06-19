package com.jana.products.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDTO {

    private Integer id;

    private String productName;

    private Double price;

    private byte[] image;

    private String measureName;

    private String typeName;

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", name='" + productName + '\'' +
                ", price=" + price +
                ", measureName='" + measureName + '\'' +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
