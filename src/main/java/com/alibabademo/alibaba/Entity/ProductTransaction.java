package com.alibabademo.alibaba.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class ProductTransaction {

    @Id
    @Column(name = "Id")
    private Long Id;

    @Column(name = "Product_Name")
    private String productName;

    @Column(name = "Product_Number")
    private Long productNumber;

    @Column(name = "Price")
    private Long price;

    @Column(name = "Quantity_Ordered")
    private Long quantityOrdered;

    @Column(name = "Discount")
    private Long discount;

    @Column(name = "Shipping_Cost")
    private Long ShippingCost;

    @Column(name = "Total_Cost")
    private Long totalCost;

    @Column(name = "Sale_Status")
    private Boolean saleStatus;

    @Column(name = "Ready_To_Ship")
    private Boolean shippingStatus;

    @Column(name = "Date_Sold")
    private Date dateSold;

    @Column(name = "Transaction_Number")
    private Long transactionNumber;

    @Column(name = "Procession_Time")
    private String processingTime;

    @Column(name = "Company_Name")
    private String companyName;

    @Column(name = "Supplier_Contact")
    private Long supplierContact;

    @Column(name = "Country")
    private String country;



}
