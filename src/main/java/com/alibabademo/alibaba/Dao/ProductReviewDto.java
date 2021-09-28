package com.alibabademo.alibaba.Dao;

import lombok.Data;

@Data
public class ProductReviewDto {

    private Long Id;

    private String productName;

    private Long productNumber;

    private Long numberOfReviews;

    private Long fiveStarRating;

    private Long fourStarRating;

    private Long threeStarRating;

    private Long twoStarRating;

    private Long oneStarRating;

    private Long rating;

    private String comment;

}
