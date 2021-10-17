package com.xlsxfilewriter.excelcoder.Controller;

import com.xlsxfilewriter.excelcoder.DAO.ShopOwnersDto;
import com.xlsxfilewriter.excelcoder.Entity.ShopOwners;
import com.xlsxfilewriter.excelcoder.ResponsePojo.ResponsePojo;
import com.xlsxfilewriter.excelcoder.Service.ShopOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/excelCoder")
public class ExcelCoderController {

    @Autowired
    private ShopOwnerService shopOwnerService;

    //(1) Create Shop with Shop Owner's detail
    @PostMapping("/createShopOwners")
    public ResponsePojo<ShopOwners> createShopOwners(@RequestBody ShopOwnersDto shopOwnersDto){

       return shopOwnerService.createShopOwners(shopOwnersDto);
    }


    //(2) Writing Headers in the new sheet
    @GetMapping("/writeExcel")
    private void writeExcelData() throws IOException {
        shopOwnerService.writeExcelData();
    }
}
