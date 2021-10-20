package com.xlsxfilewriter.excelcoder.Controller;

import com.xlsxfilewriter.excelcoder.DAO.ShopOwnersDto;
import com.xlsxfilewriter.excelcoder.Entity.ShopOwners;
import com.xlsxfilewriter.excelcoder.ResponsePojo.ResponsePojo;
import com.xlsxfilewriter.excelcoder.Service.ClonedSheet;
import com.xlsxfilewriter.excelcoder.Service.ExcelCopier;
import com.xlsxfilewriter.excelcoder.Service.ExcelIterator;
import com.xlsxfilewriter.excelcoder.Service.ShopOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/excelCoder")
public class ExcelCoderController {

    @Autowired
    private ShopOwnerService shopOwnerService;

    @Autowired
    private ExcelIterator excelIterator;

    @Autowired
    private ExcelCopier excelCopier;

    @Autowired
    private ClonedSheet clonedSheet;

    //(1) Create Shop with Shop Owner's detail
    @PostMapping("/createShopOwners")
    public ResponsePojo<ShopOwners> createShopOwners(@RequestBody ShopOwnersDto shopOwnersDto){

       return shopOwnerService.createShopOwners(shopOwnersDto);
    }


    //(2) Writing Excel Data using FOR LOOP
    @GetMapping("/writeExcel")
    private void writeExcelData() throws IOException {
        shopOwnerService.writeExcelData();
    }


    //(3) Writing Excel Data using ITERATOR and WHILE LOOP
    @GetMapping("/iteratorWrite")
    public void iteratorWrite() throws IOException {
        excelIterator.iteratorWrite();
    }


    //This method is to test the ability to copy from one...it has not worked yet...EmptyFileException "The supplied file was empty (zero bytes long)
    @GetMapping("/copyFromSystem")
    public void copyFromSystemToSystem() throws IOException {
        excelCopier.copyFromSystemToSystem();
    }


    //This method is to copy excel data from a cloned file to a new excel sheet/file...it has not worked yet...EmptyFileException "The supplied file was empty (zero bytes long)
    @GetMapping("/copyFromClonedSheet")
    public void copyFromClonedSheetToSystem() throws IOException {
         clonedSheet.copyFromClonedSheetToSystem();
    }

    }
