package com.sciencematch.sciencematch.dto.book.response;

import com.sciencematch.sciencematch.enums.School;
import com.sciencematch.sciencematch.enums.Semester;
import com.sciencematch.sciencematch.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookResponseDto {

    private Long bookId;
    private School school;
    private Semester semester;
    private String title;
    private Integer editionNum;
    private String publisher;

    public static BookResponseDto of(Book book) {
        return new BookResponseDto(book.getId(), book.getSchool(), book.getSemester(), book.getTitle(),
            book.getEditionNum(), book.getPublisher());
    }
}
