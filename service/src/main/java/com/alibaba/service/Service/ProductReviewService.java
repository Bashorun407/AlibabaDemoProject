package com.alibaba.service.Service;

import com.alibaba.domain.Dao.ProductReviewDto;
import com.alibaba.domain.Entity.Product;
import com.alibaba.domain.Entity.ProductReview;
import com.alibaba.domain.Entity.QProductReview;
import com.alibaba.repository.Repository.ProductReppo;
import com.alibaba.repository.Repository.ProductReviewReppo;
import com.alibaba.service.Exception.Exception.ApiRequestException;
import com.alibaba.service.Response.RestResponse.ResponsePojo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
public class ProductReviewService {

    @Autowired
    private ProductReppo productReppo;

    @Autowired
    private ProductReviewReppo productReviewReppo;


    @Autowired
    private EntityManager entityManager;


    //Method to create review
    public ResponsePojo<ProductReview> createReview(Long Id, ProductReviewDto productRevDto){

        //To verify that 'empty' or 'null' values are not entered into the database
        if(ObjectUtils.isEmpty(Id))
            throw new ApiRequestException("Id is needed to rate this product!!");

        //Introducing ProductReppo to verify information entered
        Optional<Product> productOptional1 = productReppo.findById(Id);
        productOptional1.orElseThrow(()->new ApiRequestException(String.format("Product with this Id %s, not found!", Id)));

        ProductReview productRev = new ProductReview();
        //The following information were collected from the Product Database
        productRev.setId(productOptional1.get().getId());
        productRev.setProductName(productOptional1.get().getProductName());
        productRev.setProductNumber(productOptional1.get().getProductNumber());

        //The following data are collected from the productRevDto object to initialize the variables with zero value instead of 'null'
        productRev.setFiveStarRating(productRevDto.getFiveStarRating());
        productRev.setFourStarRating(productRevDto.getFourStarRating());
        productRev.setThreeStarRating(productRevDto.getThreeStarRating());
        productRev.setTwoStarRating(productRevDto.getTwoStarRating());
        productRev.setOneStarRating(productRevDto.getOneStarRating());
        productRev.setRating(productRevDto.getRating());
        productRev.setNumberOfReviews(productRevDto.getNumberOfReviews());


        productReviewReppo.save(productRev);

        ResponsePojo<ProductReview> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productRev);
        responsePojo.setMessage("Review variables initiated!");

        return responsePojo;
    }

    //(1) Method 1 for Creating a ProductReview
    public ResponsePojo<ProductReview> updateRatings(Long Id, Long rating, ProductReviewDto productRevDto){

        //To verify that 'empty' or 'null' values are not entered into the database
        if(ObjectUtils.isEmpty(Id))
            throw new ApiRequestException("Id is needed to rate this product!!");

        if(!(rating>=1 && rating<=5))
            throw new ApiRequestException("rating must be from 1 to 5!!");

        //Introducing ProductReppo to verify information entered
        Optional<ProductReview> productReviewOptional1 = productReviewReppo.findById(Id);
        productReviewOptional1.orElseThrow(()->new ApiRequestException(String.format("Product with this Id %s, not found!", Id)));

        //The following retrieves the data needed from the Product database
        ProductReview productRev = productReviewOptional1.get();

                //Conditional flows to check and update rating
        if(rating == 5) {
            productRev.setFiveStarRating(productRev.getFiveStarRating() + 1);
        }

        if(rating == 4) {
            productRev.setFourStarRating(productRev.getFourStarRating() + 1);
        }

        if(rating == 3) {
            productRev.setThreeStarRating(productRev.getThreeStarRating() + 1);
        }

        if(rating == 2) {
            productRev.setTwoStarRating(productRev.getTwoStarRating() + 1);
        }

        if(rating == 1) {
            productRev.setOneStarRating(productRev.getOneStarRating() + 1);
        }

        Long one = (productRev.getOneStarRating() * 20/100);
        Long two = (productRev.getTwoStarRating() * 40/100);
        Long three = (productRev.getThreeStarRating() * 60/100);
        Long four = (productRev.getFourStarRating() * 80/100);
        Long five = productRev.getFiveStarRating();

        //Getting Rating value for ProductReview
        Long ratingTotal = one + two + three + four + five;
        productRev.setRating(ratingTotal);

        productReviewReppo.save(productRev);
        ResponsePojo<ProductReview> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productRev);
        responsePojo.setMessage(String.format("Review for Product with Id %s, done!", productRevDto.getId()));

        return responsePojo;
    }

    //(2) Method to get Top Ranking Products
    public ResponsePojo<List<ProductReview>> topRankingProducts(){
        QProductReview qProductReview = QProductReview.productReview;
        //BooleanBuilder predicate = new BooleanBuilder();

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<ProductReview> jpaQuery = jpaQueryFactory.selectFrom(qProductReview)
                .where(qProductReview.rating.gt(30))
                .orderBy(qProductReview.Id.desc());

        List<ProductReview> productList = jpaQuery.fetch();

        ResponsePojo<List<ProductReview>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Top Ranking Products");

        return responsePojo;
    }


    //(4) Method to get products by category
    public ResponsePojo<List<ProductReview>> getTrendingProducts(){

        QProductReview qProductReview = QProductReview.productReview;
        BooleanBuilder predicate = new BooleanBuilder();

            predicate.and(qProductReview.fiveStarRating.between(20, 30));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<ProductReview> jpaQuery = jpaQueryFactory.selectFrom(qProductReview)
                .where(predicate.and(qProductReview.fiveStarRating.gt(15)))
                .orderBy(qProductReview.Id.desc());

        List<ProductReview> productList = jpaQuery.fetch();

        ResponsePojo<List<ProductReview>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Trending Products");

        return responsePojo;
    }

    //(5) Method to get user's comment
    public ResponsePojo<ProductReview> getUsersComment(Long Id, ProductReviewDto productReviewDto ){

        if(ObjectUtils.isEmpty(Id))
            throw new ApiRequestException("Id is needed for review");

        Optional<ProductReview> productReviewOptional = productReviewReppo.findById(Id);
        productReviewOptional.orElseThrow(()->new ApiRequestException(String.format("Product with this Id %s does not exist", Id)));

        ProductReview productRev = productReviewOptional.get();

        //Adding new comments to old comments
        productRev.setComment(  "(" + productReviewDto.getComment() +  ")" + " ("+ productRev.getComment() +") " );

        //increasing the number of reviews by 1 whenever a user makes a comment on a product
        productRev.setNumberOfReviews(productRev.getNumberOfReviews() + 1);

        productReviewReppo.save(productRev);

        ResponsePojo<ProductReview> responsePojo = new ResponsePojo<>();
        responsePojo.setMessage("Thanks for your feedback!");
        responsePojo.setData(productRev);

        return responsePojo;
    }

}
