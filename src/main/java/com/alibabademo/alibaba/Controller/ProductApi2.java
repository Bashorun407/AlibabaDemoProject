package com.alibabademo.alibaba.Controller;

import com.alibabademo.alibaba.Dao.ProductDto;
import com.alibabademo.alibaba.Entity.Product;
import com.alibabademo.alibaba.RestResponse.ResponsePojo;
import com.alibabademo.alibaba.Service.ProductService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alibaba")
public class ProductApi2 {

    @Autowired
    private ProductService2 productService2;

    //(1) Method to create a product
    public ResponsePojo<Product> createProduct(ProductDto productDto){
        return productService2.createProduct(productDto);
    }

    //(2) Update product
    public ResponsePojo<Product> productUpdate(ProductDto productDto){
        return productService2.productUpdate(productDto);
    }

    //(3) Method to search for product with the following arguments
    public ResponsePojo<Page<Product>> search(String productName, String companyName, Long productNumber, Pageable pageable){
        return productService2.search(productName, companyName, productNumber, pageable);
    }

    //(4) The methods stated here are to engage, input and increment certain features of the Product table
    public ResponsePojo<Long> clientTransaction(Long Id, Long numberOrdered){
        return  productService2.clientTransaction(Id, numberOrdered);
    }

    //(5) Method to remove product
    public void removeProduct(Long Id){
         productService2.removeProduct(Id);
    }

}
