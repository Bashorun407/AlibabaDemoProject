package com.alibaba.domain.Entity;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "ShopOwner_Detail")
public class ShopOwners {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long Id;

    @Column(name = "Shop_Name")
    private String shopName;

    @Column(name = "Shop_Number")
    private String shopNumber;

    @Column(name = "First_Name")
    private String shopOwnerFirstName;

    @Column(name = "Last_Name")
    private String shopOwnerLastName;

    @Column(name = "Phone_Number")
    private String phoneNumber;

    @Column(name = "Email_Address")
    private String emailAddress;

    @Column(name = "Date_Created")
    private Date dateCreated;

    @Column(name = "Updated_Status")
    private Boolean updatedStatus = false;

    @Column(name = "Date_Updated")
    private Date dateUpdated;
}
