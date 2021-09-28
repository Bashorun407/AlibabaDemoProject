package com.alibabademo.alibaba.Service;

import com.alibabademo.alibaba.Dao.ProductDto;
import com.alibabademo.alibaba.Entity.Product;
import com.alibabademo.alibaba.Entity.QProduct;
import com.alibabademo.alibaba.Exception.ApiException;
import com.alibabademo.alibaba.Repository.ProductReppo;
import com.alibabademo.alibaba.RestResponse.ResponsePojo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.Optional;

@Service
public class ProductService2 {

    @Autowired
    private ProductReppo productReppo;

    @Autowired
    private EntityManager entityManager;

    //(1) Method to create a product
    public ResponsePojo<Product> createProduct(ProductDto productDto){

        if(!StringUtils.hasText(productDto.getProductName()))
            throw new ApiException("Product name is required to create product!!");

//        Optional<Product>  productOptional = productReppo.findById(productDto.getId());
//        productOptional.orElseThrow(()->new ApiException(String.format("The product with this id: %s exists!!", productDto.getId())));

        Product product = new Product();

        product.setProductName(productDto.getProductName());
        product.setProductNumber(new Date().getTime());
        product.setCategory(productDto.getCategory());
        product.setProductType(productDto.getProductType());
        product.setColour(productDto.getColour());
        product.setCustomized(false);
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setAvailableQuantity(productDto.getAvailableQuantity());
        product.setDiscount(productDto.getDiscount());
        product.setDateListed(new Date());
        product.setCompanyName(productDto.getCompanyName());

        productReppo.save(product);
        ResponsePojo<Product> responsePojo = new ResponsePojo<>();
        responsePojo.setData(product);
        responsePojo.setMessage("Product is successfully created.");

        return responsePojo;
    }

    //(2) Update product
    public ResponsePojo<Product> productUpdate(ProductDto productDto){

        if(ObjectUtils.isEmpty(productDto.getId()))
            throw new ApiException("Product Id is empty!! Enter Id");

        if(ObjectUtils.isEmpty(productDto.getProductNumber()))
            throw new ApiException("Product number is required for update!!");

        Optional<Product> productOptional1 = productReppo.findById(productDto.getId());
        productOptional1.orElseThrow(()-> new ApiException(String.format("Product with this Id %s not found!!", productDto.getId())));

        Optional<Product> productOptional2 = productReppo.findByProductNumber(productDto.getProductNumber());
        productOptional2.orElseThrow(()-> new ApiException(String.format("Product with this product-number %s not found!!",
                productDto.getProductNumber())));

        //To check that it is the same's product's Id and productNumber
        Product product1 = productOptional1.get();
        Product product2 = productOptional2.get();

        if(product1 != product2)
            throw new ApiException("The product Id and product number entered are for different products!!");

        //Now the update begins
        Product product = new Product();
        product.setProductName(productDto.getProductName());
        product.setCategory(productDto.getCategory());
        product.setProductType(productDto.getProductType());
        product.setColour(productDto.getColour());
        product.setCustomized(productDto.getCustomized());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setAvailableQuantity(productDto.getAvailableQuantity());
        product.setDateListed(new Date());

        productReppo.save(product);

        ResponsePojo<Product> responsePojo = new ResponsePojo<>();
        responsePojo.setData(product);
        responsePojo.setMessage("Product updated successfully.");

        return responsePojo;
    }


    //(3) Method to search for product with the following arguments
    public ResponsePojo<Page<Product>> search(String productName, Long productNumber, Pageable pageable){

        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();

        //if search was done by product name
        if(StringUtils.hasText(productName))
            predicate.and(qProduct.productName.likeIgnoreCase("%" + productName + "%"));

        //if search was done by productNumber
        if(!ObjectUtils.isEmpty(productNumber))
            predicate.and(qProduct.productNumber.eq(productNumber));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate)
                .orderBy(qProduct.Id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        Page<Product> productPage = new PageImpl<>(jpaQuery.fetch(), pageable, jpaQuery.fetchCount());

        ResponsePojo<Page<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(productPage);
        responsePojo.setMessage("Search successful!!");

        return responsePojo;
    }



    //(5) Method to Customize Product
    public ResponsePojo<Product> customizeProduct(Long Id){
        Optional<Product> productOptional = productReppo.findById(Id);
        productOptional.orElseThrow(()->new ApiException(String.format("Product with this Id %s not found!", Id)));
        Product product = productOptional.get();
        product.setCustomized(true);

        productReppo.save(product);
        ResponsePojo<Product> responsePojo = new ResponsePojo<>();
        responsePojo.setData(product);
        responsePojo.setMessage("Product is Customized!");

        return responsePojo;
    }

    //(6) Method to remove product
    public void removeProduct(Long Id){
        Optional<Product> productOptional = productReppo.findById(Id);
        productOptional.orElseThrow(()-> new ApiException(String.format("Product with this Id %s not found!!", Id)));

        productReppo.deleteById(Id);
    }


}
