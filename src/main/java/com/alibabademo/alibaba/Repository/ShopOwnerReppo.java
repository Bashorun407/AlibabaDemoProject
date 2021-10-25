package com.alibabademo.alibaba.Repository;

import com.alibabademo.alibaba.Dao.ShopOwnersDto;
import com.alibabademo.alibaba.Entity.ShopOwners;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopOwnerReppo extends JpaRepository<ShopOwners, Long> {

    Optional<ShopOwners> findByShopNumber(String shopNumber);
    Optional<ShopOwners> findByShopName(String shopName);
}
