package com.ecommerce.project.service;

import com.ecommerce.project.repositories.CategoryRepository;
import com.ecommerce.project.repositories.ProductRepository;
import com.ecommerce.project.service.interfaces.IFileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService implements IFileService {


    private ProductRepository productRepository;

    private CategoryRepository categoryRepository;

    private ModelMapper modelMapper;

    private FileService fileService;

    @Value("${project.image}")
    private String path;

    public FileService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf('.')));
        String filePath = path + File.separator + fileName;

        File folder = new File(path);
        if (!folder.exists())
            folder.mkdir();

        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName;
    }
}
