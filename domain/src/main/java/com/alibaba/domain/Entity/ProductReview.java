package com.alibaba.domain.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ProductReview {

    @Id
    @Column(name = "ID")
    private Long Id;

    @Column(name = "Product_Name")
    private String productName;

    @Column(name = "Product_Number")
    private Long productNumber;

    @Column(name = "Five_Star_Rating")
    private Long fiveStarRating;

    @Column(name = "Four_Star_Rating")
    private Long fourStarRating;

    @Column(name = "Three_Star_Rating")
    private Long threeStarRating;

    @Column(name = "Two_Star_Rating")
    private Long twoStarRating;

    @Column(name = "One_Star_Rating")
    private Long oneStarRating;

    @Column(name = "Rating")
    private Long rating;

    @Column(name = "Number_Of_Reviews")
    private Long numberOfReviews;

    @Column(name = "Comment")
    private String comment;
}
