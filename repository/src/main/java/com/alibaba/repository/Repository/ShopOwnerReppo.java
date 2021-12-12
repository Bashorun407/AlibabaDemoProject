package com.alibaba.repository.Repository;

import com.alibaba.domain.Entity.ShopOwners;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ShopOwnerReppo extends JpaRepository<ShopOwners, Long> {

    Optional<ShopOwners> findByShopNumber(String shopNumber);
    Optional<ShopOwners> findByShopName(String shopName);
}
