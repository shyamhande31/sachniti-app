package com.noticegenerator.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public class TemplateUpload {
    @Getter @Setter
    private Integer templateId=0;
    @Getter @Setter
    private String templateName;
    @Getter @Setter
    private MultipartFile selectedTemplateLocation;
}
