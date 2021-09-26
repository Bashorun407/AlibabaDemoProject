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

    @Autowired
    private EntityManager entityManager;

    //(1) The methods stated here are to engage, input and increment certain features of the Product table
    public ResponsePojo<ProductTransaction> clientTransaction(ProductTransaction productTransaction, Boolean buyStatus){

        if(ObjectUtils.isEmpty(productTransaction.getId()))
            throw new ApiException("Id empty...insert Id");
        Optional<Product> productOptional1 = productReppo.findById(productTransaction.getId());
        productOptional1.orElseThrow(()->new ApiException(String.format("Product with Id %s not found!", productTransaction.getId())));

        Optional<Product> productOptional2 = productReppo.findByProductNumber(productTransaction.getProductNumber());
        productOptional2.orElseThrow(()-> new ApiException(String.format("Product with this Product-Number: %s not found!!", productTransaction.getProductNumber())));

        Product prod1 = productOptional1.get();
        Product prod2 = productOptional2.get();

        if(prod1!= prod2)
            throw new ApiException("The Id and Product Number Entered are for different products...try again!");

       if(buyStatus == false)
           throw new ApiException("This transaction is cancelled!!");

        ProductTransaction productTran = new ProductTransaction();

        productTran.setId(productTransaction.getId());
        productTran.setProductName(productOptional1.get().getProductName());
        productTran.setPrice(productOptional1.get().getPrice());
        productTran.setQuantityOrdered(productTransaction.getQuantityOrdered());
        productTran.setDiscount(productOptional1.get().getDiscount());
        productTran.setShippingCost(productTransaction.getShippingCost());
        productTran.setCompanyName(productTransaction.getCompanyName());
        productTran.setSupplierContact(productTransaction.getSupplierContact());
        productTran.setCountry(productTransaction.getCountry());

        //Calculation to get total cost
        Long price = productOptional1.get().getPrice();
        Long disCountPrice = price - ((productOptional1.get().getDiscount()) * price/100);


        productTran.setTotalCost((disCountPrice * productTransaction.getQuantityOrdered()) + productTran.getShippingCost());
        productTran.setSaleStatus(true);

        //If user paid for shipping
        if(productTran.getShippingCost()!=0)
            productTran.setShippingStatus(true);

        productTran.setDateSold(new Date());
        productTran.setTransactionNumber(new Date().getTime());
        productTran.setProcessingTime(productTransaction.getProcessingTime());


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


}
