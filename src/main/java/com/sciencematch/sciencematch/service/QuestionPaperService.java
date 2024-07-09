package com.sciencematch.sciencematch.service;

import com.sciencematch.sciencematch.DTO.concept.ConceptResponseDto;
import com.sciencematch.sciencematch.DTO.question_paper.NormalQuestionPaperRequestDto;
import com.sciencematch.sciencematch.DTO.question_paper.QuestionPaperCreateDto;
import com.sciencematch.sciencematch.DTO.question_paper.QuestionPaperDownloadDto;
import com.sciencematch.sciencematch.DTO.question_paper.QuestionPaperDownloadRequestDto;
import com.sciencematch.sciencematch.DTO.question_paper.QuestionPaperResponseDto;
import com.sciencematch.sciencematch.DTO.question_paper.QuestionPaperSelectDto;
import com.sciencematch.sciencematch.DTO.question_paper.QuestionResponseDto;
import com.sciencematch.sciencematch.DTO.question_paper.WrongAnswerPeriodDto;
import com.sciencematch.sciencematch.DTO.teacher.request.MultipleQuestionPaperSubmitDto;
import com.sciencematch.sciencematch.DTO.teacher.request.QuestionPaperSubmitDto;
import com.sciencematch.sciencematch.Enums.AssignStatus;
import com.sciencematch.sciencematch.domain.Chapter;
import com.sciencematch.sciencematch.domain.Student;
import com.sciencematch.sciencematch.domain.Teacher;
import com.sciencematch.sciencematch.domain.question.Answer;
import com.sciencematch.sciencematch.domain.question.AssignQuestions;
import com.sciencematch.sciencematch.domain.question.ConnectQuestion;
import com.sciencematch.sciencematch.domain.question.Question;
import com.sciencematch.sciencematch.domain.question.QuestionPaper;
import com.sciencematch.sciencematch.external.client.aws.S3Service;
import com.sciencematch.sciencematch.infrastructure.ChapterRepository;
import com.sciencematch.sciencematch.infrastructure.ConceptRepository;
import com.sciencematch.sciencematch.infrastructure.StudentRepository;
import com.sciencematch.sciencematch.infrastructure.TeacherLevelRepository;
import com.sciencematch.sciencematch.infrastructure.TeacherRepository;
import com.sciencematch.sciencematch.infrastructure.question.AnswerRepository;
import com.sciencematch.sciencematch.infrastructure.question.AssignQuestionRepository;
import com.sciencematch.sciencematch.infrastructure.question.ConnectQuestionRepository;
import com.sciencematch.sciencematch.infrastructure.question.QuestionPaperRepository;
import com.sciencematch.sciencematch.infrastructure.question.QuestionRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionPaperService {

    private final QuestionPaperRepository questionPaperRepository;
    private final QuestionRepository questionRepository;
    private final ConnectQuestionRepository connectQuestionRepository;
    private final AssignQuestionRepository assignQuestionRepository;
    private final ConceptRepository conceptRepository;
    private final AnswerRepository answerRepository;
    private final StudentRepository studentRepository;
    private final ChapterRepository chapterRepository;
    private final TeacherRepository teacherRepository;
    private final TeacherLevelRepository teacherLevelRepository;
    private final S3Service s3Service;

    //학습지 조회
    public List<QuestionPaperResponseDto> getAllQuestionPaper(
        QuestionPaperSelectDto questionPaperSelectDto) {

        List<QuestionPaperResponseDto> search = questionPaperRepository.search(
            questionPaperSelectDto);

        for (QuestionPaperResponseDto qp : search) {
            Chapter minChapter = chapterRepository.getChapterById(qp.getMinChapterId());
            Chapter maxChapter = chapterRepository.getChapterById(qp.getMaxChapterId());
            qp.setBoundary(minChapter.getDescription() + " ~ " + maxChapter.getDescription());
        }
        return search;
    }

    // 개념 조회
    public List<ConceptResponseDto> getQuestionPaperConcepts(List<Long> chapterIds) {
        return conceptRepository.getByChapterIds(chapterIds).stream().map(ConceptResponseDto::of)
            .collect(Collectors.toList());
    }

    //단원 유형별 자동 생성된 학습지 반환
    public List<QuestionResponseDto> getNormalQuestions(String email,
        NormalQuestionPaperRequestDto normalQuestionPaperRequestDto) {
        Teacher teacher = teacherRepository.getTeacherByEmail(email);

        //레벨 관계 없이 데이터 조회
        List<QuestionResponseDto> search = questionRepository.search(normalQuestionPaperRequestDto);
        Collections.shuffle(search);

        return makeNormalQuestionList(search, teacherLevelRepository.findByTeacherAndLevel(teacher,
                    normalQuestionPaperRequestDto.getLevel()).getSelectCount(normalQuestionPaperRequestDto.getQuestionNum()),
                    normalQuestionPaperRequestDto.getQuestionNum());
    }

    private List<QuestionResponseDto> makeNormalQuestionList( //난이도별 문제 개수에 맞게 선택해 반환
        List<QuestionResponseDto> questionResponseDtos, List<Integer> selectCount, Integer count) {
        List<QuestionResponseDto> result = new ArrayList<>();
        for (QuestionResponseDto q : questionResponseDtos) {
            switch (q.getLevel()) {
                case LOW:
                    if (selectCount.get(0) > 0) {
                        result.add(q);
                        selectCount.set(0, selectCount.get(0) - 1);
                    }
                    break;
                case MEDIUM_LOW:
                    if (selectCount.get(1) > 0) {
                        result.add(q);
                        selectCount.set(1, selectCount.get(1) - 1);
                    }
                    break;
                case MEDIUM:
                    if (selectCount.get(2) > 0) {
                        result.add(q);
                        selectCount.set(2, selectCount.get(2) - 1);
                    }
                    break;
                case MEDIUM_HARD:
                    if (selectCount.get(3) > 0) {
                        result.add(q);
                        selectCount.set(3, selectCount.get(3) - 1);
                    }
                    break;
                case HARD:
                    if (selectCount.get(4) > 0) {
                        result.add(q);
                        selectCount.set(4, selectCount.get(4) - 1);
                    }
                    break;
            }
            if (result.size() == count) {
                break;
            }
        }
        return result;

    }

    @Transactional
    public void createQuestionPaper(QuestionPaperCreateDto questionPaperCreateDto) {
        List<Question> questions = questionRepository.findAllByIds(
            questionPaperCreateDto.getQuestionIds());

        Map<Long, Question> collect = questions.stream()
            .collect(Collectors.toMap(Question::getId, question -> question));
        List<Question> sortedQuestion = questionPaperCreateDto.getQuestionIds().stream()
            .map(collect::get)
            .collect(Collectors.toList());

        String questionPaperUrl = s3Service.uploadFile(questionPaperCreateDto.getPdf(),
            "question-paper");

        QuestionPaper questionPaper = QuestionPaper.builder()
            .questionNum(questionPaperCreateDto.getQuestionNum())
            .school(questionPaperCreateDto.getSchool())
            .semester(questionPaperCreateDto.getSemester())
            .category(questionPaperCreateDto.getCategory())
            .questionTag(questionPaperCreateDto.getQuestionTag())
            .title(questionPaperCreateDto.getTitle())
            .makerName(questionPaperCreateDto.getMakerName())
            .subject(questionPaperCreateDto.getSubject())
            .level(questionPaperCreateDto.getLevel())
            .themeColor(questionPaperCreateDto.getThemeColor())
            .template(questionPaperCreateDto.getTemplate())
            .pdf(questionPaperUrl)
            .minChapterId(questionPaperCreateDto.getMinChapterId())
            .maxChapterId(questionPaperCreateDto.getMaxChapterId())
            .build();
        questionPaperRepository.save(questionPaper);
        for (Question question : sortedQuestion) {
            ConnectQuestion connectQuestion = ConnectQuestion.builder()
                .question(question)
                .questionPaper(questionPaper)
                .build();
            connectQuestionRepository.save(connectQuestion);
        }

    }

    @Transactional
    public void submitQuestionPaper(QuestionPaperSubmitDto questionPaperSubmitDto) {
        List<Student> students = studentRepository.getStudentsByList(
            questionPaperSubmitDto.getStudentIds());
        QuestionPaper questionPaper = questionPaperRepository.getQuestionPaperById(
            questionPaperSubmitDto.getQuestionPaperId());

        makeAssignQuestionPaper(students, questionPaper);
    }

    @Transactional
    public void submitMultipleQuestionPaper(
        MultipleQuestionPaperSubmitDto multipleQuestionPaperSubmitDto) {
        List<Student> students = studentRepository.getStudentsByList(
            multipleQuestionPaperSubmitDto.getStudentIds());
        List<QuestionPaper> papers = questionPaperRepository.getQuestionPapersByList(
            multipleQuestionPaperSubmitDto.getQuestionPaperIds());
        for (QuestionPaper questionPaper : papers) {
            makeAssignQuestionPaper(students, questionPaper);
        }
    }

    private void makeAssignQuestionPaper(List<Student> students, QuestionPaper questionPaper) {
        for (Student student : students) {
            assignQuestionRepository.save(AssignQuestions.builder()
                .questionPaper(questionPaper)
                .student(student)
                .subject(questionPaper.getSubject())
                .assignStatus(AssignStatus.WAITING)
                .build());
        }
    }

    @Transactional
    public void deleteQuestionPaper(List<Long> questionPaperId) {
        for (Long id : questionPaperId) {
            connectQuestionRepository.deleteAllByQuestionPaperId(id);
            assignQuestionRepository.deleteAllByQuestionPaperId(id);
        }
        connectQuestionRepository.flush();
        assignQuestionRepository.flush();
        questionPaperRepository.deleteAllById(questionPaperId);
    }

    public List<QuestionResponseDto> getWrongQuestionById(List<Long> assignQuestionId) {
        List<Long> questionIds = answerRepository.findAllByAssignQuestionsId(assignQuestionId)
            .stream().map(Answer::getQuestionId).collect(Collectors.toList());
        return getQuestionResponseDtos(questionIds);
    }

    public List<QuestionResponseDto> getWrongQuestionByPeriod(
        WrongAnswerPeriodDto wrongAnswerPeriodDto) {
        List<Long> questionIds = answerRepository.findAllByRightAnswerAndUpdatedAtBetween(false,
                wrongAnswerPeriodDto.getStart(), wrongAnswerPeriodDto.getEnd()).stream()
            .map(Answer::getQuestionId).collect(Collectors.toList());
        return getQuestionResponseDtos(questionIds);
    }

    private List<QuestionResponseDto> getQuestionResponseDtos(List<Long> questionIds) {
        List<Question> questions = questionRepository.findAllByIds(questionIds);
        List<QuestionResponseDto> result = new ArrayList<>();
        for (Question question : questions) {
            Chapter chapter = chapterRepository.getChapterById(question.getChapterId());
            result.add(QuestionResponseDto.of(question, chapter));
        }
        return result;
    }

    public QuestionPaperDownloadDto downloadQuestionPaper(
        QuestionPaperDownloadRequestDto questionPaperDownloadRequestDto) {
        QuestionPaper questionPaper = questionPaperRepository.getQuestionPaperById(
            questionPaperDownloadRequestDto.getQuestionPaperId());
        List<Question> questions = questionPaper.getConnectQuestions().stream()
            .map(ConnectQuestion::getQuestion)
            .collect(Collectors.toList());
        return QuestionPaperDownloadDto.of(questions, questionPaperDownloadRequestDto);
    }
}
