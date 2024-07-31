package com.sciencematch.sciencematch.controller;

import com.sciencematch.sciencematch.DTO.auth.request.StudentRequestDto;
import com.sciencematch.sciencematch.DTO.auth.response.MyPageDto;
import com.sciencematch.sciencematch.DTO.auth.response.StudentResponseDto;
import com.sciencematch.sciencematch.DTO.paper_test.PaperTestRequestDto;
import com.sciencematch.sciencematch.DTO.student.PaperTestAnswerResponseDto;
import com.sciencematch.sciencematch.DTO.student.SolvedQuestionPaperDto;
import com.sciencematch.sciencematch.DTO.teacher.request.GradingRequestDto;
import com.sciencematch.sciencematch.DTO.teacher.request.SummaryRequestDto;
import com.sciencematch.sciencematch.DTO.teacher.response.MyStudentsResponseDto;
import com.sciencematch.sciencematch.DTO.teacher.response.SimpleStudentsResponseDto;
import com.sciencematch.sciencematch.DTO.teacher.response.SummaryResponseDto;
import com.sciencematch.sciencematch.DTO.teacher.response.TeacherAssignPaperTestsResponseDto;
import com.sciencematch.sciencematch.DTO.teacher.response.TeacherAssignQuestionsResponseDto;
import com.sciencematch.sciencematch.common.dto.ApiResponseDto;
import com.sciencematch.sciencematch.exception.SuccessStatus;
import com.sciencematch.sciencematch.service.StudentService;
import com.sciencematch.sciencematch.service.TeacherService;
import com.sciencematch.sciencematch.service.common.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teacher")
@Tag(name = "선생", description = "선생 관련 API docs")
@SecurityRequirement(name = "JWT Auth")
public class TeacherController {

    private final TeacherService teacherService;
    private final AuthService authService;
    private final StudentService studentService;

