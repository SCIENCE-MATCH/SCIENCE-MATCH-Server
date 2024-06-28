package com.sciencematch.sciencematch.infrastructure;

import com.sciencematch.sciencematch.domain.Book;
import com.sciencematch.sciencematch.exception.ErrorStatus;
import com.sciencematch.sciencematch.exception.model.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {


    default Book getBookById(Long id) {
        return this.findById(id).orElseThrow(
            () -> new NotFoundException(ErrorStatus.NOT_FOUND_BOOK_EXCEPTION,
                ErrorStatus.NOT_FOUND_BOOK_EXCEPTION.getMessage())
        );
    }
}
