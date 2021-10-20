package com.xlsxfilewriter.excelcoder.Service;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

@Service
public class ExcelCopier {

    //This method is to test the ability to copy from one
    public void copyFromSystemToSystem() throws IOException {

        //File path to copy from
        String filePathCopiedFrom = "C:\\Users\\Akinbobola Oluwaseyi\\Desktop\\data\\ExcelFiles.xlsx ";
        FileInputStream inputStream = new FileInputStream(filePathCopiedFrom);

        String filePathCopiedTo = " C:\\Users\\Akinbobola Oluwaseyi\\Desktop\\data\\SystemExcel.xlsx";
        FileOutputStream outputStream = new FileOutputStream(filePathCopiedFrom);

        //Creating a workbook
        XSSFWorkbook workbook1 = new XSSFWorkbook(filePathCopiedFrom);
        XSSFWorkbook workbook2 = new XSSFWorkbook(filePathCopiedTo);

        //Creating a sheet in the workbook
        XSSFSheet sheet1 = workbook1.getSheet("Sheet2");
        XSSFSheet sheet2 = workbook2.createSheet("Sheet1");


        Iterator iterator1 = sheet1.iterator();
        Iterator iterator2 = sheet2.iterator();

        while(iterator1.hasNext()) {
            //Create rows in the sheet
            XSSFRow row1 = (XSSFRow) iterator1.next();
            XSSFRow row2 = (XSSFRow) iterator2.next();

            Iterator cellIterator1 = row1.cellIterator();
            Iterator cellIterator2 = row2.cellIterator();
            while(cellIterator1.hasNext()) {
                //Using Iterator to iterate throw the cells on each row
                XSSFCell cell1 = (XSSFCell) cellIterator1.next();
                XSSFCell cell2 = (XSSFCell) cellIterator2.next();

                //using SWITCH STATEMENT to get different datatypes
                switch (cell1.getCellType()){

                    case STRING: {
                        cell2.setCellValue(cell1.getStringCellValue());
                        break;
                    }

                    case NUMERIC: {
                        cell2.setCellValue(cell2.getNumericCellValue());
                    }

                    case BOOLEAN: {
                        cell2.setCellValue(cell1.getBooleanCellValue());
                    }

                }
            }

        }


        workbook2.write(outputStream);

        //Closing the two WORKBOOKS
        workbook1.close();
        workbook2.close();


    }
}
