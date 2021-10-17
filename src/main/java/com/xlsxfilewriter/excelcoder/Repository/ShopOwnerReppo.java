package com.xlsxfilewriter.excelcoder.Repository;

import com.xlsxfilewriter.excelcoder.Entity.ShopOwners;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopOwnerReppo extends JpaRepository<ShopOwners, Long> {

    Optional <ShopOwners> findShopOwnersByShopID(String shopID);
}
