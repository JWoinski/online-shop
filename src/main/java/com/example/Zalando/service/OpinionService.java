package com.example.Zalando.service;

import com.example.Zalando.repository.OpinionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpinionService {
    private final OpinionRepository opinionRepository;

    public Object getOpinionByProductId(int productId) {
        return opinionRepository.findByProductProductId(productId);
    }
}