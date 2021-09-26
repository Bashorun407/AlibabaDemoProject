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

    @Column(name = "Discount")
    private Long discount;

    @Column(name = "Date_Listed")
    private Date dateListed;
    }
