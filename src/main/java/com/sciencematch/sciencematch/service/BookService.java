package com.sciencematch.sciencematch.service;

import com.sciencematch.sciencematch.DTO.book.response.BookResponseDto;
import com.sciencematch.sciencematch.infrastructure.BookRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<BookResponseDto> getBookForQuestionPaper() {
        return bookRepository.findAll().stream().map(BookResponseDto::of).collect(Collectors.toList());
    }

}
