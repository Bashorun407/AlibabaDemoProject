package com.xlsxfilewriter.excelcoder.Service;

import com.xlsxfilewriter.excelcoder.ApiException.ApiException;
import com.xlsxfilewriter.excelcoder.DAO.ShopOwnersDto;
import com.xlsxfilewriter.excelcoder.Entity.ShopOwners;
import com.xlsxfilewriter.excelcoder.Repository.ShopOwnerReppo;
import com.xlsxfilewriter.excelcoder.ResponsePojo.ResponsePojo;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ShopOwnerService {

    @Autowired
    private ShopOwnerReppo shopOwnerReppo;

    public ShopOwnerService() throws IOException {
    }

    //(1) Create Shop with Shop Owner's detail
    public ResponsePojo<ShopOwners> createShopOwners(ShopOwnersDto shopOwnersDto){

        if(!StringUtils.hasText(shopOwnersDto.toString()))
            throw new ApiException("Company Name needed to create Shop...input Company Name");

        if(!StringUtils.hasText(shopOwnersDto.getShopID()))
            throw new ApiException("Shop ID needed to creat Shop...input Shop ID");

        Optional<ShopOwners> shopOwnersOptional = shopOwnerReppo.findShopOwnersByShopID(shopOwnersDto.getShopID());
        if(shopOwnersOptional.isPresent())
            throw new ApiException(String.format("Shop with this ID: %s already exists", shopOwnersDto.getShopID()));

        ShopOwners shopOwners = new ShopOwners();
        shopOwners.setCompanyName(shopOwnersDto.getCompanyName());
        shopOwners.setShopID(shopOwnersDto.getShopID());
        shopOwners.setFirstName(shopOwnersDto.getFirstName());
        shopOwners.setLastName(shopOwnersDto.getLastName());
        shopOwners.setCountry(shopOwnersDto.getCountry());
        shopOwners.setEmailAddress(shopOwnersDto.getEmailAddress());
        shopOwners.setPhoneNumber(shopOwnersDto.getPhoneNumber());

        //Saving the newly created shop in the repository
        shopOwnerReppo.save(shopOwners);

        //Returning the created shop through the Response POJO class
        ResponsePojo<ShopOwners> responsePojo = new ResponsePojo<>();
        responsePojo.setData(shopOwners);
        responsePojo.setMessage("Shop Successfully created!!");

        return responsePojo;
    }




    //(2) Writing Excel Data using FOR LOOP.
    public void writeExcelData() throws IOException {

        //File Location
        String filePath = "C:\\Users\\Akinbobola Oluwaseyi\\Desktop\\data\\newExceller.xlsx";
        //Introduce Excel file requirements/parameters
        FileOutputStream outputStream = new FileOutputStream(filePath);


        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Sheet1");

        //Creating Header values in the specified sheet
        XSSFRow row = sheet.createRow(0);
        //Creating style
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        //Creating font
        XSSFFont font = workbook.createFont();
        font.setFontHeight(20);
        font.setBold(true);
        style.setFont(font);


        XSSFCell cell = row.createCell(0);
        cell.setCellValue("Id");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("Company Name");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("Shop ID");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("First Name");
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("Last Name");
        cell.setCellStyle(style);

        cell = row.createCell(4);
        cell.setCellValue("Phone Number");
        cell.setCellStyle(style);

        cell = row.createCell(5);
        cell.setCellValue("Email_Address");
        cell.setCellStyle(style);

        cell = row.createCell(6);
        cell.setCellValue("Country");
        cell.setCellStyle(style);

        //Making sure the size of Excel cell auto resize to fit the data
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);



        //WRITING THE CELL CONTENTS
        //A list of all the shop owners in the database
        List<ShopOwners> getAllShopOwners = shopOwnerReppo.findAll();
            int rowCount = 1;

            for(ShopOwners shopOwners : getAllShopOwners){
                row = sheet.createRow(rowCount++);

                cell = row.createCell(0);
                cell.setCellValue(shopOwners.getId());

                cell = row.createCell(1);
                cell.setCellValue(shopOwners.getCompanyName());

                cell = row.createCell(2);
                cell.setCellValue(shopOwners.getShopID());

                cell = row.createCell(3);
                cell.setCellValue(shopOwners.getFirstName());

                cell = row.createCell(4);
                cell.setCellValue(shopOwners.getLastName());

                cell = row.createCell(5);
                cell.setCellValue(shopOwners.getPhoneNumber());

                cell = row.createCell(6);
                cell.setCellValue(shopOwners.getEmailAddress());

                cell = row.createCell(6);
                cell.setCellValue(shopOwners.getCountry());

            }

        workbook.write(outputStream);
        workbook.close();
    }

}
