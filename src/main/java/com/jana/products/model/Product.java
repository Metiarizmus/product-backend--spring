package com.jana.products.model;

import com.jana.products.enums.ProductEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Table(name="product")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {

    @Column(name="name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "measure_id", nullable = false)
    private Measure measure;

    @Column(name = "status", nullable = true)
    private ProductEnum productEnum;

    @Column(name = "creatorId", nullable = false)
    private Integer creatorId;

    @ManyToMany(mappedBy = "products", fetch = FetchType.EAGER)
    private Set<User> users;

//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;

    public Product(String name, Double price, byte[] image, ProductEnum productEnum) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.productEnum = productEnum;
    }
}