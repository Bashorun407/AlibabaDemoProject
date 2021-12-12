package com.alibaba.controller.Controller;

import com.alibaba.domain.Dao.ShopOwnersDto;
import com.alibaba.domain.Entity.ShopOwners;
import com.alibaba.service.ExcelWriter.ExcelFileWriter;
import com.alibaba.service.Response.RestResponse.ResponsePojo;
import com.alibaba.service.Service.ShopOwnersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shopOwners")
public class ShopOwnerApi {

    @Autowired
    private ShopOwnersService shopOwnersService;

    @Autowired
    private ExcelFileWriter excelFileWriter;

    //(1) Method to create Shop or Shop Owner detail
    @PostMapping("/createShop")
    public ResponsePojo<ShopOwners> createShopOwner(@RequestBody ShopOwnersDto shopOwnersDto){

        return shopOwnersService.createShopOwner(shopOwnersDto);
    }

    //(2) Method to get all Shop Owners
    @GetMapping("/getAllShops")
    public ResponsePojo<List<ShopOwners>> getAllShopOwners(){

        return shopOwnersService.getAllShopOwners();
    }

    //(3) Method to get a Shop Owner
    @GetMapping("/findShop/{searchItem}")
    //(3) Method to get a Shop Owner
    public ResponsePojo<List<ShopOwners>> findShopOwner(@PathVariable String searchItem){
       return shopOwnersService.findShopOwner(searchItem);
    }

    //(4) Method to update Shop
    @PutMapping("/updateShop")
    public ResponsePojo<ShopOwners> updateShopOwner(@RequestBody ShopOwnersDto shopOwnersDto){

        return shopOwnersService.updateShopOwner(shopOwnersDto);
    }


    @GetMapping("/writeExcel")
    public void writingExcelFile(){

        excelFileWriter.writingExcelFile();
    }

}
