package com.example.AgriProject.service;

import com.example.AgriProject.dto.ProductUploadDto;
import com.example.AgriProject.dto.ProductUploadResponseDto;
import com.example.AgriProject.entity.Product;
import com.example.AgriProject.entity.User;
import com.example.AgriProject.repository.ProductRepository;
import com.example.AgriProject.repository.UserRepository;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public ResponseEntity<ProductUploadResponseDto> uploadProduct(ProductUploadDto productUploadDto) throws Exception{
        System.out.print(productUploadDto.getUserId());
        String filename=System.currentTimeMillis()+"_"+productUploadDto.getImage().getOriginalFilename();
        Path uploadPath= Paths.get("uploads");

        if(!Files.exists(uploadPath))
            Files.createDirectories(uploadPath);

        Path filePath=uploadPath.resolve(filename);
        Files.copy(productUploadDto.getImage().getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);

        User user=userRepository.findById(productUploadDto.getUserId()).orElseThrow(()->
                new IllegalArgumentException("User not found"));
        Product product=Product.builder()
                        .name(productUploadDto.getName())
                        .imageUrl("/uploads/"+filename)
                        .cost(productUploadDto.getCost())
                        .description(productUploadDto.getDescription())
                        .user(user)
                       .build();
        Product saved=productRepository.save(product);

        ProductUploadResponseDto productUploadResponseDto=modelMapper.map(saved,ProductUploadResponseDto.class);

        return ResponseEntity.ok(productUploadResponseDto);
    }

    public List<Product> getProducts(Long userId) {
        return productRepository.findByUserId(userId);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
