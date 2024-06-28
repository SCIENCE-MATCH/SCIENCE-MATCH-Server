package com.sciencematch.sciencematch.service;

import com.sciencematch.sciencematch.DTO.book.request.CreateBookDto;
import com.sciencematch.sciencematch.DTO.book.response.BookQuestionResponseDto;
import com.sciencematch.sciencematch.DTO.book.response.BookResponseDto;
import com.sciencematch.sciencematch.domain.Book;
import com.sciencematch.sciencematch.domain.question.Question;
import com.sciencematch.sciencematch.infrastructure.BookRepository;
import com.sciencematch.sciencematch.infrastructure.ChapterRepository;
import com.sciencematch.sciencematch.infrastructure.question.QuestionRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final QuestionRepository questionRepository;
    private final ChapterRepository chapterRepository;

    public List<BookResponseDto> getBookForQuestionPaper() {
        return bookRepository.findAll().stream().map(BookResponseDto::of)
            .collect(Collectors.toList());
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

    public void updateBook(Long bookId, CreateBookDto createBookDto) {
        Book book = bookRepository.getBookById(bookId);
        book.patchBook(createBookDto);
        bookRepository.save(book);
    }

    public Map<String, List<Integer>> getBookChapter(Long bookId) {
        //question에서 챕터 id와 page만 필요
        Map<String, List<Integer>> group = new HashMap<>();
        List<Question> questions = questionRepository.findAllByBookId(bookId);

        for (Question question : questions) {
            String description = chapterRepository.getChapterById(question.getChapterId())
                .getDescription();
            if (group.containsKey(description)){
                List<Integer> value = group.get(description);
                if (!value.contains(question.getPage())) {
                    value.add(question.getPage());
                }
                continue;
            }
            // 값이 존재하지 않으면 새로운 리스트를 생성하고 페이지 추가
            group.put(description, new ArrayList<>(question.getPage()));
        }
        return group;
    }

    public List<BookQuestionResponseDto> getBookQuestion(Long bookId, Integer page) {
        return questionRepository.findAllByBookIdAndPage(bookId, page).stream().map(BookQuestionResponseDto::of)
            .collect(Collectors.toList());
    }
}
