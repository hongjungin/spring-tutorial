package com.ssafy.spring_boot.repository;

import com.ssafy.spring_boot.domain.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {}
