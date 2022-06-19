package com.jana.products.repository;

import com.jana.products.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepo extends JpaRepository<Type, Integer> {
}
