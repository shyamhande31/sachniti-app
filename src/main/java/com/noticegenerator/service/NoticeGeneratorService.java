package com.noticegenerator.service;

import com.noticegenerator.entity.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Blob;
import java.util.List;

public interface NoticeGeneratorService {

    UserInfo login(String email);

    LogIn loadUserByUserName(String email);

    String getRoleByEmail(String email);

    Integer createUser(UserInfo info);

    TemplateUpload uploadTemplate(MultipartFile file, String templateName);

    String uploadApplication(MultipartFile file, String selectedTemplate);

    List<String> getAllLotNumbers();

    List<String> getAllTemplateName();

    List<String> getAllLotNumbersBydate(String date);

    List<GenerateNoticesDetails> getAllDetailsByLotNumber(String lotNumber);

    void generateNotices(String lotNumber);

    String printAll(String lotNumber);

}
