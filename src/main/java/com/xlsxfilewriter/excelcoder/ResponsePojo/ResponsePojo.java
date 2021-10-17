package com.xlsxfilewriter.excelcoder.ResponsePojo;

import lombok.Data;

@Data
public class ResponsePojo <T>{

    String message;
    T data;
    int status =200;
    Boolean success = true;

}
