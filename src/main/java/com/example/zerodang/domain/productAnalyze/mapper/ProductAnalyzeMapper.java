package com.example.zerodang.domain.productAnalyze.mapper;

import com.example.zerodang.domain.product.entity.Product;
import com.example.zerodang.domain.productAnalyze.dto.response.ProductAnalyzeResponseDTO;
import com.example.zerodang.domain.productAnalyze.entity.ProductAnalyze;
import com.example.zerodang.domain.user.dto.request.UserRequestDTO;
import com.example.zerodang.domain.user.dto.response.UserResponseDTO;
import com.example.zerodang.domain.user.entity.User;
import com.example.zerodang.domain.user.entity.UserStatus;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductAnalyzeMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public ProductAnalyze toProductAnalyzeEntity(Product product, User user) {
        return ProductAnalyze.builder()
                .product(product)
                .user(user)
                .build();
    }

    public ProductAnalyzeResponseDTO.ProductAnalyzeSaveDTO toProductAnalyzeSaveResDTO(ProductAnalyze productAnalyze) {
        return modelMapper.map(productAnalyze, ProductAnalyzeResponseDTO.ProductAnalyzeSaveDTO.class);
    }
}
