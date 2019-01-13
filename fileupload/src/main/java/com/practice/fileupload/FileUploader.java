package com.practice.fileupload;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FileUploader {
    @GetMapping("/file_upload")
    public String fileUpload() {
        return "Success";
    }
}