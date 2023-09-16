package com.sciencematch.sciencematch.service;

import com.sciencematch.sciencematch.DTO.student.AssignPaperTestResponseDto;
import com.sciencematch.sciencematch.DTO.student.AssignQuestionPaperResponseDto;
import com.sciencematch.sciencematch.Enums.Category;
import com.sciencematch.sciencematch.domain.Student;
import com.sciencematch.sciencematch.domain.question.Answer;
import com.sciencematch.sciencematch.infrastructure.AssignPaperTestRepository;
import com.sciencematch.sciencematch.infrastructure.AssignQuestionRepository;
import com.sciencematch.sciencematch.infrastructure.StudentRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final AssignQuestionRepository assignQuestionRepository;
    private final AssignPaperTestRepository assignPaperTestRepository;


    public List<AssignQuestionPaperResponseDto> getMyQuestionPaper(String phoneNum) {
        Student student = studentRepository.getStudentByPhoneNum(phoneNum);
        return assignQuestionRepository.findAllByStudent(student).stream()
            .map(AssignQuestionPaperResponseDto::of).collect(Collectors.toList());
    }

    public List<Category> getQuestionPaperStructure(Long assignQuestionPaperId) {
        return assignQuestionRepository.getAssignQuestionsById(assignQuestionPaperId)
            .getAnswer().stream().map(Answer::getCategory).collect(Collectors.toList());
    }

    public List<AssignPaperTestResponseDto> getMyPaperTest(String phoneNum) {
        Student student = studentRepository.getStudentByPhoneNum(phoneNum);
        return assignPaperTestRepository.findAllByStudent(student).stream()
            .map(AssignPaperTestResponseDto::of).collect(Collectors.toList());
    }

}
