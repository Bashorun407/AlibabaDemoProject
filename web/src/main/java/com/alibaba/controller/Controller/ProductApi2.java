package com.alibaba.controller.Controller;

import com.alibaba.domain.Dao.ProductDto;
import com.alibaba.domain.Entity.Product;
import com.alibaba.service.Response.RestResponse.ResponsePojo;
import com.alibaba.service.Service.ProductService2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alibaba")
public class ProductApi2 {

    @Autowired
    private ProductService2 productService2;

    //(1) Method to create a product
    @PostMapping("/createProduct")
    public ResponsePojo<Product> createProduct(@RequestBody ProductDto productDto){
        return productService2.createProduct(productDto);
    }

    //(2) Update product
    @PutMapping("/updateProduct/{Id}")
    public ResponsePojo<Product> productUpdate(@PathVariable Long Id, @RequestBody ProductDto productDto){
        return productService2.productUpdate(Id, productDto);
    }

    //(3) Method to search for product with the following arguments
    @GetMapping("/search")
    public ResponsePojo<Page<Product>> search(@RequestParam(name = "productName", required = false) String productName,
                                              @RequestParam(name = "productNumber", required = false) Long productNumber,
                                              Pageable pageable){
        return productService2.search(productName, productNumber, pageable);
    }


    //(4) Method to Customize Product
    @PutMapping("/customizeProduct/{Id}")
    public ResponsePojo<Product> customizeProduct(@PathVariable Long Id){
        return productService2.customizeProduct(Id);
    }

    //(5) Method to remove product
    @DeleteMapping("/deleteProduct/{Id}")
    public void removeProduct(@PathVariable Long Id){
         productService2.removeProduct(Id);
    }

}
