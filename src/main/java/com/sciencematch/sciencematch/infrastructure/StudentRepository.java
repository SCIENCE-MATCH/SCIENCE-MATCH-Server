package com.sciencematch.sciencematch.infrastructure;

import com.sciencematch.sciencematch.domain.Student;
import com.sciencematch.sciencematch.domain.Teacher;
import com.sciencematch.sciencematch.exception.ErrorStatus;
import com.sciencematch.sciencematch.exception.model.NotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByPhoneNum(String phoneNum);

    @EntityGraph(attributePaths = {"teacher"})
    List<Student> findAllByDeleted(Boolean deleted);

    //삭제된 학생까지 조회 가능해야함
    List<Student> findAllByTeacher(Teacher teacher);

    Optional<Student> findByPhoneNumAndDeleted(String PhoneNum, Boolean deleted);

    Optional<Student> findByIdAndDeleted(Long id, Boolean deleted);

    default Student getStudentByPhoneNum(String phoneNum) {
        return this.findByPhoneNumAndDeleted(phoneNum, false)
            .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
    }

    default Student getStudentById(Long id) {
        return this.findByIdAndDeleted(id, false)
            .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_USER_EXCEPTION, ErrorStatus.NOT_FOUND_USER_EXCEPTION.getMessage()));
    }

}
