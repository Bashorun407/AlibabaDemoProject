package com.xlsxfilewriter.excelcoder.Service;


import com.xlsxfilewriter.excelcoder.ApiException.ApiException;
import com.xlsxfilewriter.excelcoder.Entity.ShopOwners;
import com.xlsxfilewriter.excelcoder.Repository.ShopOwnerReppo;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


//This is just to write the same method to write excel file using iterator instead of for loop
@Service
public class ExcelIterator {

    @Autowired
    private ShopOwnerReppo shopOwnerReppo;

    // Writing Excel Data using ITERATOR and WHILE LOOP
    public void iteratorWrite() throws IOException {

        String filePath = "C:\\Users\\Akinbobola Oluwaseyi\\Desktop\\data\\ExcelIterator.xlsx";
        FileOutputStream outputStream = new FileOutputStream(filePath);

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Sheet1");

        //Creating header files in the Excel Sheet
        XSSFRow row = sheet.createRow(0);

        //Setting the Styles in the Header
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.BRIGHT_GREEN1.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        //Setting the font type
        XSSFFont font = workbook.createFont();
        font.setFontHeight(18);
        font.setBold(true);
        style.setFont(font);

        //Setting the contents of the cell
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("Id");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("Company Name");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("Shop ID");
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("First Name");
        cell.setCellStyle(style);

        cell = row.createCell(4);
        cell.setCellValue("Last Name");
        cell.setCellStyle(style);

        cell = row.createCell(5);
        cell.setCellValue("Phone Number");
        cell.setCellStyle(style);

        cell = row.createCell(6);
        cell.setCellValue("Email_Address");
        cell.setCellStyle(style);

        cell = row.createCell(7);
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
        sheet.autoSizeColumn(7);

        //Now to use Iterator Object to fill cells
        //List of all the data in the database
        List<ShopOwners> shopOwnersList = shopOwnerReppo.findAll();
        if(shopOwnersList.isEmpty())
            throw new ApiException("Nothing in the List");

        Iterator iterator = shopOwnersList.iterator();

        int rowCount = 1;
        int listCount =0;//gives counts to the elements of the ShopOwnersList
        int max = shopOwnersList.size();

        while(iterator.hasNext() && listCount<max){

            row = sheet.createRow(rowCount++);
            ShopOwners shopOwners = shopOwnersList.get(listCount++);

            cell = row.createCell(0);
            cell.setCellValue((shopOwners.getId()));
            
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

            cell = row.createCell(7);
            cell.setCellValue(shopOwners.getCountry());

        }


        //writing the data into the outputstream object
        workbook.write(outputStream);

        //it is a good practice to close the workbook when one is done
        workbook.close();
    }


}
