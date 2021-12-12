package com.alibaba.repository.Repository;

import com.alibaba.domain.Entity.ProductTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTransactionReppo extends JpaRepository<ProductTransaction, Long> {
}
