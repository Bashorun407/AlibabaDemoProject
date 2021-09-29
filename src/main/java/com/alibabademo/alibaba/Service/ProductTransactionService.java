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
    public ResponsePojo<ProductTransaction> clientTransaction(Long Id, ProductTransactionDto productTranDto) {

        if (ObjectUtils.isEmpty(Id))
            throw new ApiException("Id empty...insert Id");
        Optional<Product> productOptional1 = productReppo.findById(Id);
        productOptional1.orElseThrow(() -> new ApiException(String.format("Product with Id %s not found!", Id)));

        Optional<ProductTransaction> prodTranOptional = productTransactionReppo.findById(Id);
       if( prodTranOptional.isPresent())
           throw new ApiException("Id can not be created twice!!");


        ProductTransaction productTran = new ProductTransaction();

        productTran.setId(productOptional1.get().getId());
        productTran.setProductName(productOptional1.get().getProductName());
        productTran.setProductNumber(productOptional1.get().getProductNumber());
        productTran.setPrice(productOptional1.get().getPrice());
        productTran.setQuantityOrdered(productTranDto.getQuantityOrdered());
        productTran.setDiscount(productTranDto.getDiscount());
        productTran.setShippingCost(productTranDto.getShippingCost());
        productTran.setTotalCost(productTranDto.getTotalCost());
        productTran.setSaleStatus(false);

        //productTran.setTransactionNumber(new Date().getTime());
        // productTran.setProcessingTime("4 Work Days");
        productTran.setCompanyName(productTranDto.getCompanyName());
        productTran.setSupplierContact(productTranDto.getSupplierContact());
        productTran.setCountry(productTranDto.getCountry());

        productTransactionReppo.save(productTran);

        ResponsePojo<ProductTransaction> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productTran);
        responsePojo.setMessage("Transaction initiated successfully!");


        return responsePojo;
    }

    //(2) Method to conduct transaction
    public ResponsePojo< ProductTransaction> transactProduct(Long Id, ProductTransactionDto productTransactionDto, Long amountPaid ){

        Optional<Product> prod = productReppo.findById(Id);
        prod.orElseThrow(()-> new ApiException("Product with this Id not found on Product wall!!"));
        Product product= prod.get();

        Optional<ProductTransaction> productTransaction = productTransactionReppo.findById(Id);
        productTransaction.orElseThrow(()->new ApiException("Id does not exist on ProductTransaction wall!!"));
        ProductTransaction prodTrans = productTransaction.get();

        Long costPrice = product.getPrice();

        if(amountPaid<costPrice)
            throw new ApiException("This transaction is cancelled!!");


        prodTrans.setProductName(product.getProductName());
        prodTrans.setProductNumber(product.getProductNumber());
        prodTrans.setPrice(product.getPrice());
        prodTrans.setQuantityOrdered(productTransactionDto.getQuantityOrdered());
        prodTrans.setDiscount(productTransactionDto.getDiscount());
        prodTrans.setShippingCost(productTransactionDto.getShippingCost());
        prodTrans.setTotalCost(productTransactionDto.getTotalCost());
        prodTrans.setSaleStatus(false);
        prodTrans.setCompanyName(productTransactionDto.getCompanyName());
        prodTrans.setSupplierContact(productTransactionDto.getSupplierContact());
        prodTrans.setCountry(productTransactionDto.getCountry());

        //Mathematical calculations of Total cost and amount to pay
        Long productDiscount = (product.getDiscount());
        Long quantityOrder =productTransactionDto.getQuantityOrdered();
        Long shippingCost = productTransactionDto.getShippingCost();
        //Calculation to get total cost
        Long disCountPrice = costPrice - (productDiscount * costPrice/100);


        prodTrans.setTotalCost((disCountPrice * quantityOrder ));
        prodTrans.setSaleStatus(true);
        productTransactionReppo.save(prodTrans);

        //If user paid for shipping
        prodTrans.setShippingStatus(true);

        prodTrans.setDateSold(new Date());
        prodTrans.setTransactionNumber(new Date().getTime());
        prodTrans.setProcessingTime(productTransactionDto.getProcessingTime());

        productTransactionReppo.save(prodTrans);

        ResponsePojo<ProductTransaction> responsePojo = new ResponsePojo<>();
        responsePojo.setData(prodTrans);
        responsePojo.setMessage("Transaction successful!");

        return responsePojo;
    }



    //(3) Method to get Ready-To-Ship Products
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

    //(4) Method to get Weekly Deals
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

    //(5) Method to get small Commodities products
    public ResponsePojo<List<ProductTransaction>> lowPriceCommodities(){
        QProductTransaction qProductTransaction = QProductTransaction.productTransaction;
        //BooleanBuilder predicate = new BooleanBuilder();

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<ProductTransaction> jpaQuery = jpaQueryFactory.selectFrom(qProductTransaction)
                .where(qProductTransaction.quantityOrdered.between(1, 200).and(qProductTransaction.price.between(100,1000)))
                .orderBy(qProductTransaction.price.asc());

        List<ProductTransaction> productList = jpaQuery.fetch();

        ResponsePojo<List<ProductTransaction>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productList);
        responsePojo.setMessage("Small Commodities");

        return responsePojo;
    }


}
