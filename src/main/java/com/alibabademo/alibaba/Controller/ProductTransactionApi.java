package com.alibabademo.alibaba.Controller;

import com.alibabademo.alibaba.Dao.ProductTransactionDto;
import com.alibabademo.alibaba.Entity.Product;
import com.alibabademo.alibaba.Entity.ProductTransaction;
import com.alibabademo.alibaba.RestResponse.ResponsePojo;
import com.alibabademo.alibaba.Service.ProductTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alibaba")

public class ProductTransactionApi {

    @Autowired
    private ProductTransactionService productTransactionService;

    //(1) The methods stated here are to engage, input and increment certain features of the ProductTransaction table
    @PutMapping("/clientTransaction/{Id}")
    public ResponsePojo<ProductTransaction> clientTransaction(@PathVariable Long Id, @RequestBody ProductTransactionDto productTranDto){

        return  productTransactionService.clientTransaction(Id, productTranDto);
    }

    //(2) Method to get Ready-To-Ship Products
    @GetMapping("/readyToShipProducts")
    public ResponsePojo<List<ProductTransaction>> readyToShipProducts(){
        return productTransactionService.readyToShipProducts();
    }

    //(3) Method to get Weekly Deals
    @GetMapping("/weeklyDeals")
    public ResponsePojo<List<ProductTransaction>> weeklyDeals(){
        return productTransactionService.weeklyDeals();
    }

    //(4) Method to get small Commodities products
    @GetMapping("/lowPriceCommodity")
    public ResponsePojo<List<ProductTransaction>> lowPriceCommodities(){
        return productTransactionService.lowPriceCommodities();
    }


}
