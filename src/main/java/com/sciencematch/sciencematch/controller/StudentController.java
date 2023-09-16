package com.sciencematch.sciencematch.controller;

import com.sciencematch.sciencematch.DTO.student.AssignPaperTestResponseDto;
import com.sciencematch.sciencematch.DTO.student.AssignQuestionPaperResponseDto;
import com.sciencematch.sciencematch.Enums.Category;
import com.sciencematch.sciencematch.common.dto.ApiResponseDto;
import com.sciencematch.sciencematch.exception.SuccessStatus;
import com.sciencematch.sciencematch.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
@Tag(name = "학생", description = "학생 관련 API docs")
@SecurityRequirement(name = "JWT Auth")
public class StudentController {

    //학생 페이지 관련 컨트롤러
    //학습 현황 service 와 문제 풀기 service를 injection해서 이 컨트롤러에서 mapping되게 구현하자
    private final StudentService studentService;

    @Operation(summary = "학습지 조회")
    @GetMapping("/question-paper")
    public ApiResponseDto<List<AssignQuestionPaperResponseDto>> getMyQuestionPaper(@Parameter(hidden = true) @AuthenticationPrincipal User user) {
        return ApiResponseDto.success(SuccessStatus.GET_ASSIGN_QUESTION_PAPER_SUCCESS,
            studentService.getMyQuestionPaper(user.getUsername()));
    }

    @Operation(summary = "학습지 답안 형식 조회", description = "문제 풀기 창에서 사용 | 일대일 질문은 전부 단답형")
    @GetMapping("/question-paper/answer/structure")
    public ApiResponseDto<List<Category>> getQuestionPaperStructure(@RequestParam Long AssignQuestionPaperId) {
        return ApiResponseDto.success(SuccessStatus.GET_QUESTION_PAPER_STRUCTURE_SUCCESS,
            studentService.getQuestionPaperStructure(AssignQuestionPaperId));
    }

    @Operation(summary = "일대일 질문지 조회")
    @GetMapping("/paper-test")
    public ApiResponseDto<List<AssignPaperTestResponseDto>> getMyPaperTest(@Parameter(hidden = true) @AuthenticationPrincipal User user) {
        return ApiResponseDto.success(SuccessStatus.GET_ASSIGN_QUESTION_PAPER_SUCCESS,
            studentService.getMyPaperTest(user.getUsername()));
    }
}
