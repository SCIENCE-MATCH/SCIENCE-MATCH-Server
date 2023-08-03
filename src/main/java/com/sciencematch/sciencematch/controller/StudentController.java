package com.sciencematch.sciencematch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {
    //학생 페이지 관련 컨트롤러
    //학습 현황 service 와 문제 풀기 service를 injection해서 이 컨트롤러에서 mapping되게 구현하자
}
