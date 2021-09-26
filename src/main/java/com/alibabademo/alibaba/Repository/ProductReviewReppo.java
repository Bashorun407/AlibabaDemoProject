package com.alibabademo.alibaba.Repository;

import com.alibabademo.alibaba.Entity.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductReviewReppo extends JpaRepository<ProductReview, Long> {

    Optional<ProductReview> findByProductNumber(Long productNumber);
}
