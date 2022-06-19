package com.jana.products.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "type")
@Entity
@Getter
@Setter
public class Type extends BaseEntity {

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL)
    private Set<Product> productSet;

    public Type(String name) {
        this.name = name;
    }
}
