package com.noticegenerator.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
public class ApplicationUpload {
    @Getter @Setter
    private Integer lotId=0;
    @Getter @Setter
    private String lotNumber;
    @Getter @Setter
    private Date uploadTime;
    @Getter @Setter
    private String referenceNumber;
    @Getter @Setter
    private String loanNo;
    @Getter @Setter
    private String customerName;
    @Getter @Setter
    private String product;
    @Getter @Setter
    private String prodGroup;
    @Getter @Setter
    private String emiAmt;
    @Getter @Setter
    private String noticeDate;
    @Getter @Setter
    private String noticeSendDate;
    @Getter @Setter
    private String language;
    @Getter @Setter
    private String noticeNo;
    @Getter @Setter
    private String address1;
    @Getter @Setter
    private String address2;
    @Getter @Setter
    private String address3;
    @Getter @Setter
    private String address4;
    @Getter @Setter
    private String city;
    @Getter @Setter
    private String zipCode;
    @Getter @Setter
    private String state;
    @Getter @Setter
    private String emailId;
    @Getter @Setter
    private String toBeIssuedVia;
    @Getter @Setter
    private String addType;
    @Getter @Setter
    private String vendor;
    @Getter @Setter
    private String branch;
    @Getter @Setter
    private String region;
}
