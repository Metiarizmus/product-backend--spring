package com.jana.products.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "measure")
@Entity
@Getter
@Setter
public class Measure extends BaseEntity {

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "measure", cascade = CascadeType.ALL)
    private Set<Product> productSet;

    public Measure(String name) {
        this.name = name;
    }
}
