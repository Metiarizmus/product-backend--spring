package com.jana.products.repository;

import com.jana.products.model.Measure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasureRepo extends JpaRepository<Measure, Integer> {
}
