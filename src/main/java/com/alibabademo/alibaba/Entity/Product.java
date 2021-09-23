package com.alibabademo.alibaba.Entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Product {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Column(name = "Product_Name")
    private String productName;

    @Column(name = "Product_Number")
    private Long productNumber;

    @Column(name = "Category")
    private String category;

    @Column(name = "Product_Type")
    private String productType;

    @Column(name = "Colour")
    private String colour;

    @Column(name = "Customization_Status")
    private Boolean customized;

    @Column(name = "Product_Description")
    private String description;


    @Column(name = "Cost_Price")
    private Long price;

    @Column(name = "Available_Quantity")
    private Long availableQuantity;

    @Column(name = "Total_Cost")
    private Long totalCost;

    @Column(name = "Discount")
    private Long discount;

    @Column(name = "Actual_Amount")
    private Long actualAmount;

    @Column(name = "Shipping_Charge")
    private Long shippingCharge;

    @Column(name = "Shipping_Discount")
    private Long shippingDiscount;

    @Column(name = "Shipping Cost")
    private Long shippingCost;

    @Column(name = "Quantity_Ordered")
    private Long quantityOrdered;

    @Column(name = "Processing_Time")
    private String processingTime;

    @Column(name = "Sale_Status")
    private Boolean saleStatus;

    @Column(name = "Date_Sold")
    private Date dateSold;

    @Column(name = "Transaction_Number")
    private Long transactionNumber;

    @Column(name = "Date_Listed")
    private Date dateListed;

    @Column(name = "Time_Listed")
    private Long timeListed;

    @Column(name = "Reviews")
    private Long reviews;

    @Column(name = "Ratings")
    private Long ratings;

    @Column(name = "Company_Name")
    private String companyName;

    @Column(name = "Supplier_Contact")
    private Long supplierContact;

    @Column(name = "Country/Region")
    private String country;

}
