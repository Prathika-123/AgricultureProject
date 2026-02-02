package com.example.AgriProject.dto;

import com.example.AgriProject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUploadResponseDto {
    private String name;
    private String imageUrl;
    private double cost;
    private String description;
    private User user;
}
