package com.sciencematch.sciencematch.controller;

import com.sciencematch.sciencematch.common.dto.ApiResponseDto;
import com.sciencematch.sciencematch.domain.dto.admin.AdminStudentResponseDto;
import com.sciencematch.sciencematch.domain.dto.admin.AdminTeamResponseDto;
import com.sciencematch.sciencematch.domain.dto.admin.WaitingTeacherResponseDto;
import com.sciencematch.sciencematch.domain.dto.chapter.ChapterPatchDto;
import com.sciencematch.sciencematch.domain.dto.chapter.ChapterPostDto;
import com.sciencematch.sciencematch.domain.dto.chapter.ChapterRequestDto;
import com.sciencematch.sciencematch.domain.dto.chapter.ChapterResponseDto;
import com.sciencematch.sciencematch.domain.dto.question.QuestionPostDto;
import com.sciencematch.sciencematch.domain.dto.question.QuestionRequestDto;
import com.sciencematch.sciencematch.domain.dto.question.AdminQuestionResponseDto;
import com.sciencematch.sciencematch.exception.SuccessStatus;
import com.sciencematch.sciencematch.service.AdminService;
import com.sciencematch.sciencematch.service.ChapterService;
import com.sciencematch.sciencematch.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@SecurityRequirement(name = "JWT Auth")
@Tag(name = "관리자", description = "관리자 API")
public class AdminController {

    private final AdminService adminService;
    private final ChapterService chapterService;

    private final QuestionService questionService;

    @GetMapping("/teachers/waiting")
    @Operation(summary = "승인 대기 선생 조회")
    public ApiResponseDto<List<WaitingTeacherResponseDto>> getAllWaitingTeachers() {
        return ApiResponseDto.success(SuccessStatus.GET_ALL_WAITING_TEACHERS_SUCCESS,
            adminService.getAllWaitingTeachers());
    }

    @PostMapping("/teachers/{id}")
    @Operation(summary = "선생 가입 승인")
    public ApiResponseDto<WaitingTeacherResponseDto> assignTeacher(
        @PathVariable(name = "id") Long id) {
        return ApiResponseDto.success(SuccessStatus.ASSIGN_TEACHER_SUCCESS,
            adminService.assignTeacher(id));
    }

    @GetMapping("/teachers")
    @Operation(summary = "선생 리스트 조회")
    public ApiResponseDto<List<WaitingTeacherResponseDto>> getAllTeachers() {
        return ApiResponseDto.success(SuccessStatus.GET_ALL_TEACHERS_SUCCESS,
            adminService.getAllTeachers());
    }

    @DeleteMapping("/teachers/{id}")
    @Operation(summary = "선생 삭제")
    public ApiResponseDto<WaitingTeacherResponseDto> getAllTeacher(
        @PathVariable(name = "id") Long id) {
        return ApiResponseDto.success(SuccessStatus.DELETE_TEACHER_SUCCESS,
            adminService.deleteTeacher(id));
    }

    @GetMapping("/students")
    @Operation(summary = "학생 리스트 조회")
    public ApiResponseDto<List<AdminStudentResponseDto>> getAllStudents() {
        return ApiResponseDto.success(SuccessStatus.GET_ALL_STUDENTS_SUCCESS,
            adminService.getAllStudents());
    }

    @DeleteMapping("/students/{id}")
    @Operation(summary = "학생 삭제")
    public ApiResponseDto<AdminStudentResponseDto> deleteStudent(
        @PathVariable(name = "id") Long id) {
        return ApiResponseDto.success(SuccessStatus.DELETE_STUDENT_SUCCESS,
            adminService.deleteStudent(id));
    }

    @GetMapping("/teams")
    @Operation(summary = "반 리스트 조회")
    public ApiResponseDto<List<AdminTeamResponseDto>> getAllTeams() {
        return ApiResponseDto.success(SuccessStatus.GET_TEAMS_SUCCESS,
            adminService.getAllTeams());
    }

    @DeleteMapping("/teams/{id}")
    @Operation(summary = "반 삭제")
    public ApiResponseDto<AdminTeamResponseDto> deleteTeam(@PathVariable(name = "id") Long id) {
        return ApiResponseDto.success(SuccessStatus.DELETE_TEAM_SUCCESS,
            adminService.deleteTeam(id));
    }

    @PostMapping("/chapter/get")
    @Operation(summary = "단원 조회")
    public ApiResponseDto<List<ChapterResponseDto>> getChapter(
        @RequestBody ChapterRequestDto chapterRequestDto) {
        return ApiResponseDto.success(SuccessStatus.GET_CHAPTER_SUCCESS,
            chapterService.getChapter(chapterRequestDto));
    }

    @PostMapping("/chapter")
    @Operation(summary = "단원 추가")
    public ApiResponseDto<?> postChapter(@RequestBody ChapterPostDto chapterPostDto) {
        chapterService.postChapter(chapterPostDto);
        return ApiResponseDto.success(SuccessStatus.POST_CHAPTER_SUCCESS);
    }

    @PatchMapping("/chapter")
    @Operation(summary = "단원 수정")
    public ApiResponseDto<?> postChapter(@RequestBody ChapterPatchDto chapterPatchDto) {
        chapterService.patchChapter(chapterPatchDto);
        return ApiResponseDto.success(SuccessStatus.PATCH_CHAPTER_SUCCESS);
    }

    @DeleteMapping("/chapter")
    @Operation(summary = "단원 삭제")
    public ApiResponseDto<?> deleteChapter(@RequestParam Long chapterId) {
        chapterService.deleteChapter(chapterId);
        return ApiResponseDto.success(SuccessStatus.DELETE_CHAPTER_SUCCESS);
    }

    @PostMapping(value = "/question/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "문제 추가")
    public ApiResponseDto<?> postQuestion(@ModelAttribute QuestionPostDto questionPostDto)
        throws IOException {
        questionService.postQuestion(questionPostDto);
        return ApiResponseDto.success(SuccessStatus.POST_QUESTION_SUCCESS);
    }

    @PostMapping(value = "/question")
    @Operation(summary = "문제 조회")
    public ApiResponseDto<List<AdminQuestionResponseDto>> getQuestions(
        @RequestBody QuestionRequestDto questionRequestDto) {
        return ApiResponseDto.success(SuccessStatus.GET_QUESTION_SUCCESS,
            questionService.getQuestions(questionRequestDto));
    }

    @DeleteMapping(value = "/question")
    @Operation(summary = "문제 삭제")
    public ApiResponseDto<?> deleteQuestion(
        @RequestParam Long questionId) throws IOException {
        questionService.deleteQuestion(questionId);
        return ApiResponseDto.success(SuccessStatus.DELETE_QUESTION_SUCCESS);
    }

}
