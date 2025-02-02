package com.sciencematch.sciencematch.controller;

import com.sciencematch.sciencematch.dto.question_paper.QuestionPaperDetailDto;
import com.sciencematch.sciencematch.dto.student.AssignPaperTestResponseDto;
import com.sciencematch.sciencematch.dto.student.AssignPaperTestSolveDto;
import com.sciencematch.sciencematch.dto.student.AssignQuestionPaperResponseDto;
import com.sciencematch.sciencematch.dto.student.AssignQuestionPaperSolveDto;
import com.sciencematch.sciencematch.dto.student.PaperTestAnswerResponseDto;
import com.sciencematch.sciencematch.dto.student.SolvedQuestionPaperDto;
import com.sciencematch.sciencematch.dto.student.StudentMyPageDto;
import com.sciencematch.sciencematch.common.dto.ApiResponseDto;
import com.sciencematch.sciencematch.exception.SuccessStatus;
import com.sciencematch.sciencematch.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/mypage")
    @Operation(summary = "마이페이지 조회")
    public ApiResponseDto<StudentMyPageDto> getMyPage(
            @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        return ApiResponseDto.success(SuccessStatus.GET_MYPAGE_SUCCESS,
                studentService.getMypage(user.getUsername()));
    }

    @GetMapping("/question-paper")
    @Operation(summary = "학습지 리스트 조회")
    public ApiResponseDto<List<AssignQuestionPaperResponseDto>> getMyQuestionPaper(
        @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        return ApiResponseDto.success(SuccessStatus.GET_ASSIGN_QUESTION_PAPER_LIST_SUCCESS,
            studentService.getMyQuestionPaper(user.getUsername()));
    }

    @GetMapping("/question-paper/detail")
    @Operation(summary = "학습지 상세 조회", description = "문제 풀기 창에서 사용 | 일대일 질문은 전부 단답형")
    public ApiResponseDto<QuestionPaperDetailDto> getQuestionPaperDetail(
        @RequestParam Long AssignQuestionPaperId) {
        return ApiResponseDto.success(SuccessStatus.GET_QUESTION_PAPER_DETAIL_SUCCESS,
            studentService.getQuestionPaperDetail(AssignQuestionPaperId));
    }

    @Operation(summary = "일대일 질문지 리스트 조회")
    @GetMapping("/paper-test")
    public ApiResponseDto<List<AssignPaperTestResponseDto>> getMyPaperTest(
        @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        return ApiResponseDto.success(SuccessStatus.GET_ASSIGN_PAPER_TEST_LIST_SUCCESS,
            studentService.getMyPaperTest(user.getUsername()));
    }

    @Operation(summary = "학습지 답 제출")
    @PostMapping("/question-paper")
    public ApiResponseDto<?> solveAssignQuestionPaper(
        @RequestBody @Valid AssignQuestionPaperSolveDto assignQuestionPaperSolveDto) {
        studentService.solveAssignQuestionPaper(assignQuestionPaperSolveDto);
        return ApiResponseDto.success(SuccessStatus.SOLVE_QUESTION_PAPER_SUCCESS);
    }

    @Operation(summary = "일대일 질문 답 제출")
    @PostMapping("/paper-test")
    public ApiResponseDto<?> solveAssignPaperTest(
        @RequestBody @Valid AssignPaperTestSolveDto assignPaperTestSolveDto) {
        studentService.solveAssignPaperTest(assignPaperTestSolveDto);
        return ApiResponseDto.success(SuccessStatus.SOLVE_PAPER_TEST_SUCCESS);
    }

    @Operation(summary = "완료한 학습지 조회", description = "학습지 제출 후에 나오는 뷰도 이 api로 호출")
    @GetMapping("/question-paper/{id}/complete")
    public ApiResponseDto<SolvedQuestionPaperDto> getCompleteQuestionPaper(@Schema(example = "52") @PathVariable("id") Long id) {
        return ApiResponseDto.success(SuccessStatus.GET_ASSIGN_QUESTION_PAPER_SUCCESS,
            studentService.getCompleteQuestionPaper(id));
    }

    @Operation(summary = "완료한 일대일 질문 조회", description = "일대일 질문 제출 후에 나오는 뷰도 이 api로 호출")
    @GetMapping("/paper-test/{id}/complete")
    public ApiResponseDto<PaperTestAnswerResponseDto> getCompletePaperTest(@PathVariable("id") Long id) {
        return ApiResponseDto.success(SuccessStatus.GET_ASSIGN_QUESTION_PAPER_SUCCESS,
            studentService.getCompletePaperTest(id));
    }
}
