package com.alibabademo.alibaba.Controller;

import com.alibabademo.alibaba.Dao.ProductReviewDto;
import com.alibabademo.alibaba.Entity.Product;
import com.alibabademo.alibaba.Entity.ProductReview;
import com.alibabademo.alibaba.Repository.ProductReppo;
import com.alibabademo.alibaba.RestResponse.ResponsePojo;
import com.alibabademo.alibaba.Service.ProductReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alibaba")
public class ProductReviewApi {

    @Autowired
    private ProductReviewService productReviewService;

    //Method to create review
    //(1) Method to create a Product Transaction object
    @PostMapping("/initiateReview/{Id}")
    public ResponsePojo<ProductReview> createReview(@PathVariable Long Id, @RequestBody ProductReviewDto productRevDto){
        return productReviewService.createReview(Id, productRevDto);
    }


    //(2) Method 1 to do ProductReview
    @PutMapping("/rating/{Id}/{rate}")
    public ResponsePojo<ProductReview> updateRatings(@PathVariable Long Id,  @PathVariable Long rate, @RequestBody ProductReviewDto productRevDto) {
        return productReviewService.updateRatings(Id, rate, productRevDto);
    }

    //(3) Method to List Top ranking products
    @GetMapping("/topRankingProducts")
    public ResponsePojo<List<ProductReview>> topRankingProducts(){
        return productReviewService.topRankingProducts();
    }

    //(4) Method to get products by category
    @GetMapping("/getTrendingProducts")
    public ResponsePojo<List<ProductReview>> getTrendingProducts(){
        return productReviewService.getTrendingProducts();
    }

    //(5) Method to get comment and increment review
    @PutMapping("/usersComment/{Id}")
    public ResponsePojo<ProductReview> getUsersComment(@PathVariable Long Id, @RequestBody ProductReviewDto productReviewDto){
        return  productReviewService.getUsersComment(Id, productReviewDto);
    }

}

