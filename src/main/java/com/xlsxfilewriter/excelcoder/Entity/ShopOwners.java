package com.xlsxfilewriter.excelcoder.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "Shop_Owners_On_Alibaba")
public class ShopOwners {

    @javax.persistence.Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Column(name = "Company_Name")
    private String companyName;

    @Column(name = "Shop_ID")
    private String shopID;

    @Column(name = "First_Name")
    private String firstName;

    @Column(name = "Last_Name")
    private String lastName;

    @Column(name = "Phone_Number")
    private String phoneNumber;

    @Column(name = "Email_Address")
    private String emailAddress;

    @Column(name = "Country_Location")
    private String country;

}


