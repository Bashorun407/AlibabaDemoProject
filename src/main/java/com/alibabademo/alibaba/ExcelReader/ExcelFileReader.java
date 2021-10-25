package com.alibabademo.alibaba.ExcelReader;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Iterator;

@Service
public class ExcelFileReader {


    //The class constructor throws an exception
    public ExcelFileReader() throws IOException {

    }

    //To create the Path to the Excel data
    String filePath = "C:\\Users\\Akinbobola Oluwaseyi\\Desktop\\alibaba\\src\\main\\java\\com\\alibabademo\\alibaba\\ExcelData\\Data1.xlsx";

    //using FILE INPUT STREAM to read the data in the file path
    FileInputStream inputStream = new FileInputStream(filePath);

    //creating a variable to open the workbook in excel
    XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

    //creating an object to specify the sheet the data will be read from
    XSSFSheet sheet = workbook.getSheet("Sheet1");

    public void readExcelFile() {

        //Introducing the Iterator (an alternative to using FOR LOOP)
        Iterator iterator = sheet.iterator();

        //using the WHILE LOOP to loop through each row and its cells
        while (iterator.hasNext()){

            XSSFRow row = (XSSFRow) iterator.next();

            Iterator cellIterator = row.cellIterator();

            while (cellIterator.hasNext()){

                XSSFCell cell = (XSSFCell) cellIterator.next();

                //using SWITCH STATEMENT
                switch (cell.getCellType()){

                    case STRING:{
                        System.out.print(cell.getStringCellValue() + "      |       ");
                        break;
                    }

                    case NUMERIC: {
                        System.out.print(cell.getNumericCellValue() + "      |       ");
                        break;
                    }

                    case BOOLEAN:{
                        System.out.print(cell.getBooleanCellValue() + "      |       ");
                        break;
                    }
                }
            }
            //to print next row on a new line
            System.out.println();
        }

    }


}
