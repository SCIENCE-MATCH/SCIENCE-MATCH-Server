package com.sciencematch.sciencematch.service;

import com.sciencematch.sciencematch.DTO.book.request.CreateBookDto;
import com.sciencematch.sciencematch.DTO.book.response.BookResponseDto;
import com.sciencematch.sciencematch.domain.Book;
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

    public void createBook(CreateBookDto createBookDto) {
        Book book = Book.builder()
            .school(createBookDto.getSchool())
            .semester(createBookDto.getSemester())
            .title(createBookDto.getTitle())
            .editionNum(createBookDto.getEditionNum())
            .publisher(createBookDto.getPublisher())
            .build();
        bookRepository.save(book);
    }

}
