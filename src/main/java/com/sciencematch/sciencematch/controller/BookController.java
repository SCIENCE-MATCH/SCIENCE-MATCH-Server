package com.sciencematch.sciencematch.controller;

import com.sciencematch.sciencematch.DTO.book.request.CreateBookDto;
import com.sciencematch.sciencematch.common.dto.ApiResponseDto;
import com.sciencematch.sciencematch.exception.SuccessStatus;
import com.sciencematch.sciencematch.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "JWT Auth")
@Tag(name = "시중교재", description = "시중교재 API")
public class BookController {

    private final BookService bookService;

    @GetMapping("/book/question-paper")
    @Operation(summary = "시중교재 조회", description = "선생 1-1 뷰의 시중교재 학습지 조회 부분입니다.")
    public ApiResponseDto<?> getBookForQuestionPaper() {
        return ApiResponseDto.success(SuccessStatus.GET_BOOK_SUCCESS,
            bookService.getBookForQuestionPaper());
    }

    @PostMapping("/admin/book/create")
    @Operation(summary = "새 교재 추가", description = "관리자 1-1 뷰의 새 교재 추가 부분입니다.")
    public ApiResponseDto<?> createBook(@RequestBody CreateBookDto createBookDto) {
        bookService.createBook(createBookDto);
        return ApiResponseDto.success(SuccessStatus.CREATE_BOOK_SUCCESS);
    }

    @GetMapping("/book/chapter/{id}")
    @Operation(summary = "교재 챕터 조회", description = "선생 1-1 뷰의 시중 교재 문제 선택 부분입니다.")
    public ApiResponseDto<?> getBookChapter(@PathVariable("id") Long bookId) {
        return ApiResponseDto.success(SuccessStatus.GET_BOOK_QUESTION_SUCCESS, bookService.getBookChapter(bookId));
    }

    @GetMapping("/book/question/{id}/{page}")
    @Operation(summary = "교재 문제 조회", description = "선생 1-1 뷰의 시중 교재 문제 선택 부분입니다.")
    public ApiResponseDto<?> getBookQuestion(@PathVariable("id") Long bookId, @PathVariable("page") Integer page) {
        return ApiResponseDto.success(SuccessStatus.GET_BOOK_QUESTION_SUCCESS, bookService.getBookQuestion(bookId, page));
    }
}
