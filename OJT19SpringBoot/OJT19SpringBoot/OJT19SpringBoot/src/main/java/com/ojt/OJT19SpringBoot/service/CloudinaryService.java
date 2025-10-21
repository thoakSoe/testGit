package com.ojt.OJT19SpringBoot.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ojt.OJT19SpringBoot.entity.FileEntity;
import com.ojt.OJT19SpringBoot.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private FileRepository fileRepository;

    public String uploadFile(MultipartFile file) throws IOException {
        // ✅ Detect file type
        String contentType = file.getContentType();
        String resourceType;

        if (contentType != null) {
            if (contentType.startsWith("image"))
                resourceType = "image";
            else if (contentType.startsWith("video"))
                resourceType = "video";
            else
                resourceType = "raw"; // pdf, docx, ppt, zip, etc.
        } else {
            resourceType = "raw";
        }

        // ✅ Upload file to Cloudinary
        Map uploadResult = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap(
                        "resource_type", resourceType,
                        "folder", "springboot_uploads",
                        "use_filename", true,
                        "unique_filename", true,
                        "overwrite", false
                )
        );


        String fileUrl = uploadResult.get("secure_url").toString();

        // ✅ Save to database
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(file.getOriginalFilename());
        fileEntity.setFileUrl(fileUrl);
        fileEntity.setFileType(contentType);
        fileEntity.setUploadedAt(LocalDateTime.now());

        fileRepository.save(fileEntity);

        return fileUrl;
    }

    public List<FileEntity> getAllFiles() {
        return fileRepository.findAll();
    }

}
