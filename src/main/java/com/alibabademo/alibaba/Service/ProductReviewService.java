package com.alibabademo.alibaba.Service;

import com.alibabademo.alibaba.Dao.ProductReviewDto;
import com.alibabademo.alibaba.Entity.*;
import com.alibabademo.alibaba.Exception.ApiException;
import com.alibabademo.alibaba.Repository.ProductReppo;
import com.alibabademo.alibaba.Repository.ProductReviewReppo;
import com.alibabademo.alibaba.RestResponse.ResponsePojo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

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


    //(1) Method 1 for Creating a ProductReview
    public ResponsePojo<ProductReview> updateRatingsAndReviews(ProductReviewDto productReviewDto, Long rating){

        //To verify that 'empty' or 'null' values are not entered into the database
        if(ObjectUtils.isEmpty(productReviewDto.getId()))
            throw new ApiException("Id is needed for Rating");

        //Introducing ProductReppo to verify information entered
        Optional<Product> productOptional1 = productReppo.findById(productReviewDto.getId());
        productOptional1.orElseThrow(()->new ApiException(String.format("Product with this Id %s, not found!", productReviewDto.getProductNumber())));

        Optional<Product> productOptional2 = productReppo.findByProductNumber(productReviewDto.getProductNumber());
        productOptional2.orElseThrow(()->new ApiException(String.format("Product with this Number %s not found!!", productReviewDto.getId())));

        //To verify that the Id and Product Number entered are for the same product
        Product productRev1 = productOptional1.get();
        Product productRev2 = productOptional2.get();

        if(productRev1 != productRev2)
            throw new ApiException("The Id Entered and Product-Number entered are for different products...Try again");

        //The following retrieves the data needed from the database
        ProductReview productRev = new ProductReview();
        productRev.setId(productReviewDto.getId());
        productRev.setProductName(productReviewDto.getProductName());
        productRev.setProductNumber(productReviewDto.getProductNumber());
        productRev.setCompanyName(productReviewDto.getCompanyName());
        productRev.setComment(productReviewDto.getComment());

        //Conditional flows to check and update rating
        if(rating == 5) {
            productRev.setFiveStarRating(productReviewDto.getFiveStarRating() + 1);
        }

        if(rating == 4) {
            productRev.setFourStarRating(productReviewDto.getFourStarRating() + 1);
        }

        if(rating == 3) {
            productRev.setThreeStarRating(productReviewDto.getThreeStarRating() + 1);
        }

        if(rating == 2) {
            productRev.setThreeStarRating(productReviewDto.getTwoStarRating() + 1);
        }

        if(rating == 1) {
            productRev.setOneStarRating(productReviewDto.getOneStarRating() + 1);
        }

        productReviewReppo.save(productRev);

        Long ratingTotal = (productRev.getFiveStarRating() + (productRev.getFourStarRating() * 80/100)
                + (productRev.getThreeStarRating() * 60/100) + (productRev.getTwoStarRating() * 40/100)
                + (productRev.getOneStarRating() * 20/100))/5;

        //Getting Rating and Review Entity for ProductReview Entity
        productRev.setRating(ratingTotal);
        productRev.setNumberOfReviews(productRev.getNumberOfReviews() + 1);
        productReviewReppo.save(productRev);

        ResponsePojo<ProductReview> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productRev);
        responsePojo.setMessage(String.format("Review for Product with Id %s, done!", productReviewDto.getId() ));

        return responsePojo;
    }

    //(2) Method to get Top Ranking Products
    public ResponsePojo<List<ProductReview>> topRankingProducts(){
        QProductReview qProductReview = QProductReview.productReview;
        //BooleanBuilder predicate = new BooleanBuilder();

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<ProductReview> jpaQuery = jpaQueryFactory.selectFrom(qProductReview)
                .where(qProductReview.rating.gt(30))
                .orderBy(qProductReview.country.desc());

        List<ProductReview> productList = jpaQuery.fetch();

        ResponsePojo<List<ProductReview>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Top Ranking Products");

        return responsePojo;
    }

    //(3) Method to get top products by a company
    public ResponsePojo<List<ProductReview>> topProductsByCompany(String companyName){
        QProductReview qProductReview = QProductReview.productReview;
        BooleanBuilder predicate = new BooleanBuilder();

        if(StringUtils.hasText(companyName))
            predicate.and(qProductReview.companyName.likeIgnoreCase("%" + companyName + "%"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<ProductReview> jpaQuery = jpaQueryFactory.selectFrom(qProductReview)
                .where(predicate.and(qProductReview.numberOfReviews.gt(30)))
                .orderBy(qProductReview.companyName.desc());

        List<ProductReview> productList = jpaQuery.fetch();

        ResponsePojo<List<ProductReview>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Top Products by Company");

        return responsePojo;
    }

    //(4) Method to get products by category
    public ResponsePojo<List<ProductReview>> getTrendingProducts(String searchItem){

        QProductReview qProductReview = QProductReview.productReview;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(searchItem))
            predicate.and(qProductReview.fiveStarRating.between(20, 30));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<ProductReview> jpaQuery = jpaQueryFactory.selectFrom(qProductReview)
                .where(predicate.and(qProductReview.numberOfReviews.gt(15)))
                .orderBy(qProductReview.companyName.desc());

        List<ProductReview> productList = jpaQuery.fetch();

        ResponsePojo<List<ProductReview>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Trending Products");

        return responsePojo;
    }

}
