package com.alibabademo.alibaba.Dao;

import lombok.Data;

import java.util.Date;

@Data
public class ProductTransactionDto {

    private Long Id;

    private String productName;

    private Long price;

    private Long productNumber;

    private Long quantityOrdered;

    private Long discount;

    private Long ShippingCost;

    private Long totalCost;

    private Boolean saleStatus;

    private Date dateSold;

    private Long transactionNumber;

    private String processingTime;

    private String companyName;

    private Long supplierContact;

    private String country;



}
