package com.sciencematch.sciencematch.controller;

import com.sciencematch.sciencematch.common.dto.ApiResponseDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
@Tag(name = "학생", description = "학생 관련 API docs")
@SecurityRequirement(name = "JWT Auth")
public class StudentController {
    //학생 페이지 관련 컨트롤러
    //학습 현황 service 와 문제 풀기 service를 injection해서 이 컨트롤러에서 mapping되게 구현하자

//    @GetMapping("/question-paper")
//    public ApiResponseDto<?> getMyQuestionPaper() {
//    }
}
