package com.alibabademo.alibaba.Service;

import com.alibabademo.alibaba.Entity.Product;
import com.alibabademo.alibaba.Entity.ProductTransaction;
import com.alibabademo.alibaba.Entity.QProductTransaction;
import com.alibabademo.alibaba.Exception.ApiException;
import com.alibabademo.alibaba.Repository.ProductReppo;
import com.alibabademo.alibaba.Repository.ProductTransactionReppo;
import com.alibabademo.alibaba.RestResponse.ResponsePojo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ProductTransactionService {

    @Autowired
    private ProductTransactionReppo productTransactionReppo;

    @Autowired
    private ProductReppo productReppo;

    private EntityManager entityManager;

    //(1) The methods stated here are to engage, input and increment certain features of the Product table
    public ResponsePojo<ProductTransaction> clientTransaction(Long Id, Long numberOrdered, Long discount, Long shippingCost,
                                                String companyName,
                                                Long supplierContact,
                                                String country){

        if(ObjectUtils.isEmpty(Id))
            throw new ApiException("Id empty...insert Id");
        Optional<Product> productOptional = productReppo.findById(Id);
        productOptional.orElseThrow(()->new ApiException(String.format("Product with Id %s not found!", Id)));

        ProductTransaction productTran = new ProductTransaction();
        productTran.setQuantityOrdered(numberOrdered);
        productTran.setId(productOptional.get().getId());
        productTran.setProductName(productOptional.get().getProductName());
        productTran.setPrice(productOptional.get().getPrice());
        productTran.setQuantityOrdered(numberOrdered);
        productTran.setDiscount(discount);
        productTran.setShippingCost(shippingCost);
        productTran.setCompanyName(companyName);
        productTran.setSupplierContact(supplierContact);
        productTran.setCountry(country);

        //Calculation to get total cost
        Long price = productOptional.get().getPrice();
        Long disCountPrice = price - (discount * price/100);


        productTran.setTotalCost((disCountPrice * numberOrdered) + shippingCost);
        productTran.setSaleStatus(true);
        productTran.setDateSold(new Date());
        productTran.setTransactionNumber(new Date().getTime());


        productTransactionReppo.save(productTran);


        ResponsePojo<ProductTransaction> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productTran);
        responsePojo.setMessage("Transaction successful!");


        return responsePojo;
    }



    //(2) Method to get Ready-To-Ship Products
    public ResponsePojo<List<ProductTransaction>> readyToShipProducts(){
        QProductTransaction qProductTransaction = QProductTransaction.productTransaction;
        //BooleanBuilder predicate = new BooleanBuilder();

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<ProductTransaction> jpaQuery = jpaQueryFactory.selectFrom(qProductTransaction)
                .where(qProductTransaction.saleStatus.eq(true).and(qProductTransaction.shippingStatus.eq(true)))
                .orderBy(qProductTransaction.transactionNumber.asc());

        List<ProductTransaction> productList = jpaQuery.fetch();

        ResponsePojo<List<ProductTransaction>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Ready To Ship");

        return responsePojo;
    }

    //(3) Method to get Weekly Deals
    public ResponsePojo<List<ProductTransaction>> weeklyDeals(){
        QProductTransaction qProductTransaction = QProductTransaction.productTransaction;
        //BooleanBuilder predicate = new BooleanBuilder();

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<ProductTransaction> jpaQuery = jpaQueryFactory.selectFrom(qProductTransaction)
                .where(qProductTransaction.saleStatus.eq(true))
                .orderBy(qProductTransaction.transactionNumber.asc());

        List<ProductTransaction> productList = jpaQuery.fetch();

        ResponsePojo<List<ProductTransaction>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Weekly Deals");

        return responsePojo;
    }

    //(4) Method to get small Commodities products
    public ResponsePojo<List<ProductTransaction>> smallCommodities(){
        QProductTransaction qProductTransaction = QProductTransaction.productTransaction;
        //BooleanBuilder predicate = new BooleanBuilder();

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<ProductTransaction> jpaQuery = jpaQueryFactory.selectFrom(qProductTransaction)
                .where(qProductTransaction.quantityOrdered.between(1, 10).and(qProductTransaction.price.between(100,300)))
                .orderBy(qProductTransaction.saleStatus.asc());

        List<ProductTransaction> productList = jpaQuery.fetch();

        ResponsePojo<List<ProductTransaction>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Small Commodities");

        return responsePojo;
    }


    //(5) Method to get products by category
    public ResponsePojo<List<ProductTransaction>> getReadyToShip(String searchItem){

        QProductTransaction qProductTransaction = QProductTransaction.productTransaction;
        BooleanBuilder predicate = new BooleanBuilder();
        if(StringUtils.hasText(searchItem))
            predicate.and(qProductTransaction.saleStatus.eq(true));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<ProductTransaction> jpaQuery = jpaQueryFactory.selectFrom(qProductTransaction)
                .where(predicate.and(qProductTransaction.dateSold.gt(new Date())));//I need to use a Date function which I can use to select day intervals

        List<ProductTransaction> productList = jpaQuery.fetch();

        ResponsePojo<List<ProductTransaction>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Ready To Ship Between 7 Days");

        return responsePojo;
    }


}
