package com.alibaba.repository.Repository;

import com.alibaba.domain.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductReppo extends JpaRepository<Product, Long> {
    Optional<Product> findByProductNumber(Long productNumber);
}

