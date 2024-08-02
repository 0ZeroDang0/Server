package com.example.zerodang.global.gpt.service;

import com.example.zerodang.domain.product.entity.Product;
import com.example.zerodang.global.gpt.dto.GPTRequestDTO;
import com.example.zerodang.global.gpt.dto.GPTResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
@Slf4j
public class GptServiceImpl implements GptService{
    private final WebClient gptWebClient;

    @Value("${gpt.model}")
    private String model;

    @Value("${gpt.api.url}")
    private String apiUrl;

    public GptServiceImpl(@Qualifier("gptWebClient") WebClient gptWebClient) {
        this.gptWebClient = gptWebClient;
    }

    @Override
    public Mono<String> analyzeWithGPT(Product product1, Product product2) {
        String productName1 = product1.getProductName();
        String productDescription1 = product1.getProductDescription();
        String productName2 = product2.getProductName();
        String productDescription2 = product2.getProductDescription();

        String prompt = String.format(
                "이름 : %s\n원재료 : %s\n\n이름 : %s\n원재료 : %s\n\n두 제품에 들어간 감미료들을 찾아주고 두 제품에 들어간 감미료를 보면서 어떤 걸 섭취하는 게 더 나을지 분석해줘.\n" +
                        "응답을 아래와 같은 형식으로 주세요:\n\n" +
                        "감미료 분석:\n" +
                        "비교 및 결론:\n" +
                        "안전성: 설명\n" +
                        "칼로리: 설명\n" +
                        "부작용: 설명\n" +
                        "따라서, 결론"
                , productName1, productDescription1, productName2, productDescription2
        );

        System.out.println(prompt);

        GPTRequestDTO gptRequestDTO = new GPTRequestDTO(model, prompt);

        return gptWebClient.post()
                .uri(apiUrl)
                .bodyValue(gptRequestDTO)
                .retrieve()
                .bodyToMono(GPTResponseDTO.class)
                .map(response -> response.getChoices().get(0).getMessage().getContent().trim());
    }
}