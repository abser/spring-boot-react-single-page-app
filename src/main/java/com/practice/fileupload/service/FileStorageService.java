package com.practice.fileupload.service;

import com.practice.fileupload.exception.FileStorageException;
import com.practice.fileupload.exception.MyFileNotFoundException;
import com.practice.fileupload.property.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;
    private String fileName;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        this.fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // Check if the file's name contains invalid characters
            if(this.fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(this.fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return this.fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + this.fileName + ". Please try again!", ex);
        }
    }

    public List<String> readCSVFile() {
        BufferedReader fileReader = null;
        try {
            List<String> hashedNRIC = new ArrayList<String>();
            String line = "";
            String filePath = this.fileStorageLocation.toString()+ '/' + this.fileName;
            System.out.println(filePath);
            fileReader = new BufferedReader(new FileReader(filePath));
            // fileReader.readLine(); // Read CSV Header

            while( (line = fileReader.readLine()) != null) {
                // String[] token = line.split(",");
                // if(token.length > 0) {
                //     hashedNRIC.add(token[0]);
                // }
                hashedNRIC.add(line);
            }
            return hashedNRIC;
        } catch(Exception e) {
            throw new MyFileNotFoundException("Not able to read uploaded CSV file from location:" + this.fileStorageLocation);
        } finally {
            try {
				fileReader.close();
			} catch (IOException e) {
				System.out.println("Closing fileReader Error!");
				// e.printStackTrace();
			}
        }

    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }
}
