package com.alibabademo.alibaba.Service;

import com.alibabademo.alibaba.Dao.ProductTransactionDto;
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
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductTransactionService {

    @Autowired
    private ProductTransactionReppo productTransactionReppo;

    @Autowired
    private ProductReppo productReppo;

    @Autowired
    private EntityManager entityManager;

    //(1) The methods stated here are to engage, input and increment certain features of the Product table
    public ResponsePojo<ProductTransaction> clientTransaction(Long Id, ProductTransactionDto productTranDto){

        if(ObjectUtils.isEmpty(Id))
            throw new ApiException("Id empty...insert Id");
        Optional<Product> productOptional1 = productReppo.findById(Id);
        productOptional1.orElseThrow(()->new ApiException(String.format("Product with Id %s not found!", Id)));


        ProductTransaction productTran = new ProductTransaction();

        productTran.setId(productOptional1.get().getId());
        productTran.setProductName(productOptional1.get().getProductName());
        productTran.setProductNumber(productOptional1.get().getProductNumber());
        productTran.setPrice(productOptional1.get().getPrice());
        productTran.setQuantityOrdered(productTranDto.getQuantityOrdered());
        productTran.setDiscount(productOptional1.get().getDiscount());
        productTran.setShippingCost(productTranDto.getShippingCost());
        productTran.setCompanyName(productTranDto.getCompanyName());
        productTran.setSupplierContact(productTranDto.getSupplierContact());
        productTran.setCountry(productTranDto.getCountry());

        Long amountPaid = productTranDto.getPrice();
        Long costPrice = productTran.getPrice();

        if( amountPaid<costPrice)
            throw new ApiException("This transaction is cancelled!!");

        //Calculation to get total cost
        Long disCountPrice = costPrice - ((productOptional1.get().getDiscount()) * costPrice/100);


        productTran.setTotalCost((disCountPrice * productTranDto.getQuantityOrdered()) + productTran.getShippingCost());
        productTran.setSaleStatus(true);

        //If user paid for shipping
        if(productTran.getShippingCost()!=0)
            productTran.setShippingStatus(true);

        productTran.setDateSold(new Date());
        productTran.setTransactionNumber(new Date().getTime());
        productTran.setProcessingTime(productTranDto.getProcessingTime());


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
    public ResponsePojo<List<ProductTransaction>> lowPriceCommodities(){
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
