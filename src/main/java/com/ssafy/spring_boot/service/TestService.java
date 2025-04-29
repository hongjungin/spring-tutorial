package com.ssafy.spring_boot.service;

import com.ssafy.spring_boot.domain.Test;
import com.ssafy.spring_boot.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {
    private TestRepository testRepository;

    @Transactional
    public List<Test> findAllTests() {
        return testRepository.findAll();
    }
}
