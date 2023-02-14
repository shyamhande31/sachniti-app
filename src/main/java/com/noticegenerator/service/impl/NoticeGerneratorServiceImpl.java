package com.noticegenerator.service.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.noticegenerator.dao.NoticeGeneratorDAO;
import com.noticegenerator.dao.impl.NoticeGeneratorDAOImpl;
import com.noticegenerator.entity.*;
import com.noticegenerator.service.NoticeGeneratorService;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class NoticeGerneratorServiceImpl implements NoticeGeneratorService {

    private NoticeGeneratorDAO dao;

    public NoticeGerneratorServiceImpl() {
        dao = new NoticeGeneratorDAOImpl();
    }

    @Override
    public UserInfo login(String email) {
        return dao.login(email);
    }

    @Override
    public LogIn loadUserByUserName(String email) {
        return dao.loadUserByUserName(email);
    }

    @Override
    public String getRoleByEmail(String email) {
        return dao.getRoleByEmail(email);
    }

    @Override
    public Integer createUser(UserInfo info) {
        return dao.createUser(info);
    }

    @Override
    public TemplateUpload uploadTemplate(MultipartFile file, String templateName) {
        TemplateUpload template = new TemplateUpload();
        template.setTemplateName(templateName);
        template.setSelectedTemplateLocation(file);
        return dao.uploadTemplate(template);
    }

    @Override
    public String uploadApplication(MultipartFile file, String selectedTemplate) {
        List<ApplicationUpload> lot = new ArrayList<>();
        InputStream fin = null;
        List<String> columnHeaderList = new ArrayList<>();
        try {
            fin = file.getInputStream();
            XSSFWorkbook workbook = new XSSFWorkbook(fin);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            int i = 0;
            while (rowIterator.hasNext()) {
                ApplicationUpload applicationUpload = new ApplicationUpload();
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (i == 0) {
                        switch (cell.getCellType()) {
                            case STRING:
                                columnHeaderList.add(cell.getStringCellValue());
                                break;
                        }
                    } else {
                        String todayDate=new SimpleDateFormat("E MMM dd yyyy HH:mm").format(new java.util.Date());
                        applicationUpload.setLotNumber(todayDate + "_BANK_" + "001");
                        applicationUpload.setUploadTime(Date.valueOf("2023-01-23"));
                        switch (cell.getCellType()) {
                            case STRING:
                                if (columnHeaderList.get(cell.getColumnIndex()).equals("REFERENCE NO."))
                                    applicationUpload.setReferenceNumber(cell.getStringCellValue());
                                if (columnHeaderList.get(cell.getColumnIndex()).contains("LOAN NO"))
                                    applicationUpload.setLoanNo(cell.getStringCellValue());
                                if (columnHeaderList.get(cell.getColumnIndex()).equals("CUSTOMER NAME"))
                                    applicationUpload.setCustomerName(cell.getStringCellValue());
                                if (columnHeaderList.get(cell.getColumnIndex()).equals("PRODUCT"))
                                    applicationUpload.setProduct(cell.getStringCellValue());
                                if (columnHeaderList.get(cell.getColumnIndex()).equals("PROD GROUP"))
                                    applicationUpload.setProdGroup(cell.getStringCellValue());
                                if (columnHeaderList.get(cell.getColumnIndex()).equals("Language"))
                                    applicationUpload.setLanguage(cell.getStringCellValue());
                                if (columnHeaderList.get(cell.getColumnIndex()).equals("Notice No."))
                                    applicationUpload.setNoticeNo(cell.getStringCellValue());
                                if (columnHeaderList.get(cell.getColumnIndex()).contains("ADDRESS1"))
                                    applicationUpload.setAddress1(cell.getStringCellValue());
                                if (columnHeaderList.get(cell.getColumnIndex()).contains("ADDRESS2"))
                                    applicationUpload.setAddress2(cell.getStringCellValue());
                                if (columnHeaderList.get(cell.getColumnIndex()).contains("ADDRESS3"))
                                    applicationUpload.setAddress3(cell.getStringCellValue());
                                if (columnHeaderList.get(cell.getColumnIndex()).contains("ADDRESS4"))
                                    applicationUpload.setAddress4(cell.getStringCellValue());
                                if (columnHeaderList.get(cell.getColumnIndex()).contains("CITY"))
                                    applicationUpload.setCity(cell.getStringCellValue());
                                if (columnHeaderList.get(cell.getColumnIndex()).contains("STATE"))
                                    applicationUpload.setState(cell.getStringCellValue());
                                if (columnHeaderList.get(cell.getColumnIndex()).contains("EMAIL ID"))
                                    applicationUpload.setEmailId(cell.getStringCellValue());
                                if (columnHeaderList.get(cell.getColumnIndex()).contains("TO BE ISSUED VIA"))
                                    applicationUpload.setToBeIssuedVia(cell.getStringCellValue());
                                if (columnHeaderList.get(cell.getColumnIndex()).contains("ADD TYPE"))
                                    applicationUpload.setAddType(cell.getStringCellValue());
                                if (columnHeaderList.get(cell.getColumnIndex()).contains("VENDOR"))
                                    applicationUpload.setVendor(cell.getStringCellValue());
                                if (columnHeaderList.get(cell.getColumnIndex()).contains("BRANCH"))
                                    applicationUpload.setBranch(cell.getStringCellValue());
                                if (columnHeaderList.get(cell.getColumnIndex()).contains("REGION"))
                                    applicationUpload.setRegion(cell.getStringCellValue());
                                break;
                            case NUMERIC:
                                if(DateUtil.isCellDateFormatted(cell)){
                                    SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
                                    if (columnHeaderList.get(cell.getColumnIndex()).equals("Notice Date"))
                                        applicationUpload.setNoticeDate(dateFormat.format(cell.getDateCellValue()));
                                    if (columnHeaderList.get(cell.getColumnIndex()).equals("Notice SEND Date"))
                                        applicationUpload.setNoticeSendDate(dateFormat.format(cell.getDateCellValue()));
                                }else{
                                    if (columnHeaderList.get(cell.getColumnIndex()).equals("EMI AMT"))
                                        applicationUpload.setEmiAmt(String.valueOf((int) cell.getNumericCellValue()));
                                    if (columnHeaderList.get(cell.getColumnIndex()).contains("ZIP CODE"))
                                        applicationUpload.setZipCode(String.valueOf((int) cell.getNumericCellValue()));
                                }
                        }
                    }
                }
                if (i != 0) {
                    lot.add(applicationUpload);
                }
                i++;
            }
            fin.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dao.uploadApplication(lot);
    }

    @Override
    public List<String> getAllLotNumbers() {
        return dao.getAllLotNumbers();
    }

    @Override
    public List<String> getAllTemplateName() {
        return dao.getAllTemplateName();
    }

    @Override
    public List<String> getAllLotNumbersBydate(String date) {
        List<String> lotNumbers=dao.getAllLotNumbers();
        List<String> newList=new ArrayList<>();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        try {
            for(String num:lotNumbers){
                if(date.equals(dateFormat.format(new SimpleDateFormat("E MMM dd yyyy HH:mm").parse(num.substring(0,21))))){
                    newList.add(num);
                }
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return newList;
    }

    @Override
    public List<GenerateNoticesDetails> getAllDetailsByLotNumber(String lotNumber) {
        return dao.getAllDetailsByLotNumber(lotNumber);
    }

    @Override
    public void generateNotices(String lotNumber) {
        String folderName=lotNumber.substring(0,15)+lotNumber.substring(21);
        File folder=new File("E:\\pdfs\\"+folderName);
        folder.mkdir();
        List<ApplicationUpload> details = dao.getLotDetails(lotNumber);
        InputStream stream = dao.getLatestTemplate("AUTO SUITE");
        XWPFDocument document = null;
        try {
            document = new XWPFDocument(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        for(ApplicationUpload detail:details){
            try {
                String arr[]=detail.getReferenceNumber().split("/");
                String referenceNumber="";
                for(int i=0;i<arr.length;i++){
                    if(i==arr.length-1)
                        referenceNumber+=arr[i];
                    else
                        referenceNumber+=arr[i]+"_";
                }
                folder=new File("E:\\pdfs\\"+folderName+"\\"+referenceNumber);
                folder.mkdir();
                PDDocument pdfdoc = new PDDocument();
                pdfdoc.addPage(new PDPage());
                pdfdoc.save("E:\\pdfs\\"+folderName+"\\"+referenceNumber+"\\"+referenceNumber+".pdf");
                pdfdoc.close();
                Document doc=new Document();
                PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("E:\\pdfs\\"+folderName+"\\"+referenceNumber+"\\"+referenceNumber+".pdf" ));
                doc.open();
                for (XWPFParagraph para : paragraphs) {
                    String text=para.getText();
                        if (text.contains("< REFERENCE NO. >")) {
                            text=text.replace("< REFERENCE NO. >", detail.getReferenceNumber());
                        }
                        if (text.contains("< Notice Date>")) {
                            text=text.replace("< Notice Date>", detail.getNoticeDate());
                        }
                        if (text.contains("<Notice Date>")) {
                            text=text.replace("<Notice Date>", detail.getNoticeDate());
                        }
                        if (text.contains("<Customer Name>")) {
                            text=text.replace("<Customer Name>", detail.getCustomerName());
                        }
                        if (text.contains("<Address1>")) {
                            text=text.replace("<Address1>", detail.getAddress1()!=null?detail.getAddress1():"");
                        }
                        if (text.contains("<Address2>")) {
                            text=text.replace("<Address2>", detail.getAddress2()!=null?detail.getAddress2():"");
                        }
                        if (text.contains("<Address 3 >")) {
                            text=text.replace("<Address 3 >", detail.getAddress3()!=null?detail.getAddress3():"");
                        }
                        if (text.contains("<Address4>")) {
                            text=text.replace("<Address4>", detail.getAddress4()!=null?detail.getAddress4():"");
                        }
                        if (text.contains("<City>")) {
                            text=text.replace("<City>", detail.getCity());
                        }
                        if (text.contains("<ZipCode>")) {
                            text=text.replace("<ZipCode>", detail.getZipCode());
                        }
                        if (text.contains("<State>")) {
                            text=text.replace("<State>", detail.getState());
                        }
                        if (text.contains("<Product>")) {
                            text=text.replace("<Product>", detail.getProduct());
                        }
                        if (text.contains("<Loan No>")) {
                            text=text.replace("<Loan No>", detail.getLoanNo());
                        }
                        if (text.contains("<EMI Amount>")) {
                            text=text.replace("<EMI Amount>", detail.getEmiAmt());
                        }
                        doc.add(new Paragraph(text));
                }
                doc.close();
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (DocumentException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String printAll(String lotNumber){
        String folderName=lotNumber.substring(0,15)+lotNumber.substring(21);
        List<GenerateNoticesDetails> list=dao.getAllDetailsByLotNumber(lotNumber);
        PDFMergerUtility obj = new PDFMergerUtility();
        obj.setDestinationFileName("E\\pdfs\\"+folderName+"\\merged.pdf");
        try{
            for(GenerateNoticesDetails info:list){
                String arr[] = info.getReferenceNumber().split("/");
                String referenceNumber = "";
                for (int i = 0; i < arr.length; i++) {
                    if (i == arr.length - 1)
                        referenceNumber += arr[i];
                    else
                        referenceNumber += arr[i] + "_";
                }

                File file = new File("E:\\pdfs\\" + folderName + "\\" + referenceNumber + ".pdf");
                obj.addSource(file);
            }
            obj.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
        }catch (FileNotFoundException e){
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return folderName;
    }

}
