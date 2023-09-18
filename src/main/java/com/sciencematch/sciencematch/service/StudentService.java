package com.sciencematch.sciencematch.service;

import com.sciencematch.sciencematch.DTO.student.AnswerResponseDto;
import com.sciencematch.sciencematch.DTO.student.AssignPaperTestResponseDto;
import com.sciencematch.sciencematch.DTO.student.AssignPaperTestSolveDto;
import com.sciencematch.sciencematch.DTO.student.AssignQuestionPaperResponseDto;
import com.sciencematch.sciencematch.DTO.student.AssignQuestionPaperSolveDto;
import com.sciencematch.sciencematch.DTO.student.PaperTestAnswerResponseDto;
import com.sciencematch.sciencematch.Enums.Category;
import com.sciencematch.sciencematch.domain.Student;
import com.sciencematch.sciencematch.domain.paper_test.AssignPaperTest;
import com.sciencematch.sciencematch.domain.paper_test.PaperTestAnswer;
import com.sciencematch.sciencematch.domain.question.Answer;
import com.sciencematch.sciencematch.domain.question.AssignQuestions;
import com.sciencematch.sciencematch.infrastructure.paper_test.AssignPaperTestRepository;
import com.sciencematch.sciencematch.infrastructure.paper_test.PaperTestAnswerRepository;
import com.sciencematch.sciencematch.infrastructure.paper_test.PaperTestQuestionRepository;
import com.sciencematch.sciencematch.infrastructure.question.AnswerRepository;
import com.sciencematch.sciencematch.infrastructure.question.AssignQuestionRepository;
import com.sciencematch.sciencematch.infrastructure.StudentRepository;
import com.sciencematch.sciencematch.infrastructure.question.ConnectQuestionRepository;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final AssignQuestionRepository assignQuestionRepository;
    private final AssignPaperTestRepository assignPaperTestRepository;
    private final ConnectQuestionRepository connectQuestionRepository;
    private final PaperTestQuestionRepository paperTestQuestionRepository;
    private final AnswerRepository answerRepository;
    private final PaperTestAnswerRepository paperTestAnswerRepository;


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

    @Transactional
    public void solveAssignQuestionPaper(AssignQuestionPaperSolveDto assignQuestionPaperSolveDto) {

        AssignQuestions questions = assignQuestionRepository.getAssignQuestionsById(assignQuestionPaperSolveDto.getAssignQuestionPaperId());

        List<Answer> answers = connectQuestionRepository.getAllConnectQuestionByQuestionPaper(
                questions.getQuestionPaper())
            .stream().map(cq -> Answer.builder()
                .solution(cq.getQuestion().getSolution())
                .solutionImg(cq.getQuestion().getSolutionImg())
                .category(cq.getQuestion().getCategory())
                .chapterId(cq.getQuestion().getChapterId())
                .build()).collect(Collectors.toList());

        List<String> solvingAnswer = assignQuestionPaperSolveDto.getAnswer();

        for (int i=0; i<(answers.size()); i++) {
            answers.get(i).setSubmitAnswer(solvingAnswer.get(i));
            //제출한 답과 answer의 solution이 일치하면 정답처리
            if (Objects.equals(answers.get(i).getSolution(),
                solvingAnswer.get(i))) {
                answers.get(i).setRightAnswer();
            }
        }

        questions.setAnswerAndAssignStatus(answers);
        answerRepository.saveAll(answers);

    }

    @Transactional
    public void solveAssignPaperTest(AssignPaperTestSolveDto assignPaperTestSolveDto) {
        AssignPaperTest assignPaperTest = assignPaperTestRepository.getAssignPaperTestById(
            assignPaperTestSolveDto.getAssignPaperTestId());

        //answer 객체 생성
        List<PaperTestAnswer> answers = paperTestQuestionRepository.getAllPaperTestQuestionByPaperTest(
            assignPaperTest.getPaperTest()).stream().map(cq -> PaperTestAnswer.builder()
            .solution(cq.getSolution())
            .build()).collect(Collectors.toList());

        List<String> solvingAnswer = assignPaperTestSolveDto.getAnswer();

        for (int i=0; i<(answers.size()); i++) {
            answers.get(i).setSubmitAnswer(solvingAnswer.get(i));
            //제출한 답과 answer의 solution이 일치하면 정답처리
            if (Objects.equals(answers.get(i).getSolution(),
                solvingAnswer.get(i))) {
                answers.get(i).setRightAnswer();
            }
        }

        assignPaperTest.setPaperTestAnswerAndAssignStatus(answers);
        paperTestAnswerRepository.saveAll(answers);
    }

    public List<AnswerResponseDto> getCompleteQuestionPaper(Long questionId) {
        return assignQuestionRepository.getAssignQuestionsById(questionId).getAnswer().stream()
            .map(AnswerResponseDto::of).collect(
                Collectors.toList());
    }

    public List<PaperTestAnswerResponseDto> getCompletePaperTest(Long paperTestId) {
        return assignPaperTestRepository.getAssignPaperTestById(paperTestId).getPaperTestAnswer().stream()
            .map(PaperTestAnswerResponseDto::of).collect(Collectors.toList());
    }

}
