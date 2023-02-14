package com.noticegenerator.controller;

import com.noticegenerator.entity.GenerateNoticesDetails;
import com.noticegenerator.entity.LogIn;
import com.noticegenerator.entity.TemplateUpload;
import com.noticegenerator.entity.UserInfo;
import com.noticegenerator.service.NoticeGeneratorService;
import com.noticegenerator.service.impl.NoticeGerneratorServiceImpl;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping
public class NoticeController {
    @Autowired
    private NoticeGeneratorService service;

    public NoticeController(){
        service=new NoticeGerneratorServiceImpl();
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @GetMapping("/login/{email}")
    public UserInfo login(@PathVariable String email){
        return service.login(email);
    }

    @GetMapping("/getRole")
    public String getRole(@RequestParam String email){
        return service.getRoleByEmail(email);
    }

    @PostMapping("/createUser")
    public Integer createUser(@RequestBody UserInfo info){
        return service.createUser(info);
    }

    @PostMapping(value="/templateUpload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public TemplateUpload templateUpload(@RequestParam MultipartFile file, @RequestParam String extra){
        return service.uploadTemplate(file,extra);
    }

    @PostMapping(value="/applicationUpload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String applicationUpload(@RequestParam MultipartFile file, @RequestParam String extra) throws IOException {
        return service.uploadApplication(file,extra);
    }

    @GetMapping("/getLotNumbers")
    public List<String> getAllLotNumbers(){
        return service.getAllLotNumbers();
    }

    @GetMapping("/getLotNumbersByDate/{date}")
    public List<String> getAllLotNumbersByDate(@PathVariable String  date){
        return service.getAllLotNumbersBydate(date);
    }

    @GetMapping("/getAllTemplateName")
    public List<String> getAllTemplateName(){
        return service.getAllTemplateName();
    }

    @GetMapping("/getAllLoanNumbers")
    public List<GenerateNoticesDetails> getAllLoanNumbers(@RequestParam String lotNumber){
        return service.getAllDetailsByLotNumber(lotNumber);
    }

    @GetMapping("/generateNotices")
    public Integer generateNotices(@RequestParam String lotNumber){
         service.generateNotices(lotNumber);
         return 1;
    }

    @GetMapping("/getNotice/{fileName}")
    public ResponseEntity getNoticeByFileName(@PathVariable String fileName) {
        Path path = Paths.get("E:\\pdfs\\Sun Feb 05 2023_BANK_001\\"+fileName);
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/printAll/{lotNumber}")
    public ResponseEntity<InputStreamResource> printAllNotices(@PathVariable String lotNumber) {
        String folderName=service.printAll(lotNumber);
        String filePath = "E:\\pdfs\\"+folderName+"\\";
        String fileName = "merged.pdf";
        File file = new File(filePath+fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-disposition", "inline;filename=" +fileName);

        InputStreamResource resource = null;
        try {
            resource = new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);
    }
}
