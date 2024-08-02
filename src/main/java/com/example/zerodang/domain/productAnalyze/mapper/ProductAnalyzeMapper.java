package com.example.zerodang.domain.productAnalyze.mapper;

import com.example.zerodang.domain.product.entity.Product;
import com.example.zerodang.domain.productAnalyze.dto.response.ProductAnalyzeResponseDTO;
import com.example.zerodang.domain.productAnalyze.entity.ProductAnalyze;
import com.example.zerodang.domain.sweetener.entity.Sweetener;
import com.example.zerodang.domain.user.dto.request.UserRequestDTO;
import com.example.zerodang.domain.user.dto.response.UserResponseDTO;
import com.example.zerodang.domain.user.entity.User;
import com.example.zerodang.domain.user.entity.UserStatus;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

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
        return ProductAnalyzeResponseDTO.ProductAnalyzeSaveDTO.builder()
                .productAnalyzeId(productAnalyze.getProductAnalyzeId())
                .userId(productAnalyze.getUser().getUserId())
                .userName(productAnalyze.getUser().getUserName())
                .productId(productAnalyze.getProduct().getProductId())
                .productName(productAnalyze.getProduct().getProductName())
                .build();
    }

    public ProductAnalyzeResponseDTO.ProductAnalyzeFindDetailDTO toProductAnalyzeFindDetailDTO(Product product) {
        return ProductAnalyzeResponseDTO.ProductAnalyzeFindDetailDTO.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productCategory(product.getProductCategory())
                .productImageUrl(product.getThumbnail())
                .productMl(product.getProductMl())
                .carbohydrateG(product.getCarbohydrateG())
                .sugarG(product.getSugarG())
                .proteinG(product.getProteinG())
                .fatG(product.getFatG())
                .saturatedFatG(product.getSaturatedFatG())
                .transFatG(product.getTransFatG())
                .cholesterolG(product.getCholesterolG())
                .sodiumG(product.getSodiumG())
                .build();
    }

    public ProductAnalyzeResponseDTO.ProductAnalyzeResultDTO toProductAnalyzeResultDTO(Product product1, Product product2, List<Sweetener> sweeteners, String resultComment) {
        List<ProductAnalyzeResponseDTO.ProductAnalyzeFindDetailDTO> productAnalyzeFindDetailDTOList = List.of(
                toProductAnalyzeFindDetailDTO(product1),
                toProductAnalyzeFindDetailDTO(product2)
        );

        return ProductAnalyzeResponseDTO.ProductAnalyzeResultDTO.builder()
                .productAnalyzeFindDetailDTOList(productAnalyzeFindDetailDTOList)
                .sweetenerList(sweeteners)
                .resultComment(resultComment)
                .build();
    }
}
