package com.alibabademo.alibaba.Controller;

import com.alibabademo.alibaba.Dao.ProductDto;
import com.alibabademo.alibaba.Entity.Product;
import com.alibabademo.alibaba.RestResponse.ResponsePojo;
import com.alibabademo.alibaba.Service.ProductService2;
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
    @PutMapping("/updateProduct")
    public ResponsePojo<Product> productUpdate(@RequestBody ProductDto productDto){
        return productService2.productUpdate(productDto);
    }

    //(3) Method to search for product with the following arguments
    @GetMapping("/search")
    public ResponsePojo<Page<Product>> search(@RequestParam(name = "productName", required = false) String productName,
                                              @RequestParam(name = "productNumber", required = false) Long productNumber,
                                              Pageable pageable){
        return productService2.search(productName, productNumber, pageable);
    }


    //(5) Method to Customize Product
    @PutMapping("/customizeProduct/{Id}")
    public ResponsePojo<Product> customizeProduct(@PathVariable Long Id){
        return productService2.customizeProduct(Id);
    }

    //(6) Method to remove product
    @DeleteMapping("/deleteProduct/{Id}")
    public void removeProduct(@PathVariable Long Id){
         productService2.removeProduct(Id);
    }

}
