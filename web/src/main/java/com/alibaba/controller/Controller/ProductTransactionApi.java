package com.alibaba.controller.Controller;

import com.alibaba.domain.Dao.ProductTransactionDto;
import com.alibaba.domain.Entity.ProductTransaction;
import com.alibaba.service.Response.RestResponse.ResponsePojo;
import com.alibaba.service.Service.ProductTransactionService;
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

    //(2) Method to conduct transaction
    @PutMapping("/transact/{Id}/{amountPaid}")
    public ResponsePojo< ProductTransaction> transactProduct(@PathVariable Long Id, @RequestBody ProductTransactionDto productTransactionDto, @PathVariable Long amountPaid ){
        return productTransactionService.transactProduct(Id, productTransactionDto, amountPaid);
    }


    //(3) Method to get Ready-To-Ship Products
    @GetMapping("/readyToShipProducts")
    public ResponsePojo<List<ProductTransaction>> readyToShipProducts(){
        return productTransactionService.readyToShipProducts();
    }

    //(4) Method to get Weekly Deals
    @GetMapping("/weeklyDeals")
    public ResponsePojo<List<ProductTransaction>> weeklyDeals(){
        return productTransactionService.weeklyDeals();
    }

    //(5) Method to get small Commodities products
    @GetMapping("/lowPriceCommodity")
    public ResponsePojo<List<ProductTransaction>> lowPriceCommodities(){
        return productTransactionService.lowPriceCommodities();
    }

}
