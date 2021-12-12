package com.alibaba.service.ExcelWriter;

import com.alibaba.domain.Entity.ShopOwners;
import com.alibaba.repository.Repository.ShopOwnerReppo;
import com.alibaba.service.Exception.Exception.ApiRequestException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.FileOutputStream;
import java.util.List;

@Service
public class ExcelFileWriter {

    @Autowired
    private ShopOwnerReppo shopOwnerReppo;

    public void writingExcelFile(){

        try{

            //List of Shop Owners in the database
            List<ShopOwners> shopOwnersList = shopOwnerReppo.findAll();

            String filePath = "C:\\Users\\Akinbobola Oluwaseyi\\Desktop\\Java Projects\\alibaba\\service\\src\\main\\java\\com\\alibaba\\service\\ExcelWriter\\ShopOwners.xlsx";

            if(!StringUtils.hasText(filePath))
                throw new ApiRequestException("File Path is invalid!!");


            FileOutputStream outputStream = new FileOutputStream(filePath);

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Sheet1");

            //Setting the font
            XSSFFont font = workbook.createFont();
            font.setBold(true);
            font.setFontHeight(16);

            //Setting the style of the cells
            CellStyle style = workbook.createCellStyle();
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setFillForegroundColor(IndexedColors.TURQUOISE.getIndex());
            style.setFont(font);

            //To Create Header Rows
            XSSFRow row = sheet.createRow(0);

            //To create cells in the row of the Header
            XSSFCell cell = row.createCell(0);
            cell.setCellValue("Id");
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue("Shop Name");
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue("Shop Number");
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue("Shop Owner First Name");
            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue("Shop Owner Last Name");
            cell.setCellStyle(style);

            cell = row.createCell(5);
            cell.setCellValue("Phone Number");
            cell.setCellStyle(style);

            cell = row.createCell(6);
            cell.setCellValue("Email Address");
            cell.setCellStyle(style);

            cell = row.createCell(7);
            cell.setCellValue("Date Created");
            cell.setCellStyle(style);

            cell = row.createCell(8);
            cell.setCellValue("Update Status");
            cell.setCellStyle(style);

            cell = row.createCell(9);
            cell.setCellValue("Date Updated");
            cell.setCellStyle(style);

            //Using For Loop to read the cell values for each of the rows and columns
            int rowCount = 1;
            for(ShopOwners shopOwners : shopOwnersList){

                //to Autosize columns
                sheet.autoSizeColumn(rowCount);

                //Creating new row in each iteration
                row = sheet.createRow(rowCount++);

                //Creating new cells in each row
                cell = row.createCell(0);
                cell.setCellValue(shopOwners.getId());

                cell = row.createCell(1);
                cell.setCellValue(shopOwners.getShopName());

                cell = row.createCell(2);
                cell.setCellValue(shopOwners.getShopNumber());

                cell = row.createCell(3);
                cell.setCellValue(shopOwners.getShopOwnerFirstName());

                cell = row.createCell(4);
                cell.setCellValue(shopOwners.getShopOwnerLastName());

                cell = row.createCell(5);
                cell.setCellValue(shopOwners.getPhoneNumber());

                cell = row.createCell(6);
                cell.setCellValue(shopOwners.getEmailAddress());

                cell = row.createCell(7);
                cell.setCellValue(shopOwners.getDateCreated());

//                cell = row.createCell(8);
//                cell.setCellValue(shopOwners.getUpdatedStatus());

                cell = row.createCell(9);
                cell.setCellValue(shopOwners.getDateUpdated());

                }

            //Writing the data into the file location on OUTPUT STREAM
            workbook.write(outputStream);
            outputStream.close();
            workbook.close();

            System.out.println("Excel File Successfully Written");
        }

        catch (Exception e){

            e.printStackTrace();
        }
    }
}
