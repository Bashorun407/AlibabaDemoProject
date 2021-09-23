package com.alibabademo.alibaba.Repository;

import com.alibabademo.alibaba.Entity.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReviewReppo extends JpaRepository<ProductReview, Long> {
}
