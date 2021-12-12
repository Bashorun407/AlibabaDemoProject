package com.alibaba.domain.Dao;

import lombok.Data;
import org.hibernate.exception.DataException;

import java.util.Date;

@Data
public class ProductDto {

    private Long Id;

    private String productName;

    private Long productNumber;

    private String category;

    private String productType;

    private String colour;

    private Boolean customized;

    private String description;

    private Long price;

    private Long availableQuantity;

    private Long discount;

    private Date dateListed;

    private String companyName;
}
