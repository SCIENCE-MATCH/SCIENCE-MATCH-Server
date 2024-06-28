package com.sciencematch.sciencematch.infrastructure;

import com.sciencematch.sciencematch.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
