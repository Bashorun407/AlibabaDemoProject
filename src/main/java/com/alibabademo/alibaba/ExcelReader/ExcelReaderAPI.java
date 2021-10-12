package com.alibabademo.alibaba.ExcelReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/excel")
public class ExcelReaderAPI {

    @Autowired
    private ExcelFileReader excelFileReader;

    @GetMapping("/readExcelFile")
    public void readExcelFile() {
        excelFileReader.readExcelFile();
    }

}
