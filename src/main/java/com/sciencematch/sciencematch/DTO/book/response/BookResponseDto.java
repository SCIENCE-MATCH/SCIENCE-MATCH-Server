package com.sciencematch.sciencematch.DTO.book.response;

import com.sciencematch.sciencematch.Enums.School;
import com.sciencematch.sciencematch.Enums.Semester;
import com.sciencematch.sciencematch.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookResponseDto {

    private School school;
    private Semester semester;
    private String title;
    private Integer editionNum;
    private String publisher;

    public static BookResponseDto of(Book book) {
        return new BookResponseDto(book.getSchool(), book.getSemester(), book.getTitle(),
            book.getEditionNum(), book.getPublisher());
    }
}
