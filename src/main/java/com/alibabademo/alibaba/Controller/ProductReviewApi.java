package com.alibabademo.alibaba.Controller;

import com.alibabademo.alibaba.Dao.ProductDto;
import com.alibabademo.alibaba.Entity.ProductReview;
import com.alibabademo.alibaba.RestResponse.ResponsePojo;
import com.alibabademo.alibaba.Service.ProductReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alibaba")
public class ProductReviewApi {

    @Autowired
    private ProductReviewService productReviewService;

    //(1) Method 1 for Creating a ProductReview
    public ResponsePojo<ProductReview> updateRatingsAndReviews(ProductDto productDto, Long rating){
        return productReviewService.updateRatingsAndReviews(productDto, rating);
    }


    }
