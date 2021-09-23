package com.alibabademo.alibaba.Dao;

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

    private Long totalCost;

    private Long discount;

    private Long actualAmount;

    private Long shippingCharge;

    private Long shippingDiscount;

    private Long shippingCost;

    private Long quantityOrdered;

    private String processingTime;

    private Boolean saleStatus;

    private Date dateSold;

    private Long transactionNumber;

    private Date dateListed;

    private Long timeListed;

    private Long reviews;

    private Long ratings;

    private String companyName;

    private Long supplierContact;

    private String country;

}
