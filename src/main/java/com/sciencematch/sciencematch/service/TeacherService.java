package com.sciencematch.sciencematch.service;

import com.sciencematch.sciencematch.controller.dto.response.MyPageDto;
import com.sciencematch.sciencematch.domain.AssignQuestions;
import com.sciencematch.sciencematch.domain.QuestionPaper;
import com.sciencematch.sciencematch.domain.Student;
import com.sciencematch.sciencematch.domain.Teacher;
import com.sciencematch.sciencematch.domain.dto.question_paper.QuestionPaperResponseDto;
import com.sciencematch.sciencematch.domain.dto.question_paper.QuestionPaperSelectDto;
import com.sciencematch.sciencematch.domain.dto.teacher.MultipleQuestionPaperSubmitDto;
import com.sciencematch.sciencematch.domain.dto.teacher.QuestionPaperSubmitDto;
import com.sciencematch.sciencematch.domain.dto.team.TeamResponseDto;
import com.sciencematch.sciencematch.domain.dto.teacher.SimpleStudentsResponseDto;
import com.sciencematch.sciencematch.domain.dto.teacher.MyStudentsResponseDto;
import com.sciencematch.sciencematch.external.client.aws.S3Service;
import com.sciencematch.sciencematch.infrastructure.AssignQuestionRepository;
import com.sciencematch.sciencematch.infrastructure.Question.QuestionPaperRepository;
import com.sciencematch.sciencematch.infrastructure.StudentRepository;
import com.sciencematch.sciencematch.infrastructure.TeacherRepository;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final QuestionPaperRepository questionPaperRepository;
    private final AssignQuestionRepository assignQuestionRepository;
    private final S3Service s3Service;

    //로고 변경
    @Transactional
    public void changeLogo(MultipartFile logo, String email) throws IOException {
        Teacher teacher = teacherRepository.getTeacherByEmail(email);
        if (teacher.getLogo() != null) {
            s3Service.deleteFile(teacher.getLogo());
        }
        String logoURL = s3Service.uploadImage(logo, "logo");
        teacher.changeLogo(logoURL);
    }

    //학생 관리 조회
    public List<MyStudentsResponseDto> getMyStudents(String email) {
        Teacher teacher = teacherRepository.getTeacherByEmail(email);
        return studentRepository.findAllByTeacher(teacher).stream().map(MyStudentsResponseDto::of)
            .collect(Collectors.toList());
    }

    //마이페이지 조회
    public MyPageDto getMypage(String email) {
        return MyPageDto.of(teacherRepository.getTeacherByEmail(email));
    }

    //간단 학생 조회 (반 생성, 퀴즈 등)
    public List<SimpleStudentsResponseDto> findAllStudents(String email) {
        return teacherRepository.getTeacherByEmail(email).getStudents().stream().map(
                SimpleStudentsResponseDto::of)
            .collect(Collectors.toList());
    }

    //나의 반 목록 조회
    public List<TeamResponseDto> getMyGroups(String email) {
            return teacherRepository.getTeacherByEmail(email).getTeam().stream()
                .map(TeamResponseDto::of)
                .collect(Collectors.toList());
    }

    public List<QuestionPaperResponseDto> getAllQuestionPaper(
        QuestionPaperSelectDto questionPaperSelectDto) {
        return questionPaperRepository.search(questionPaperSelectDto);
    }

    @Transactional
    public void submitQuestionPaper(QuestionPaperSubmitDto questionPaperSubmitDto) {
        List<Student> students = studentRepository.getStudentsByList(
            questionPaperSubmitDto.getStudentIds());
        QuestionPaper questionPaper = questionPaperRepository.getQuestionPaperById(
            questionPaperSubmitDto.getQuestionPaperId());
        for (Student student : students) {
            assignQuestionRepository.save(AssignQuestions.builder()
                .questionPaper(questionPaper)
                .student(student)
                .subject(questionPaper.getSubject())
                .build());
        }
    }

    @Transactional
    public void submitMultipleQuestionPaper(
        MultipleQuestionPaperSubmitDto multipleQuestionPaperSubmitDto) {
        List<Student> students = studentRepository.getStudentsByList(
            multipleQuestionPaperSubmitDto.getStudentIds());
        List<QuestionPaper> papers = questionPaperRepository.getQuestionPapersByList(
            multipleQuestionPaperSubmitDto.getQuestionPaperIds());
        for (QuestionPaper questionPaper : papers) {
            for (Student student : students) {
                assignQuestionRepository.save(AssignQuestions.builder()
                    .questionPaper(questionPaper)
                    .student(student)
                    .subject(questionPaper.getSubject())
                    .build());
            }
        }
    }
}
