package com.alibabademo.alibaba.Repository;

import com.alibabademo.alibaba.Entity.ProductTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTransactionReppo extends JpaRepository<ProductTransaction, Long> {
}
