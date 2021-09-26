package com.alibabademo.alibaba.Controller;

import com.alibabademo.alibaba.Dao.ProductReviewDto;
import com.alibabademo.alibaba.Entity.Product;
import com.alibabademo.alibaba.Entity.ProductReview;
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

    //(1) Method 1 for Creating a ProductReview
    @PutMapping("/rating/{rating}")
    public ResponsePojo<ProductReview> updateRatingsAndReviews(@RequestBody ProductReviewDto reviewDto, @PathVariable("rating") Long rating) {
        return productReviewService.updateRatingsAndReviews(reviewDto, rating);
    }

    //(2) Method to List Top ranking products
    @GetMapping("/topRankingProducts")
    public ResponsePojo<List<ProductReview>> topRankingProducts(){
        return productReviewService.topRankingProducts();
    }

    //(3) Method to get top products by a company
    @GetMapping("/topProductsByCompany/{companyName}")
    public ResponsePojo<List<ProductReview>> topProductsByCompany(@PathVariable  String companyName){
        return productReviewService.topProductsByCompany(companyName);
    }

    //(4) Method to get products by category
    @GetMapping("/getTrendingProducts/{searchItem}")
    public ResponsePojo<List<ProductReview>> getTrendingProducts(@PathVariable String searchItem){
        return productReviewService.getTrendingProducts(searchItem);
    }


}