    @PatchMapping(value = "/logo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "로고 변경")
    public ApiResponseDto<?> uploadLogo(
        @Parameter(hidden = true) @AuthenticationPrincipal User user,
        @ModelAttribute MultipartFile logo) throws IOException {
        teacherService.changeLogo(logo, user.getUsername());
        return ApiResponseDto.success(SuccessStatus.CHANGE_LOGO_SUCCESS);
    }

    @DeleteMapping(value = "/logo")
    @Operation(summary = "로고 삭제")
    public ApiResponseDto<?> deleteLogo(
        @Parameter(hidden = true) @AuthenticationPrincipal User user) throws IOException {
        teacherService.deleteLogo(user.getUsername());
        return ApiResponseDto.success(SuccessStatus.CHANGE_LOGO_SUCCESS);
    }

    @GetMapping("/mypage")
    @Operation(summary = "마이페이지 조회")
    public ApiResponseDto<MyPageDto> getMyPage(
        @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        return ApiResponseDto.success(SuccessStatus.GET_MYPAGE_SUCCESS,
            teacherService.getMypage(user.getUsername()));
    }

    @PostMapping("/student/summary")
    @Operation(summary = "학습내역 조회")
    public ApiResponseDto<SummaryResponseDto> getStudentSummary(
        @RequestBody @Valid SummaryRequestDto summaryRequestDto) {
        return ApiResponseDto.success(SuccessStatus.GET_MYPAGE_SUCCESS,
            teacherService.getStudentSummary(summaryRequestDto));
    }

    @PostMapping("/student/create")
    @Operation(summary = "학생 생성")
    public ApiResponseDto<StudentResponseDto> createStudent(
        @Parameter(hidden = true) @AuthenticationPrincipal User user,
        @RequestBody @Valid StudentRequestDto studentRequestDto) {
        return ApiResponseDto.success(SuccessStatus.CREATE_STUDENT_SUCCESS,
            authService.signupStudent(studentRequestDto, user.getUsername()));
    }

    @PostMapping("/student/update")
    @Operation(summary = "학생 정보 업데이트")
    public ApiResponseDto<StudentResponseDto> updateStudent(
        @RequestBody @Valid StudentRequestDto studentRequestDto) {
        return ApiResponseDto.success(SuccessStatus.STUDENT_INFO_UPDATE_SUCCESS,
            authService.updateStudent(studentRequestDto));
    }

    @DeleteMapping("/student/delete")
    @Operation(summary = "학생 삭제")
    public ApiResponseDto<StudentResponseDto> deleteStudent(
        @Schema(example = "01087654321") @RequestParam("phoneNum") String phoneNum) {
        return ApiResponseDto.success(SuccessStatus.DELETE_STUDENT_SUCCESS,
            authService.deleteStudent(phoneNum));
    }

    @PatchMapping("/student/re-enroll")
    @Operation(summary = "학생 재등록")
    public ApiResponseDto<?> reEnrollStudent(
        @RequestParam @Valid Long studentId) {
        authService.reEnrollStudent(studentId);
        return ApiResponseDto.success(SuccessStatus.RE_ENROLL_STUDENT_SUCCESS);
    }

    @GetMapping("/students")
    @Operation(summary = "나의 학생들 조회") //학생 관리 뷰에서 필요한 api
    public ApiResponseDto<List<MyStudentsResponseDto>> getStudents(
        @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        return ApiResponseDto.success(SuccessStatus.GET_ALL_STUDENTS_SUCCESS,
            teacherService.getMyStudents(user.getUsername()));
    }

    @GetMapping("/students/all")
    @Operation(summary = "간단 학생들 조회", description = "왼쪽에 간략하게 나와있는 탭 (추후 수정가능성 존재)")
    //학습지 등 간략하게 학생 리스트를 살펴볼 때 필요한 api
    public ApiResponseDto<List<SimpleStudentsResponseDto>> findAllStudents(
        @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        return ApiResponseDto.success(SuccessStatus.GET_ALL_STUDENT_SUCCESS,
            teacherService.findAllStudents(user.getUsername()));
    }

    @GetMapping("/assign-question-paper/{studentId}")
    @Operation(summary = "학생에게 출제한 학습지 리스트 조회")
    public ApiResponseDto<List<TeacherAssignQuestionsResponseDto>> getAssignQuestionPaper(@Schema(example = "2") @Param("studentID") @PathVariable Long studentId) {
        return ApiResponseDto.success(SuccessStatus.GET_ASSIGN_QUESTION_PAPER_LIST_SUCCESS,
            teacherService.getAssignQuestionPaper(studentId));
    }

    @GetMapping("/assign-paper-test/{studentId}")
    @Operation(summary = "학생에게 출제한 일대일 질문 리스트 조회")
    public ApiResponseDto<List<TeacherAssignPaperTestsResponseDto>> getAssignPaperTest(@Schema(example = "2") @Param("studentID") @PathVariable Long studentId) {
        return ApiResponseDto.success(SuccessStatus.GET_ASSIGN_PAPER_TEST_LIST_SUCCESS,
            teacherService.getAssignPaperTest(studentId));
    }

    @DeleteMapping("/assign-paper-test/{paperTestId}")
    @Operation(summary = "학생에게 출제한 일대일 질문 삭제")
    public ApiResponseDto<?> deleteAssignPaperTest(@Schema(example = "2") @Param("paperTestId") @PathVariable Long paperTestId) {
        teacherService.deleteAssignPaperTest(paperTestId);
        return ApiResponseDto.success(SuccessStatus.DELETE_ASSIGN_PAPER_TEST_SUCCESS);
    }

    @DeleteMapping("/assign-question-paper/{assignQuestionId}")
    @Operation(summary = "학생에게 출제한 학습지 삭제")
    public ApiResponseDto<?> deleteAssignQuestions(@Schema(example = "2") @Param("assignQuestionId") @PathVariable Long assignQuestionId) {
        teacherService.deleteAssignQuestion(assignQuestionId);
        return ApiResponseDto.success(SuccessStatus.DELETE_ASSIGN_PAPER_TEST_SUCCESS);
    }

    @Operation(summary = "출제한 학습지 조회")
    @GetMapping("/assign-question-paper/{id}/complete")
    public ApiResponseDto<SolvedQuestionPaperDto> getCompleteQuestionPaper(@Schema(example = "52") @PathVariable("id") Long id) {
        return ApiResponseDto.success(SuccessStatus.GET_ASSIGN_QUESTION_PAPER_SUCCESS,
            studentService.getCompleteQuestionPaper(id));
    }

    @Operation(summary = "출제한 일대일 질문 조회")
    @GetMapping("/assign-paper-test/{id}/complete")
    public ApiResponseDto<PaperTestAnswerResponseDto> getCompletePaperTest(@PathVariable("id") Long id) {
        return ApiResponseDto.success(SuccessStatus.GET_ASSIGN_QUESTION_PAPER_SUCCESS,
            studentService.getCompletePaperTest(id));
    }

    @Operation(summary = "학습지 문제 채점")
    @PostMapping("/grading/question-paper")
    public ApiResponseDto<?> gradingQuestionPaper(@RequestBody GradingRequestDto gradingRequestDto) {
        teacherService.gradingQuestionPaper(gradingRequestDto);
        return ApiResponseDto.success(SuccessStatus.GRADING_ANSWER_SUCCESS);
    }

    @Operation(summary = "일대일 질문 문제 채점")
    @PostMapping("/grading/paperTest")
    public ApiResponseDto<?> gradingPaperTest(@RequestBody GradingRequestDto gradingRequestDto) {
        teacherService.gradingPaperTest(gradingRequestDto);
        return ApiResponseDto.success(SuccessStatus.GRADING_ANSWER_SUCCESS);
    }

    @PostMapping(value = "/paper-test/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "일대일 질문 생성")
    public ApiResponseDto<?> createPaperTest(@ModelAttribute PaperTestRequestDto paperTestRequestDto) {
        teacherService.createPaperTest(paperTestRequestDto);
        return ApiResponseDto.success(SuccessStatus.CREATE_PAPER_TEST_SUCCESS);
    }
}
