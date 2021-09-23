package com.alibabademo.alibaba.Service;

import com.alibabademo.alibaba.Dao.ProductDto;
import com.alibabademo.alibaba.Dao.ProductReviewDto;
import com.alibabademo.alibaba.Entity.Product;
import com.alibabademo.alibaba.Entity.ProductReview;
import com.alibabademo.alibaba.Exception.ApiException;
import com.alibabademo.alibaba.Repository.ProductReppo;
import com.alibabademo.alibaba.Repository.ProductReviewReppo;
import com.alibabademo.alibaba.RestResponse.ResponsePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Service
public class ProductReviewService {

    @Autowired
    private ProductReviewReppo productReviewReppo;

    @Autowired
    private ProductReppo productReppo;

    //(1) Method 1 for Creating a ProductReview
    public ResponsePojo<ProductReview> updateRatingsAndReviews(ProductDto productDto, Long rating){

        //To verify that 'empty' or 'null' values are not entered into the database
        if(ObjectUtils.isEmpty(productDto.getId()))
            throw new ApiException("Id is needed for Rating");

        //Introducing ProductReppo to verify information entered
        Optional<Product> productOptional1 = productReppo.findById(productDto.getId());
        productOptional1.orElseThrow(()->new ApiException(String.format("Product with this Id %s, not found!", productDto.getId())));

        Optional<Product> productOptional2 = productReppo.findByProductNumber(productDto.getProductNumber());
        productOptional2.orElseThrow(()->new ApiException(String.format("Product with this Number %s not found!!", productDto.getId())));

        //To verify that the Id and Product Number entered are for the same product
        Product product1 = productOptional1.get();
        Product product2 = productOptional2.get();

        if(product1 != product2)
            throw new ApiException("The Id Entered and Product-Number entered are for different products...Try again");

        //The following retrieves the data needed from the database
        ProductReview productReview = new ProductReview();
        productReview.setId(product1.getId());
        productReview.setProductName(product1.getProductName());
        productReview.setProductNumber(product1.getProductNumber());
        productReview.setCompanyName(product1.getCompanyName());
        productReview.setCountry(product1.getCountry());


        //Conditional flows to check and update rating
        if(rating == 5) {
            productReview.setFiveStarRating(productReview.getFiveStarRating() + 1);
        }

        if(rating == 4) {
            productReview.setFourStarRating(productReview.getFourStarRating() + 1);
        }

        if(rating == 3) {
            productReview.setThreeStarRating(productReview.getThreeStarRating() + 1);
        }

        if(rating == 2) {
            productReview.setThreeStarRating(productReview.getTwoStarRating() + 1);
        }

        if(rating == 1) {
            productReview.setOneStarRating(productReview.getOneStarRating() + 1);
        }

        productReviewReppo.save(productReview);

        Long ratingTotal = (productReview.getFiveStarRating() + (productReview.getFourStarRating() * 80/100)
                + (productReview.getThreeStarRating() * 60/100) + (productReview.getTwoStarRating() * 40/100)
                + (productReview.getOneStarRating() * 20/100))/5;

        //Getting Rating and Review Entity for ProductReview Entity
        productReview.setRating(ratingTotal);
        productReview.setNumberOfReviews(productReview.getNumberOfReviews() + 1);
        productReviewReppo.save(productReview);

        //Getting Rating and Review for Product Entity
        product1.setRatings(ratingTotal);
        product1.setReviews(productReview.getNumberOfReviews());
        productReppo.save(product1);

        ResponsePojo<ProductReview> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productReview);
        responsePojo.setMessage(String.format("Review for Product with Id %s, created", productDto.getId() ));

        return responsePojo;
    }

}
